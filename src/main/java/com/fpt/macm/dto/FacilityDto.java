package com.fpt.macm.dto;

public class FacilityDto {

	private int facilityId;
	private String facilityName;
	private String facilityCategoryName;
	private int quantityUsable;
	private int quantityBroken;

	public int getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(int facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public String getFacilityCategoryName() {
		return facilityCategoryName;
	}

	public void setFacilityCategoryName(String facilityCategoryName) {
		this.facilityCategoryName = facilityCategoryName;
	}

	public int getQuantityUsable() {
		return quantityUsable;
	}

	public void setQuantityUsable(int quantityUsable) {
		this.quantityUsable = quantityUsable;
	}

	public int getQuantityBroken() {
		return quantityBroken;
	}

	public void setQuantityBroken(int quantityBroken) {
		this.quantityBroken = quantityBroken;
	}

}
