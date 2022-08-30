package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class TrainingScheduleDto implements Comparable<TrainingScheduleDto>{

	private int id;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime finishTime;
	private int totalAttend;
	private int totalSize;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public int getTotalAttend() {
		return totalAttend;
	}

	public void setTotalAttend(int totalAttend) {
		this.totalAttend = totalAttend;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	@Override
	public int compareTo(TrainingScheduleDto o) {
		return o.getDate().compareTo(this.getDate());
	}

}
