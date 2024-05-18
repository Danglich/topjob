package com.danglich.jobxinseeker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String>{


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		return value.trim().length() >= 6 && !value.trim().contains(" ");
	}
	

}
