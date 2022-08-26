package com.fpt.macm.model.dto;

public class UserActiveReportDto {
	private int totalActive;
	private double percentActive;
	private int totalUser;
	private double percentTotalUser;
	public int getTotalActive() {
		return totalActive;
	}
	public void setTotalActive(int totalActive) {
		this.totalActive = totalActive;
	}
	public double getPercentActive() {
		return percentActive;
	}
	public void setPercentActive(double percentActive) {
		this.percentActive = percentActive;
	}
	public int getTotalUser() {
		return totalUser;
	}
	public void setTotalUser(int totalUser) {
		this.totalUser = totalUser;
	}
	public double getPercentTotalUser() {
		return percentTotalUser;
	}
	public void setPercentTotalUser(double percentTotalUser) {
		this.percentTotalUser = percentTotalUser;
	}
	
	
}
