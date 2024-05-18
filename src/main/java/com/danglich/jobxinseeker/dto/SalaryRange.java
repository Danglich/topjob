package com.danglich.jobxinseeker.dto;

public enum SalaryRange {
	
	
	UNDER_FIVE(0 , 5 , "Dưới 5 triệu"),
	FIVE_TO_TEN(5 , 10 , "5 đến 10 triệu"),
	TEN_TO_20(10 , 20 , "10 đến 20 triệu"),
	GREATER_20(20 , 1000 , "Trên 20 triệu");

	private int minSalary;
	
	private int maxSalary;
	
	private String detail;
;

	SalaryRange(int minSalary, int maxSalary, String detail) {
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
		this.detail = detail;
	}
	public int getMinSalary() {
		return minSalary;
	}
	public void setMinSalary(int minSalary) {
		this.minSalary = minSalary;
	}
	public int getMaxSalary() {
		return maxSalary;
	}
	public void setMaxSalary(int maxSalary) {
		this.maxSalary = maxSalary;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
