package com.fpt.macm.dto;

import java.time.LocalDateTime;

public class FeeBalanceDashboardDto {

	private double balance;
	private LocalDateTime createdOn;

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

}
