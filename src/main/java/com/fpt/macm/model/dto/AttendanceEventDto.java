package com.fpt.macm.model.dto;

import java.time.LocalDate;

public class AttendanceEventDto implements Comparable<AttendanceEventDto>{

	private int id;
	private int eventId;
	private String eventName;
	private String name;
	private String studentId;
	private int status;
	private LocalDate date;

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	@Override
	public int compareTo(AttendanceEventDto o) {
		// TODO Auto-generated method stub
		return this.getId() - o.getId();
	}

}
