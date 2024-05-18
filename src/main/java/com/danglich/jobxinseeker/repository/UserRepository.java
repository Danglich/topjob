package com.danglich.jobxinseeker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.danglich.jobxinseeker.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	@Modifying
	@Query("UPDATE User u " +
            "SET u.enabled = TRUE " +
            "WHERE u.id = ?1")
    void enable(int userId );
	
	@Modifying
	@Query("UPDATE User u " +
            "SET u.password = ?2 " +
            "WHERE u.id = ?1")
    void resetPassword(int userId , String newPassword );
	
	Optional<User> findByEmail(String email);
}
