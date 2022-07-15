package com.fpt.macm.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "club_fund_report")
public class ClubFundReport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private double fundChange;
	
	@Column
	private String note;
	
	@Column
	private double fundBalance;
	
	@Column
	private String createdBy;
	
	@Column
	private LocalDateTime createdOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getFundChange() {
		return fundChange;
	}

	public void setFundChange(double fundChange) {
		this.fundChange = fundChange;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public double getFundBalance() {
		return fundBalance;
	}

	public void setFundBalance(double fundBalance) {
		this.fundBalance = fundBalance;
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
	
}
