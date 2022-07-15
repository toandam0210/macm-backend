package com.fpt.macm.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private String description;
	
	@Column
	private int maxQuantityComitee;
	
	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;
	
	@Column
	private double totalAmountEstimated;
	
	@Column
	private double totalAmountActual;
	
	@Column
	private double amountFromClub;
	
	@Column
	private double amountPerRegisterEstimated;
	
	@Column
	private double amountPerRegisterActual;
	
	@Column
	private String semester;

	public double getAmountFromClub() {
		return amountFromClub;
	}

	public void setAmountFromClub(double amountFromClub) {
		this.amountFromClub = amountFromClub;
	}

	public double getTotalAmountEstimated() {
		return totalAmountEstimated;
	}

	public void setTotalAmountEstimated(double totalAmountEstimated) {
		this.totalAmountEstimated = totalAmountEstimated;
	}

	public double getTotalAmountActual() {
		return totalAmountActual;
	}

	public void setTotalAmountActual(double totalAmountActual) {
		this.totalAmountActual = totalAmountActual;
	}

	public double getAmountPerRegisterEstimated() {
		return amountPerRegisterEstimated;
	}

	public void setAmountPerRegisterEstimated(double amountPerRegisterEstimated) {
		this.amountPerRegisterEstimated = amountPerRegisterEstimated;
	}

	public double getAmountPerRegisterActual() {
		return amountPerRegisterActual;
	}

	public void setAmountPerRegisterActual(double amountPerRegisterActual) {
		this.amountPerRegisterActual = amountPerRegisterActual;
	}

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

	public int getMaxQuantityComitee() {
		return maxQuantityComitee;
	}

	public void setMaxQuantityComitee(int maxQuantityComitee) {
		this.maxQuantityComitee = maxQuantityComitee;
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

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}
	
}
