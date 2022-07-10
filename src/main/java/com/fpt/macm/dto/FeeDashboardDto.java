package com.fpt.macm.dto;

public class FeeDashboardDto {

	private String semester;
	private int month;
	private double totalIncome;
	private double totalSpend;

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}

	public double getTotalSpend() {
		return totalSpend;
	}

	public void setTotalSpend(double totalSpend) {
		this.totalSpend = totalSpend;
	}

}
