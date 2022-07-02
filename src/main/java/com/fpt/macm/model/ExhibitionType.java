package com.fpt.macm.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "exhibition_type")
public class ExhibitionType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "exhibition_type_id")
	private Set<ExhibitionTeam> exhibitionTeams;

	@Column
	private String name;

	@Column
	private int numberMale;

	@Column
	private int numberFemale;

	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberMale() {
		return numberMale;
	}

	public void setNumberMale(int numberMale) {
		this.numberMale = numberMale;
	}

	public int getNumberFemale() {
		return numberFemale;
	}

	public void setNumberFemale(int numberFemale) {
		this.numberFemale = numberFemale;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Set<ExhibitionTeam> getExhibitionTeams() {
		return exhibitionTeams;
	}

	public void setExhibitionTeams(Set<ExhibitionTeam> exhibitionTeams) {
		this.exhibitionTeams = exhibitionTeams;
	}

}
