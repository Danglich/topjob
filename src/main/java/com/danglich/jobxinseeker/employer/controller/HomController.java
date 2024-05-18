package com.danglich.jobxinseeker.employer.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@PreAuthorize("hasRole('ROLE_EMPLOYER')")
public class HomController {
	
	
	@GetMapping("/employer/home")
	public ModelAndView showHome() {
		ModelAndView modelAndView = new ModelAndView("employer/home/index.html");
		
		return modelAndView;
	}

}
