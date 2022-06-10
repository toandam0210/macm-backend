package com.fpt.macm.dto;

import java.time.LocalDateTime;

public class FacilityReportDto {

	private LocalDateTime createdOn;
	private String description;

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
