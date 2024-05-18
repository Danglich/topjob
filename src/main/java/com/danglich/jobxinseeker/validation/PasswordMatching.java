package com.danglich.jobxinseeker.validation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatching {

	String password ();
	String confirmPassword ();
	String message () default "Mật khẩu phải khớp với nhau";
	
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
