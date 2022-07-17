package com.fpt.macm.model.dto;

import java.time.LocalDateTime;

public class FacilityRequestDto {

	private int id;
	private int facilityId;
	private String facilityName;
	private int facilityCategoryId;
	private String facilityCategory;
	private int quantity;
	private double unitPrice;
	private String status;
	private String createdBy;
	private LocalDateTime createdOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityCategory() {
		return facilityCategory;
	}

	public void setFacilityCategory(String facilityCategory) {
		this.facilityCategory = facilityCategory;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public int getFacilityCategoryId() {
		return facilityCategoryId;
	}

	public void setFacilityCategoryId(int facilityCategoryId) {
		this.facilityCategoryId = facilityCategoryId;
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
