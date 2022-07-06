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
@Table(name = "competitive_bracket")
public class CompetitivePlayerBracket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "competitiveTypeId")
	private CompetitiveType competitiveType;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "playerId")
	private CompetitivePlayer competitivePlayer;
	
	
	@Column
	private int round;
	
	@Column
	private int numerical_order_id;
	
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

	public int getNumerical_order_id() {
		return numerical_order_id;
	}

	public void setNumerical_order_id(int numerical_order_id) {
		this.numerical_order_id = numerical_order_id;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
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
