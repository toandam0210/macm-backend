package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ScheduleDto {
	private LocalDate date;
	private String title;
	private LocalTime startTime;
	private LocalTime finishTime;
	private Boolean existed;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(LocalTime finishTime) {
		this.finishTime = finishTime;
	}
	public Boolean getExisted() {
		return existed;
	}
	public void setExisted(Boolean existed) {
		this.existed = existed;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
