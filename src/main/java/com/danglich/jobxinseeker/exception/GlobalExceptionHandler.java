package com.danglich.jobxinseeker.exception;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(AuthException.class)
	public String handleAuthException(AuthException ex , Model theModel) {
		theModel.addAttribute("error", ex.getMessage());
		
		return "redirect:@{/auth(error)}";
	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNoHandlerFound(NoHandlerFoundException ex) {
		
		return "page/notfound";
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		
		return "page/notfound";
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public String handleAccessDeniedException(AccessDeniedException ex) {
		ex.printStackTrace();
		
		return "page/access_denied";
	}
	
	
	@ExceptionHandler(Exception.class)
	public void handleException(Exception ex ) {
		ex.printStackTrace();
	}

}
