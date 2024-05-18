package com.danglich.jobxinseeker.service;

import java.io.IOException;

import org.springframework.data.domain.Page;

import com.danglich.jobxinseeker.dto.ApplicationDTO;
import com.danglich.jobxinseeker.model.Application;
import com.danglich.jobxinseeker.model.ApplicationStatus;

public interface ApplicationService {
	
	Application create(ApplicationDTO form) throws IOException;
	
	Page<Application> getAppliedForCurrentUser(int page, ApplicationStatus status); 
	

}
