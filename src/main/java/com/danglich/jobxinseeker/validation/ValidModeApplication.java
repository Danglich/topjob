package com.danglich.jobxinseeker.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidModeApplicationValidator.class)
@Documented
public @interface ValidModeApplication {

	String message () default "Bạn phải chọn một trong 2 lựa chọn CV của chúng tôi";
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

