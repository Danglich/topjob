package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.User;

public interface ConfirmationTokenService {
	
	String save(User user);
	
	void updateConfirmedAt(String token);
	
	boolean isExitedToken(Integer userId);

}
