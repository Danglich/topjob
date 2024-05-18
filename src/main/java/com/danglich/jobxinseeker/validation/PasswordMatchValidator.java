package com.danglich.jobxinseeker.validation;

import org.springframework.beans.BeanWrapperImpl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatching, Object>{

	
	private String password;
	private String confirmPassword;
	
	@Override
	public void initialize(PasswordMatching constraintAnnotation) {
		
		this.password = constraintAnnotation.password();
		this.confirmPassword = constraintAnnotation.confirmPassword();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
            return true;
        }

		BeanWrapperImpl beanWrapper = new BeanWrapperImpl(value);
        String passwordValue = (String) beanWrapper.getPropertyValue(password);
        Object confirmPasswordValue = beanWrapper.getPropertyValue(confirmPassword);

        return passwordValue != null && passwordValue.equals(confirmPasswordValue);
	}

}
