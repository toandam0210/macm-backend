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
	@JoinColumn(name = "competitiveTypeId")
	private CompetitivePlayerBracket firstPlayer;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "competitiveTypeId")
	private CompetitivePlayerBracket secondPlayer;
	
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

	public CompetitivePlayerBracket getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(CompetitivePlayerBracket firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public CompetitivePlayerBracket getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(CompetitivePlayerBracket secondPlayer) {
		this.secondPlayer = secondPlayer;
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
