package com.danglich.jobxinseeker.controller;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Application;
import com.danglich.jobxinseeker.model.ApplicationStatus;
import com.danglich.jobxinseeker.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ApplicationController {
	
	private final ApplicationService service;

	@GetMapping("/lich-su-ung-tuyen") 
	public String showApplied(
			@RequestParam(value = "page", defaultValue = "0") int pageNumber,
			@RequestParam(value = "status", defaultValue = "") String statusString,
            Model model) {
		
		Page<Application> page = null;
		ApplicationStatus status = null;
		
		if(statusString.isEmpty()) {
			page = service.getAppliedForCurrentUser(pageNumber, null);
			
		} else {
			try {
				status =ApplicationStatus.valueOf(statusString);
				page = service.getAppliedForCurrentUser(pageNumber, status);
			} catch (Exception e) {
				return "redirect:/lich-su-ung-tuyen";
			}
		}
		
		model.addAttribute("applications", page.getContent());
        model.addAttribute("currentPage", page.getNumber());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("statuses", ApplicationStatus.values());
        model.addAttribute("currentStatus", status);
        
		return "application/applied";
	}
	
	@PostMapping("/ung-tuyen")
	public String showApplyPage(@Valid @ModelAttribute("application")  ApplicationDTO applicationDTO , BindingResult bindingResult) throws IOException {
		if(bindingResult.hasErrors()) {
			System.out.println("Loiiiiii");
		}
		service.create(applicationDTO);
		
		return "redirect:/lich-su-ung-tuyen";
		
	}
	
	
}
