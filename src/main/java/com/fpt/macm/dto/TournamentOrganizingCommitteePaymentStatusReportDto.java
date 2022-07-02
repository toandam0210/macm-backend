package com.fpt.macm.dto;

import java.time.LocalDateTime;

public class TournamentOrganizingCommitteePaymentStatusReportDto {

	private int id;
	private int tournamentId;
	private String userName;
	private String userStudentId;
	private double fundChange;
	private double fundBalance;
	private boolean paymentStatus;
	private String createdBy;
	private LocalDateTime createdOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTournamentId() {
		return tournamentId;
	}

	public void setTournamentId(int tournamentId) {
		this.tournamentId = tournamentId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStudentId() {
		return userStudentId;
	}

	public void setUserStudentId(String userStudentId) {
		this.userStudentId = userStudentId;
	}

	public double getFundChange() {
		return fundChange;
	}

	public void setFundChange(double fundChange) {
		this.fundChange = fundChange;
	}

	public double getFundBalance() {
		return fundBalance;
	}

	public void setFundBalance(double fundBalance) {
		this.fundBalance = fundBalance;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
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
