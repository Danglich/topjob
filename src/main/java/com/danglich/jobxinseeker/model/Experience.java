package com.danglich.jobxinseeker.model;

public enum Experience {
	NON("Không có kinh nghiệm"),
	UNDER_ONE("Dưới 1 năm"),
	ONE_TO_THREE("1 - 3 năm"),
	GREAT_THAN_THREE("Trên 3 năm");
	
	private String describe;
	
	public String getDescribe() {
		
		return describe;
	}

	Experience(String string) {
		this.describe = string;
	}
	

}
