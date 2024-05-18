package com.danglich.jobxinseeker.controller;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.dto.SalaryRange;
import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.Experience;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.AddressService;
import com.danglich.jobxinseeker.service.CVService;
import com.danglich.jobxinseeker.service.CompanyService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class JobController {

	private final JobService service;
	private final AddressService addressService;
	private final CVService cvService;

	@GetMapping("/viec-lam")
	public String showJobPage(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@RequestParam(value = "experience", defaultValue = "") String experience,
			@RequestParam(value = "addressId", required = false) Integer addressId,
			@RequestParam(value = "salaryRange", defaultValue = "") String salaryRange,
			Model model) {

		model.addAttribute("experiences", Experience.values());
		model.addAttribute("addresses", addressService.getAll());
		model.addAttribute("salaryRanges", SalaryRange.values());

		Page<Jobs> jobsPage = service.searchJobsByCriteria(keyword, addressId,
				experience, salaryRange, page);
		model.addAttribute("jobs", jobsPage.getContent());
		model.addAttribute("currentPage", jobsPage.getNumber());
		model.addAttribute("totalPages", jobsPage.getTotalPages());
		model.addAttribute("totalElements", jobsPage.getTotalElements());

		List<Jobs> suggestJobsByUser = service.getTop4SuggestJobs();
		model.addAttribute("suggestJobsByUser", suggestJobsByUser);

		return "job/list";
	}

	@GetMapping("/viec-lam-tot-nhat")
	public String showTopJob() {

		return "job/hot-job";
	}

	@GetMapping("/viec-lam-moi-nhat")
	public String showNewJob(
			@RequestParam(value = "page", defaultValue = "0") int page,
			Model model) {

		Page<Jobs> jobPage = service.getNewestJobs(page);
		model.addAttribute("jobs", jobPage.getContent());
		model.addAttribute("currentPage", jobPage.getNumber());
		model.addAttribute("totalPages", jobPage.getTotalPages());
		model.addAttribute("totalElements", jobPage.getTotalElements());

		return "job/new-job";
	}

	@GetMapping("/viec-lam-da-luu")
	public String showSavedJob(Model theModel) {
		Set<Jobs> savedJobs = service.getJobsSaved();

		theModel.addAttribute("savedJobs", savedJobs);

		return "job/saved";
	}

	@GetMapping("/viec-lam/{jobId}")
	public String showJobDetail(@PathVariable(name = "jobId") int jobId,
			Model theModel) {

		Jobs job = service.getById(jobId);
		theModel.addAttribute("job", job);

		List<Jobs> suggestJobs = service
				.getSuggestJobsByCategory(job.getCategory().getId());
		theModel.addAttribute("suggestJobs", suggestJobs);

		List<Jobs> suggestJobsByUser = service.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobsByUser", suggestJobsByUser);

		boolean isApplied = job.getApplications().stream()
				.filter(a -> a.getJob().getId() == job.getId()).findFirst()
				.isPresent();
		theModel.addAttribute("isApplied", isApplied);

		return "job/job-detail";
	}

	@GetMapping("/ung-tuyen/{jobId}")
	public String showApplicationForm(@PathVariable(name = "jobId") int jobId,
			Model theModel) {

		Jobs job = service.getById(jobId);
		theModel.addAttribute("job", job);
		
		List<CV> cvs = cvService.getForCurrentSeeker();
		theModel.addAttribute("cvs", cvs);

		List<Jobs> suggestJobsByUser = service.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobsByUser", suggestJobsByUser);

		ApplicationDTO application = new ApplicationDTO();
		application.setJobId(job.getId());
		application.setMode("UPLOADED");
		theModel.addAttribute("application", application);

		return "application/application-form";
	}

}
