package com.fpt.macm.model.entity;

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
@Table(name = "competitive_player_bracket")
public class CompetitivePlayerBracket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "competitiveTypeId")
	private CompetitiveType competitiveType;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "player_id")
	private CompetitivePlayer competitivePlayer;
	
	@Column
	private Integer numericalOrderId;
	
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

	public CompetitiveType getCompetitiveType() {
		return competitiveType;
	}

	public void setCompetitiveType(CompetitiveType competitiveType) {
		this.competitiveType = competitiveType;
	}

	public CompetitivePlayer getCompetitivePlayer() {
		return competitivePlayer;
	}

	public void setCompetitivePlayer(CompetitivePlayer competitivePlayer) {
		this.competitivePlayer = competitivePlayer;
	}

	public Integer getNumericalOrderId() {
		return numericalOrderId;
	}

	public void setNumericalOrderId(Integer numericalOrderId) {
		this.numericalOrderId = numericalOrderId;
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
	
}
