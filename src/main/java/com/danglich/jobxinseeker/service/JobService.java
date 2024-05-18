package com.danglich.jobxinseeker.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.danglich.jobxinseeker.model.Address;
import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Company;
import com.danglich.jobxinseeker.model.Experience;
import com.danglich.jobxinseeker.model.Jobs;

public interface JobService {
	
	Page<Jobs> getNewestJobs(int page);
	
	Page<Jobs> getTopJobs(int page);
	
	Page<Jobs> getSuggestJobsForUser(int page);
	
	Jobs getById(int id);
	
	List<Jobs> getSuggestJobsByCategory(int categoryId);
	
	List<Jobs> getTop4SuggestJobs();
	
	Set<Jobs> getJobsSaved();
	
	Page<Jobs> getJobsOfCompany(String keyword , Company company, int pageNumber);
	
	Page<Jobs> searchJobsByCriteria(String keyword, Integer addressId, String experience , String salaryRange, int pageNumber);

}
