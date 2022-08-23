package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserAttendanceStatusDto {

	private String userName;
	private String studentId;
	private int status;
	private String title;
	private String description;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private LocalDate endDateEvent;
	private LocalTime endTimeEvent;
	private int type;
	private int id;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getEndDateEvent() {
		return endDateEvent;
	}

	public void setEndDateEvent(LocalDate endDateEvent) {
		this.endDateEvent = endDateEvent;
	}

	public LocalTime getEndTimeEvent() {
		return endTimeEvent;
	}

	public void setEndTimeEvent(LocalTime endTimeEvent) {
		this.endTimeEvent = endTimeEvent;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
