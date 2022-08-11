package com.fpt.macm.model.dto;

import java.time.LocalDate;

public class UpcomingActivityDto implements Comparable<UpcomingActivityDto>{

	private int id;
	private String name;
	private int type;
	private LocalDate date;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public int compareTo(UpcomingActivityDto o) {
		return o.getDate().compareTo(this.getDate());
	}

}
