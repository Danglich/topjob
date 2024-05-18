package com.danglich.jobxinseeker.dto;

import com.danglich.jobxinseeker.validation.PasswordMatching;
import com.danglich.jobxinseeker.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@PasswordMatching(password = "newPassword" , confirmPassword = "confirmPassword")
public class ChangePasswordDTO {

	@Email(message = "Yêu cầu phải đúng định dạng email")
	@NotNull(message = "Yêu cầu phải có email")
	private String email;
	
	@ValidPassword
	private String oldPassword;
	
	@ValidPassword
	private String newPassword;
	
	private String confirmPassword;
}
