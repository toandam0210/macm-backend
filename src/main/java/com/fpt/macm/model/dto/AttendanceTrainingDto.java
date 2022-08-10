package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AttendanceTrainingDto implements Comparable<AttendanceTrainingDto>{

	private int trainingScheduleId;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private int status;

	public int getTrainingScheduleId() {
		return trainingScheduleId;
	}

	public void setTrainingScheduleId(int trainingScheduleId) {
		this.trainingScheduleId = trainingScheduleId;
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

	@Override
	public int compareTo(AttendanceTrainingDto o) {
		return this.getDate().compareTo(o.getDate());
	}

}
