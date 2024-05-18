package com.danglich.jobxinseeker.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@EqualsAndHashCode(callSuper=false)
@Table(name = "application")
public class Application extends DateAudit {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private ApplicationStatus status;
	
	@Column(name = "message")
	private String message;
	
	@OneToOne
	@JoinColumn(name = "cv_id", referencedColumnName = "id")
	private CV cv;
	
	@ManyToOne
	@JoinColumn(name = "seeker_id")
	private JobSeekers seeker;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "job_id")
	private Jobs job;
	

}
