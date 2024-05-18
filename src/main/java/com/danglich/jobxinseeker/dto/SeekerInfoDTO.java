package com.danglich.jobxinseeker.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SeekerInfoDTO {

	private String fullName;

	@NotBlank(message = "Email không được trống")
	@Email(message = "Email không đúng định dạng")
	private String email;

	@Pattern(regexp = "(^$|[0-9]{10})", message = "Số điện thoại không hợp lệ")
	private String phoneNumber;

	
}
