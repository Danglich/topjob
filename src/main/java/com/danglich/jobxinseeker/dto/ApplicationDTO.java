package com.danglich.jobxinseeker.dto;

import org.springframework.web.multipart.MultipartFile;

import com.danglich.jobxinseeker.validation.ValidModeApplication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
	
	@ValidModeApplication
	private String mode;
	
	private Integer cvId;
	
	private String message;
	
	private MultipartFile file;
	
	private Integer jobId;
	
	
}
