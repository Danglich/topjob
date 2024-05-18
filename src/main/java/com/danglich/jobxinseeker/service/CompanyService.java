package com.danglich.jobxinseeker.service;

import org.springframework.data.domain.Page;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Company;

public interface CompanyService {
	
	Page<Company> getTopCompanies(int pageNumber);
	
	Page<Company> getAllCompanies(int pageNumber);
	
	Company getById(int id);
	
	Page<Company> seach(String q , int pageNumber);
	
	Page<Company> getCompaniesByCategory(Category category , int pageNumber);

}
