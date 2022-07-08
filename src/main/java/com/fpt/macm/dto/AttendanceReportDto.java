package com.fpt.macm.dto;

import java.time.LocalDate;

public class AttendanceReportDto {
	private LocalDate date;
	private int totalUserJoin;
	private int totalAttendInTrainingSession;
	private int totalAbsentInTrainingSession;
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getTotalUserJoin() {
		return totalUserJoin;
	}
	public void setTotalUserJoin(int totalUserJoin) {
		this.totalUserJoin = totalUserJoin;
	}
	public int getTotalAttendInTrainingSession() {
		return totalAttendInTrainingSession;
	}
	public void setTotalAttendInTrainingSession(int totalAttendInTrainingSession) {
		this.totalAttendInTrainingSession = totalAttendInTrainingSession;
	}
	public int getTotalAbsentInTrainingSession() {
		return totalAbsentInTrainingSession;
	}
	public void setTotalAbsentInTrainingSession(int totalAbsentInTrainingSession) {
		this.totalAbsentInTrainingSession = totalAbsentInTrainingSession;
	}
	
	
}
