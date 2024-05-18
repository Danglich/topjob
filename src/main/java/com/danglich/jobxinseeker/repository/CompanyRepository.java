package com.danglich.jobxinseeker.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.Category;
import com.danglich.jobxinseeker.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{
	
	@Query("SELECT c FROM Company c LEFT JOIN c.jobs a GROUP BY c ORDER BY COALESCE(COUNT(a), 0) DESC")
	Page<Company> findTopCompaniesByApplicationCount( Pageable pageable);
	
	Page<Company> findByNameContaining(String name , Pageable pageable);
	
	Page<Company> findByCategory(Category category , Pageable pageable);

}
