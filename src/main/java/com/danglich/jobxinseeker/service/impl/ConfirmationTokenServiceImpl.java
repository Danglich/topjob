package com.danglich.jobxinseeker.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import com.danglich.jobxinseeker.model.ConfirmationToken;
import com.danglich.jobxinseeker.model.JobSeekers;
import com.danglich.jobxinseeker.model.User;
import com.danglich.jobxinseeker.repository.ConfirmationTokenRepository;
import com.danglich.jobxinseeker.service.ConfirmationTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService{
	
	private final ConfirmationTokenRepository confirmationTokenRepository;

	@Override
	public String save(User user) {
		
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = ConfirmationToken.builder()
													.createdAt(LocalDateTime.now())
													.expiredAt(LocalDateTime.now().plusHours(24))
													.user(user)
													.token(token)
													.build();
		
		confirmationTokenRepository.save(confirmationToken);
		
		return token;
	}

	@Override
	public void updateConfirmedAt(String token) {
		confirmationTokenRepository.findByToken(token)
					.orElseThrow(() -> new ResourceAccessException("Not found the confirmation token"));
		
		confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
	}


	@Override
	public boolean isExitedToken(Integer userId) {
		
		return confirmationTokenRepository.findByUserIdAndExpiredAtBefore(userId, LocalDateTime.now()).isEmpty();
	}
	
	

}
