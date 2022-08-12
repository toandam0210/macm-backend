package com.fpt.macm.model.dto;

public class ActivityReportDto {

	private int totalTournamentAndEvent;
	private int averageMembersPerTournamentAndEvent;
	private int averageCommitteePerTournamentAndEvent;

	public int getTotalTournamentAndEvent() {
		return totalTournamentAndEvent;
	}

	public void setTotalTournamentAndEvent(int totalTournamentAndEvent) {
		this.totalTournamentAndEvent = totalTournamentAndEvent;
	}

	public int getAverageMembersPerTournamentAndEvent() {
		return averageMembersPerTournamentAndEvent;
	}

	public void setAverageMembersPerTournamentAndEvent(int averageMembersPerTournamentAndEvent) {
		this.averageMembersPerTournamentAndEvent = averageMembersPerTournamentAndEvent;
	}

	public int getAverageCommitteePerTournamentAndEvent() {
		return averageCommitteePerTournamentAndEvent;
	}

	public void setAverageCommitteePerTournamentAndEvent(int averageCommitteePerTournamentAndEvent) {
		this.averageCommitteePerTournamentAndEvent = averageCommitteePerTournamentAndEvent;
	}

}
