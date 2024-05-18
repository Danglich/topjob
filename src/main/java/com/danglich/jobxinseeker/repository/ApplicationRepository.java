package com.danglich.jobxinseeker.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.Application;
import com.danglich.jobxinseeker.model.ApplicationStatus;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	Page<Application> findBySeekerId(int seekerId , Pageable page);
	Page<Application> findBySeekerIdAndStatus(int seekerId , ApplicationStatus status , Pageable page);
}
