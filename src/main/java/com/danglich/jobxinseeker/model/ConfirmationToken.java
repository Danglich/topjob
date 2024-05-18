package com.danglich.jobxinseeker.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "confirmation_token")
public class ConfirmationToken {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	
	@Column(name = "expired_at")
	private LocalDateTime expiredAt;
	
	@Column(name = "confirmed_at")
	private LocalDateTime confirmedAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

}
