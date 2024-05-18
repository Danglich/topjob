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
@Constraint(validatedBy = ValidPasswordValidator.class)
@Documented
public @interface ValidPassword {

	String message () default "Mật khẩu phải chứ ít nhất 6 ký tự và không chứ dấu cách";
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

