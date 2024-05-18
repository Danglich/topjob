package com.danglich.jobxinseeker.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.danglich.jobxinseeker.model.ConfirmationToken;


@Repository
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Integer>{

	@Modifying
	@Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    void updateConfirmedAt(String token , LocalDateTime comfirmedAt);
	
	Optional<ConfirmationToken> findByToken(String token);
	
	List<ConfirmationToken> findByUserIdAndExpiredAtBefore(Integer userId , LocalDateTime now);
}
