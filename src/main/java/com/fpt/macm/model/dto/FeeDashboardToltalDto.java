package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

public class FeeDashboardToltalDto {

	private String semester;
	private double totalIncome;
	private double totalSpend;
	private double totalIncomePercent;
	private double totalSpendPercent;
	List<FeeDashboardDto> feeDashboardDtos = new ArrayList<FeeDashboardDto>();

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
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

	public double getTotalIncomePercent() {
		return totalIncomePercent;
	}

	public void setTotalIncomePercent(double totalIncomePercent) {
		this.totalIncomePercent = totalIncomePercent;
	}

	public double getTotalSpendPercent() {
		return totalSpendPercent;
	}

	public void setTotalSpendPercent(double totalSpendPercent) {
		this.totalSpendPercent = totalSpendPercent;
	}

	public List<FeeDashboardDto> getFeeDashboardDtos() {
		return feeDashboardDtos;
	}

	public void setFeeDashboardDtos(List<FeeDashboardDto> feeDashboardDtos) {
		this.feeDashboardDtos = feeDashboardDtos;
	}

}
