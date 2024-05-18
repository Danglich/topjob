package com.danglich.jobxinseeker.security.oauth2;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.danglich.jobxinseeker.service.AuthService;
import com.danglich.jobxinseeker.service.JobSeekerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler{
	
	private final AuthService authService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,  HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {
		CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
		authService.processLoginWithOAuth(user);
		
		HttpSession session = request.getSession();
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(savedRequest != null) 
        	response.sendRedirect(savedRequest.getRedirectUrl());
        else response.sendRedirect("/");
	}

}
