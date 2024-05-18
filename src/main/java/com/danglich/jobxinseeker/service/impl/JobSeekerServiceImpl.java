package com.danglich.jobxinseeker.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.SeekerInfoDTO;
import com.danglich.jobxinseeker.exception.IncorrectPasswordException;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.model.Provider;
import com.danglich.jobxinseeker.model.User;
import com.danglich.jobxinseeker.repository.JobSeekerRepository;
import com.danglich.jobxinseeker.repository.UserRepository;
import com.danglich.jobxinseeker.security.oauth2.CustomOAuth2User;
import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.CompanyService;
import com.danglich.jobxinseeker.service.JobSeekerService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobSeekerServiceImpl implements JobSeekerService {

	private final JobSeekerRepository repository;
	private final JobService jobService;
	private final CompanyService companyService;
	private final AuthService authService;
	private final UserRepository userRepository;

	@Override
	public SeekerInfoDTO getSeekerInfo(String username) {

		User user = authService.getCurrentUser();

		JobSeekers seeker = repository.findByUser(user).orElseThrow(
				() -> new UsernameNotFoundException("Not found user"));

		SeekerInfoDTO seekerInfo = new SeekerInfoDTO(user.getFullname(),
				seeker.getUser().getEmail(), seeker.getPhoneNumber());

		return seekerInfo;
	}

	@Override
	public JobSeekers getByUsername(String username) {

		User user = authService.getCurrentUser();
		JobSeekers seeker = repository.findByUser(user).orElseThrow(
				() -> new UsernameNotFoundException("Not found seeker"));

		return seeker;
	}

	@Override
	public JobSeekers getCurrentUser() {

		User user = authService.getCurrentUser();
		JobSeekers seeker = repository.findByUser(user).orElseThrow(
				() -> new UsernameNotFoundException("Not found user"));

		return seeker;
	}

	@Override
	public void saveJob(int jobId) {

		Jobs job = jobService.getById(jobId);
		JobSeekers seeker = this.getCurrentUser();
		Set<Jobs> savedJobs = seeker.getSavedJobs();
		savedJobs.add(job);
		repository.save(seeker);

	}

	@Override
	public void unSaveJob(int jobId) {
		Jobs job = jobService.getById(jobId);
		JobSeekers seeker = this.getCurrentUser();
		Set<Jobs> savedJobs = seeker.getSavedJobs();
		savedJobs.remove(job);
		repository.save(seeker);

	}

	@Override
	public SeekerInfoDTO updateInfo(SeekerInfoDTO request) {
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceAccessException(
						"Not found user with this email"));
		
		JobSeekers seeker = repository.findByUser(user).orElseThrow(() -> new ResourceAccessException(
				"Not found user with this email"));

		user.setFullname(request.getFullName());
		seeker.setPhoneNumber(request.getPhoneNumber());
		repository.save(seeker);
		userRepository.save(user);
		return request;

	}

	@Override
	public void followCompany(int companyId) {

		Company company = companyService.getById(companyId);
		JobSeekers seeker = this.getCurrentUser();
		Set<Company> followedCompanies = seeker.getFollowedCompanies();
		followedCompanies.add(company);
		repository.save(seeker);

	}

	@Override
	public void unfollowCompany(int companyId) {
		Company company = companyService.getById(companyId);
		JobSeekers seeker = this.getCurrentUser();
		Set<Company> followedCompanies = seeker.getFollowedCompanies();
		followedCompanies.remove(company);
		repository.save(seeker);

	}

}
