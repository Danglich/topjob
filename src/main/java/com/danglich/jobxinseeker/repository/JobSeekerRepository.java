package com.danglich.jobxinseeker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.User;

@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeekers, Integer>{
	
	Optional<JobSeekers> findByUser(User user);

}
