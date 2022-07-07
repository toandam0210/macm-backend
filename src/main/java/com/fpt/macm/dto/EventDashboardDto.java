package com.fpt.macm.dto;

public class EventDashboardDto {
	private String eventName;
	private int totalMemberJoin;
	private double totalRevenueInEvent;
	private double totalExpenditure;

	public int getTotalMemberJoin() {
		return totalMemberJoin;
	}
	public void setTotalMemberJoin(int totalMemberJoin) {
		this.totalMemberJoin = totalMemberJoin;
	}
	public double getTotalRevenueInEvent() {
		return totalRevenueInEvent;
	}
	public void setTotalRevenueInEvent(double totalRevenueInEvent) {
		this.totalRevenueInEvent = totalRevenueInEvent;
	}
	public double getTotalExpenditure() {
		return totalExpenditure;
	}
	public void setTotalExpenditure(double totalExpenditure) {
		this.totalExpenditure = totalExpenditure;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
}
