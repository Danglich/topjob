package com.danglich.jobxinseeker.utils;

import org.springframework.stereotype.Component;

@Component(value = "compareUtils")
public class CompareUtils {
	
	public boolean compareIntAndString(int number , String string) {
		if(string == null || string.isEmpty()) {
			return false;
		}
		return Integer.parseInt(string) == number;
		
	}
	
	public boolean compareEnumAndString(Object e , String string ) {
		if(string == null || string.isEmpty() || e == null) {
			return false;
		}
		
		return e.toString().equals(string);
	}

}
