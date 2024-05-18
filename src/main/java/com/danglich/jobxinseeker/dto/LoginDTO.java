package com.danglich.jobxinseeker.dto;

import com.danglich.jobxinseeker.validation.ValidPassword;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LoginDTO {
	
	@Email(message = "Phải đúng định dạng của email")
	@NotBlank(message = "Yêu cầu nhập email")
	private String email;
	
	@ValidPassword
	@NotBlank(message = "Yêu cầu nhập mật khẩu")
	private String password;

}
