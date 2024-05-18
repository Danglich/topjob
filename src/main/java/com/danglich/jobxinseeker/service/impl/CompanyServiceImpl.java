package com.danglich.jobxinseeker.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.repository.CompanyRepository;
import com.danglich.jobxinseeker.service.CompanyService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

	private final CompanyRepository repository;
	private final static int NUMBER_PER_PAGE = 6;

	@Override
	public Page<Company> getTopCompanies(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);

		return repository.findTopCompaniesByApplicationCount(pageable);
	}

	@Override
	public Page<Company> getAllCompanies(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);

		return repository.findAll(pageable);
	}

	@Override
	public Company getById(int id) {
		Company company = repository.findById(id)
				.orElseThrow(() -> new ResourceAccessException("Not found company with id :" + id));
		return company;
	}

	@Override
	public Page<Company> seach(String q, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);

		return repository.findByNameContaining(q, pageable);
	}

	@Override
	public Page<Company> getCompaniesByCategory(Category category, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, NUMBER_PER_PAGE);

		return repository.findByCategory(category, pageable);
	}

}
