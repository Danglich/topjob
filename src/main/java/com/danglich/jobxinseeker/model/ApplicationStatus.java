package com.danglich.jobxinseeker.model;

public enum ApplicationStatus {

	APPLIED("Đã ứng tuyển"),
	VIEWED("Nhà tuyển dụng đã xem");
	
	private String message;

	ApplicationStatus(String string) {
		message = string;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
}
