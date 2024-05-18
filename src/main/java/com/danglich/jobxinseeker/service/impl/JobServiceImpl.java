package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.SalaryRange;
import com.danglich.jobxinseeker.model.Address;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Experience;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.repository.JobRepository;
import com.danglich.jobxinseeker.service.AddressService;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

	private JobRepository repository;
	private JobSeekerService seekerService;
	private AddressService addressService;

	private static final int NUMBER_PER_PAGE = 6;

	@Autowired
	public JobServiceImpl(@Lazy JobSeekerService seekerService,
			JobRepository repository, AddressService addressService) {
		this.repository = repository;
		this.seekerService = seekerService;
		this.addressService = addressService;
	}

	@Override
	public Page<Jobs> getNewestJobs(int page) {

		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		Pageable pageable = PageRequest.of(page, NUMBER_PER_PAGE, sort);

		Page<Jobs> jobPage = repository
				.findByExpiredAtAfter(LocalDateTime.now(), pageable);

		return jobPage;
	}

	@Override
	public Jobs getById(int id) {

		return repository.findById(id).orElseThrow(
				() -> new ResourceAccessException("Not found job"));
	}

	@Override
	public List<Jobs> getSuggestJobsByCategory(int categoryId) {

		return repository.findTop5ByCategoryId(categoryId);
	}

	@Override
	public List<Jobs> getTop4SuggestJobs() {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			JobSeekers jobSeeker = seekerService
					.getByUsername(authentication.getName());

			List<Jobs> jobs = repository
					.findTop4ByCategoryInOrderByCreatedAtDesc(
							jobSeeker.getCategories());
			if (jobs.size() > 0)
				return jobs;
		}

		return repository.findTop4ByOrderByCreatedAtDesc();
	}

	@Override
	public Set<Jobs> getJobsSaved() {

		JobSeekers seeker = seekerService.getCurrentUser();

		return seeker.getSavedJobs();
	}

	@Override
	public Page<Jobs> getTopJobs(int page) {
		Pageable pageable = PageRequest.of(page, NUMBER_PER_PAGE);
		Page<Jobs> jobPage = repository
				.findTopJobsByApplicationCount(LocalDateTime.now(), pageable);

		return jobPage;
	}

	@Override
	public Page<Jobs> getSuggestJobsForUser(int page) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		Pageable pageable = PageRequest.of(page, NUMBER_PER_PAGE);
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
			JobSeekers jobSeeker = seekerService
					.getByUsername(authentication.getName());

			return repository.findByCategoryInAndExpiredAtAfter(
					jobSeeker.getCategories(), LocalDateTime.now(), pageable);
		}

		return this.getNewestJobs(page);
	}

	@Override
	public Page<Jobs> getJobsOfCompany(String keyword, Company company,
			int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);
		Page<Jobs> pageJobs = repository
				.findByTitleContainingAndCompany(keyword, company, pageable);

		return pageJobs;
	}

	@Override
	public Page<Jobs> searchJobsByCriteria(String keyword, Integer addressId,
			String experienceString, String salaryRange, int pageNumber) {
		Experience experience;
		Address address;
		Integer minSalary = null;
		Integer maxSalary = null;
		try {
			experience = Experience.valueOf(experienceString);
			
		} catch (Exception e) {
			experience = null;
		}

		try {
			address = addressService.getById(addressId);
		} catch (Exception e) {
			address = null ;
		}
		
		try {
			SalaryRange range = SalaryRange.valueOf(salaryRange);
			minSalary = range.getMinSalary();
			maxSalary = range.getMaxSalary();
		} catch (Exception e) {
			minSalary = 0;
			maxSalary = 1000;
		}
		
		

		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);
		return repository.searchJobsByCriteria(keyword, address, experience,
				minSalary, maxSalary, LocalDateTime.now(), pageable);
	}

}
