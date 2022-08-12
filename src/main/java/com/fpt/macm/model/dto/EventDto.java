package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class EventDto implements Comparable<EventDto> {
	private int id;
	private String status;
	private String name;
	private int maxQuantityComitee;
	private double amountPerMemberRegister;
	private double totalAmount;
	private double amountFromClub;
	private String description;
	private LocalDateTime registrationMemberDeadline;
	private LocalDateTime registrationOrganizingCommitteeDeadline;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private int totalAttend;
	private int totalJoin;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmountPerMemberRegister() {
		return amountPerMemberRegister;
	}

	public void setAmountPerMemberRegister(double amountPerMemberRegister) {
		this.amountPerMemberRegister = amountPerMemberRegister;
	}

	public double getAmountFromClub() {
		return amountFromClub;
	}

	public void setAmountFromClub(double amountFromClub) {
		this.amountFromClub = amountFromClub;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaxQuantityComitee() {
		return maxQuantityComitee;
	}

	public void setMaxQuantityComitee(int maxQuantityComitee) {
		this.maxQuantityComitee = maxQuantityComitee;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(EventDto o) {
		// TODO Auto-generated method stub
		return this.getStartDate().compareTo(o.getStartDate()) * -1;
	}

	public LocalDateTime getRegistrationMemberDeadline() {
		return registrationMemberDeadline;
	}

	public void setRegistrationMemberDeadline(LocalDateTime registrationMemberDeadline) {
		this.registrationMemberDeadline = registrationMemberDeadline;
	}

	public LocalDateTime getRegistrationOrganizingCommitteeDeadline() {
		return registrationOrganizingCommitteeDeadline;
	}

	public void setRegistrationOrganizingCommitteeDeadline(LocalDateTime registrationOrganizingCommitteeDeadline) {
		this.registrationOrganizingCommitteeDeadline = registrationOrganizingCommitteeDeadline;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public int getTotalAttend() {
		return totalAttend;
	}

	public void setTotalAttend(int totalAttend) {
		this.totalAttend = totalAttend;
	}

	public int getTotalJoin() {
		return totalJoin;
	}

	public void setTotalJoin(int totalJoin) {
		this.totalJoin = totalJoin;
	}

}
