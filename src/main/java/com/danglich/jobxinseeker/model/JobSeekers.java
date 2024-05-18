package com.danglich.jobxinseeker.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seeker")
public class JobSeekers  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private int id;


	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "code")
	private String code;

	@Column(name = "avatar")
	private String avatar;
	
	@OneToOne()
	@MapsId
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinTable(name = "seeker_category", joinColumns = @JoinColumn(name = "seeker_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private List<Category> categories;

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH, CascadeType.DETACH})
	@JoinTable(name = "job_saved", joinColumns = @JoinColumn(name = "seeker_id"), inverseJoinColumns = @JoinColumn(name = "job_id"))
	private Set<Jobs> savedJobs;

	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,
			CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
	@JoinTable(name = "seeker_company_follow", joinColumns = @JoinColumn(name = "seeker_id"), inverseJoinColumns = @JoinColumn(name = "company_id"))
	private Set<Company> followedCompanies = new HashSet<>();;

	@OneToMany(mappedBy = "seeker")
	private List<Application> applications;


	@Override
	public String toString() {
		return "JobSeekers [id=" + id  + ", phoneNumber="
				+ phoneNumber + ", code=" + code + ", avatar=" + avatar
				+ ", enabled=" +"]";
	}

}
