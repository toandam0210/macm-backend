package com.fpt.macm.model.dto;

import java.time.LocalDate;

public class AttendanceStatusDto implements Comparable<AttendanceStatusDto>{

	private int id;
	private String name;
	private String studentId;
	private int status;
	private LocalDate date;
	private int trainingScheduleId;

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

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTrainingScheduleId() {
		return trainingScheduleId;
	}

	public void setTrainingScheduleId(int trainingScheduleId) {
		this.trainingScheduleId = trainingScheduleId;
	}

	@Override
	public int compareTo(AttendanceStatusDto o) {
		// TODO Auto-generated method stub
		return this.getId() - o.getId();
	}

}
