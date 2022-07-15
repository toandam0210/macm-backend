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
@Table(name = "event_payment_status_report")
public class EventPaymentStatusReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "eventId")
	private Event event;

	@ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private User user;

	@Column
	private double fundChange;

	@Column
	private double fundBalance;

	@Column
	private double paymentValue;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String createdBy;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getFundChange() {
		return fundChange;
	}

	public void setFundChange(double fundChange) {
		this.fundChange = fundChange;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

}
