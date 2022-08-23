package com.fpt.macm.model.dto;

public class ActivityReportDto {

	private int totalTournament;
	private int totalEvent;
	private double averageJoinTournament;
	private double averageJoinEvent;

	public int getTotalTournament() {
		return totalTournament;
	}

	public void setTotalTournament(int totalTournament) {
		this.totalTournament = totalTournament;
	}

	public int getTotalEvent() {
		return totalEvent;
	}

	public void setTotalEvent(int totalEvent) {
		this.totalEvent = totalEvent;
	}

	public double getAverageJoinTournament() {
		return averageJoinTournament;
	}

	public void setAverageJoinTournament(double averageJoinTournament) {
		this.averageJoinTournament = averageJoinTournament;
	}

	public double getAverageJoinEvent() {
		return averageJoinEvent;
	}

	public void setAverageJoinEvent(double averageJoinEvent) {
		this.averageJoinEvent = averageJoinEvent;
	}

}
