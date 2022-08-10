package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserTournamentDto implements Comparable<UserTournamentDto> {

	private int id;
	private String name;
	private String description;
	private double feeOrganizingCommiteePay;
	private int status;
	private LocalDate startDate;
	private double totalAmount;
	private double feePlayerPay;
	private double totalAmountEstimate;
	private double totalAmountFromClubEstimate;
	private double totalAmountFromClubActual;
	private LocalDateTime registrationPlayerDeadline;
	private LocalDateTime registrationOrganizingCommitteeDeadline;
	private boolean isJoined;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getFeeOrganizingCommiteePay() {
		return feeOrganizingCommiteePay;
	}

	public void setFeeOrganizingCommiteePay(double feeOrganizingCommiteePay) {
		this.feeOrganizingCommiteePay = feeOrganizingCommiteePay;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getFeePlayerPay() {
		return feePlayerPay;
	}

	public void setFeePlayerPay(double feePlayerPay) {
		this.feePlayerPay = feePlayerPay;
	}

	public double getTotalAmountEstimate() {
		return totalAmountEstimate;
	}

	public void setTotalAmountEstimate(double totalAmountEstimate) {
		this.totalAmountEstimate = totalAmountEstimate;
	}

	public double getTotalAmountFromClubEstimate() {
		return totalAmountFromClubEstimate;
	}

	public void setTotalAmountFromClubEstimate(double totalAmountFromClubEstimate) {
		this.totalAmountFromClubEstimate = totalAmountFromClubEstimate;
	}

	public double getTotalAmountFromClubActual() {
		return totalAmountFromClubActual;
	}

	public void setTotalAmountFromClubActual(double totalAmountFromClubActual) {
		this.totalAmountFromClubActual = totalAmountFromClubActual;
	}

	public LocalDateTime getRegistrationPlayerDeadline() {
		return registrationPlayerDeadline;
	}

	public void setRegistrationPlayerDeadline(LocalDateTime registrationPlayerDeadline) {
		this.registrationPlayerDeadline = registrationPlayerDeadline;
	}

	public LocalDateTime getRegistrationOrganizingCommitteeDeadline() {
		return registrationOrganizingCommitteeDeadline;
	}

	public void setRegistrationOrganizingCommitteeDeadline(LocalDateTime registrationOrganizingCommitteeDeadline) {
		this.registrationOrganizingCommitteeDeadline = registrationOrganizingCommitteeDeadline;
	}

	public boolean isJoined() {
		return isJoined;
	}

	public void setJoined(boolean isJoined) {
		this.isJoined = isJoined;
	}

	@Override
	public int compareTo(UserTournamentDto o) {
		// TODO Auto-generated method stub
		return this.getStartDate().compareTo(o.getStartDate()) * -1;
	}

}
