package com.danglich.jobxinseeker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.CV;
import com.danglich.jobxinseeker.model.JobSeekers;

@Repository
public interface CVRepository  extends JpaRepository<CV, Integer>{
	
	List<CV> findBySeeker(JobSeekers seeker); 
	
	@Modifying
	@Query("UPDATE CV cv " +
            "SET cv.filename = ?2 " +
            "WHERE cv.id = ?1")
    void updateName(int id , String name );
	
	@Modifying
	@Query("UPDATE CV cv " +
            "SET cv.isDefault = ?2 " +
            "WHERE cv.id = ?1")
    void updateIsDefault(int id , boolean isDefault);
	
	@Modifying
	@Query("UPDATE CV cv " +
            "SET cv.seeker = null " +
            "WHERE cv.id = ?1")
    void deleteBySeeker(int id );
	
	List<CV> findByIsDefaultTrueAndSeekerId(int seekerId );

}
