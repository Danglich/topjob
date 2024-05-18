package com.danglich.jobxinseeker.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.CompanyService;
import com.danglich.jobxinseeker.service.JobService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/cong-ty")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService companyService;
	private final JobService jobService;

	@GetMapping
	public String showListCompany(
			@RequestParam(value = "page", defaultValue = "0") int page,
			Model theModel) {
		Page<Company> companyPage = companyService.getAllCompanies(page);
		theModel.addAttribute("companies", companyPage.getContent());
		theModel.addAttribute("currentPage", companyPage.getNumber());
		theModel.addAttribute("totalPages", companyPage.getTotalPages());
		theModel.addAttribute("totalElements", companyPage.getTotalElements());

		return "company/list";
	}

	@GetMapping("/{companyId}")
	public String showCompanyDetail(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			@PathVariable(name = "companyId", required = true) int companyId,
			Model theModel, Principal principal) {
		Company company = companyService.getById(companyId);
		theModel.addAttribute("company", company);

		List<Company> suggestCompanies = companyService
				.getCompaniesByCategory(company.getCategory(), 0).getContent();
		theModel.addAttribute("suggestCompanies", suggestCompanies);

		List<Jobs> suggestJobs = jobService.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobs", suggestJobs);

		boolean isFollowed = principal != null && company.getFollowers()
				.stream().filter(s -> s.getUser().getEmail().equals(principal.getName()))
				.toList().size() > 0;
		theModel.addAttribute("isFollowed", isFollowed);
		
		Page<Jobs> jobsPage = jobService.getJobsOfCompany(keyword, company, page);
		theModel.addAttribute("jobs", jobsPage.getContent());
		theModel.addAttribute("currentPage", jobsPage.getNumber());
		theModel.addAttribute("totalPages", jobsPage.getTotalPages());
		theModel.addAttribute("totalElements", jobsPage.getTotalElements());

		return "company/detail";
	}

	@GetMapping("/tim-kiem")
	public String searchCompany(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "keyword", defaultValue = "") String keyword,
			Model theModel) {

		Page<Company> companyPage = companyService.seach(keyword, page);
		theModel.addAttribute("companies", companyPage.getContent());
		theModel.addAttribute("currentPage", companyPage.getNumber());
		theModel.addAttribute("totalPages", companyPage.getTotalPages());
		theModel.addAttribute("totalElements", companyPage.getTotalElements());

		List<Company> topCompanies = companyService.getTopCompanies(0)
				.getContent();
		theModel.addAttribute("topCompanies", topCompanies);

		return "company/search";
	}

}
