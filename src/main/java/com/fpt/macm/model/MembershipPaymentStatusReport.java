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
@Table(name = "membership_payment_status_report")
public class MembershipPaymentStatusReport {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "membershipInfoId")
	private MembershipInfo membershipInfo;

	@ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private User user;

	@Column
	private boolean paymentStatus;
	
	@Column
	private double fundChange;

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

	public MembershipInfo getMembershipInfo() {
		return membershipInfo;
	}

	public void setMembershipInfo(MembershipInfo membershipInfo) {
		this.membershipInfo = membershipInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public double getFundChange() {
		return fundChange;
	}

	public void setFundChange(double fundChange) {
		this.fundChange = fundChange;
	}

}
