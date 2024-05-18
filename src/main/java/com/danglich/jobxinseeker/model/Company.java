package com.danglich.jobxinseeker.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "company")
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "avatar")
	private String avatar;

	@Column(name = "banner")
	private String banner;

	@Column(name = "description")
	private String description;

	@Column(name = "scale")
	private String scale;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
	private List<Jobs> jobs;

	@ManyToMany(mappedBy = "followedCompanies")
	private Set<JobSeekers> followers = new HashSet<>();

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name 
				+ ", avatar=" + avatar + ", banner="
				+ banner + ", description=" + description + ",  scale=" + scale;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(avatar, banner, category,
				description,  followers, id, jobs, name,  scale);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(avatar, other.avatar)
				&& Objects.equals(id, other.id)
				&& Objects.equals(banner, other.banner)
				&& Objects.equals(category, other.category)
				&& Objects.equals(description, other.description)
				&& Objects.equals(followers, other.followers) && id == other.id
				&& Objects.equals(jobs, other.jobs)
				&& Objects.equals(name, other.name)
				&& Objects.equals(scale, other.scale);
	}

}
