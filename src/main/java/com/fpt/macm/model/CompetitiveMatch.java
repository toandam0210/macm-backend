package com.fpt.macm.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "competitive_match")
public class CompetitiveMatch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private int round;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "type_id")
	private CompetitiveType competitiveType;
	
	@Column
	private String firstStudentId;
	
	@Column
	private String secondStudentId;
	
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

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public String getFirstStudentId() {
		return firstStudentId;
	}

	public void setFirstStudentId(String firstStudentId) {
		this.firstStudentId = firstStudentId;
	}

	public String getSecondStudentId() {
		return secondStudentId;
	}

	public void setSecondStudentId(String secondStudentId) {
		this.secondStudentId = secondStudentId;
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

	public CompetitiveType getCompetitiveType() {
		return competitiveType;
	}

	public void setCompetitiveType(CompetitiveType competitiveType) {
		this.competitiveType = competitiveType;
	}
	
}
