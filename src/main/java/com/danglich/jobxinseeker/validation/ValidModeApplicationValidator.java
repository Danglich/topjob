package com.danglich.jobxinseeker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.val;

public class ValidModeApplicationValidator implements ConstraintValidator<ValidModeApplication, String>{


	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return false;
		return value.equals("UPLOADED") || value.equals("NEW");
	}
	

}
