package com.fpt.macm.dto;

import java.time.LocalDateTime;

public class EventPaymentStatusReportDto {

	private int id;
	private int eventId;
	private String userName;
	private String userStudentId;
	private double fundChange;
	private double fundBalance;
	private double paymentValue;
	private double amountPerRegisterEstimate;
	private double amountPerRegisterActual;
	private String createdBy;
	private LocalDateTime createdOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
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

	public double getFundBalance() {
		return fundBalance;
	}

	public void setFundBalance(double fundBalance) {
		this.fundBalance = fundBalance;
	}

	public double getPaymentValue() {
		return paymentValue;
	}

	public void setPaymentValue(double paymentValue) {
		this.paymentValue = paymentValue;
	}

	public double getAmountPerRegisterEstimate() {
		return amountPerRegisterEstimate;
	}

	public void setAmountPerRegisterEstimate(double amountPerRegisterEstimate) {
		this.amountPerRegisterEstimate = amountPerRegisterEstimate;
	}

	public double getAmountPerRegisterActual() {
		return amountPerRegisterActual;
	}

	public void setAmountPerRegisterActual(double amountPerRegisterActual) {
		this.amountPerRegisterActual = amountPerRegisterActual;
	}

}
