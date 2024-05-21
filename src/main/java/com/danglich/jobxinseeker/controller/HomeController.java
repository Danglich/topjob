package com.danglich.jobxinseeker.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.danglich.jobxinseeker.dto.SalaryRange;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Experience;
import com.danglich.jobxinseeker.model.Jobs;
import com.danglich.jobxinseeker.service.AddressService;
import com.danglich.jobxinseeker.service.CompanyService;
import com.danglich.jobxinseeker.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
	
	private final JobService jobService;
	private final CompanyService companyService;
	private final AddressService addressService;
	
	@GetMapping("/") 
	public String sayHello() {
		
		return "redirect:/home";
	}
	
	@GetMapping("/home")
	public String showHome(Authentication authentication, Model theModel) {
		
		log.info("Acessing home page");
        // for search form
		theModel.addAttribute("experiences", Experience.values());
		theModel.addAttribute("addresses", addressService.getAll());
		theModel.addAttribute("salaryRanges", SalaryRange.values());
		
		List<Jobs> newestJobs = jobService.getNewestJobs(0).getContent();
		theModel.addAttribute("newestJobs", newestJobs);
		
		List<Jobs> topJobs = jobService.getTopJobs(0).getContent();
		theModel.addAttribute("topJobs", topJobs);
		
		List<Jobs> suggestJobs = jobService.getTop4SuggestJobs();
		theModel.addAttribute("suggestJobs", suggestJobs);
		
		List<Company> topCompanies = companyService.getTopCompanies(0).getContent();
		theModel.addAttribute("topCompanies", topCompanies);

        return "home/index";
	}
	
}
