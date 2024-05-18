package com.danglich.jobxinseeker.dto;

import com.danglich.jobxinseeker.validation.PasswordMatching;
import com.danglich.jobxinseeker.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@PasswordMatching(
		password = "password" ,
		confirmPassword = "confirmPassword",
		message = "Mật khẩu phải khớp"
		
)
public class RegisterDTO {

	@Email
	@NotBlank(message = "Vui lòng nhập email của bạn")
	private String email;
	
	@NotBlank(message = "Vui lòng nhập mật khẩu")
	@ValidPassword
	private String password;
	
	@NotBlank(message = "Vui lòng nhập lại mật khẩu")
	private String confirmPassword;
	
	private boolean employer;

	
	
	
}
