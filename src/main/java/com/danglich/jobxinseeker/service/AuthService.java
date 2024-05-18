package com.danglich.jobxinseeker.service;

import com.danglich.jobxinseeker.dto.ChangePasswordDTO;
import com.danglich.jobxinseeker.dto.LoginDTO;
import com.danglich.jobxinseeker.dto.RegisterDTO;
import com.danglich.jobxinseeker.model.User;
import com.danglich.jobxinseeker.security.oauth2.CustomOAuth2User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
	
	void register(RegisterDTO registerDTO);
	
	void reSendConfirmationToken(String email);
	
	void confirm(String token, HttpServletRequest request, HttpServletResponse response);
	
	String login(LoginDTO loginDTO, HttpServletRequest request , HttpServletResponse response);

	void resetPassword(String email);
	
	void processLoginWithOAuth(CustomOAuth2User oAuth2User) ;
	
	User getCurrentUser();
	
	void changePassword(ChangePasswordDTO request);
}
