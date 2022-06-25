package com.fpt.macm.dto;

public class CompetitiveTypeDto {
	
	private int id;
	private double weightMin;
	private double weightMax;
	private boolean gender;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getWeightMin() {
		return weightMin;
	}
	public void setWeightMin(double weightMin) {
		this.weightMin = weightMin;
	}
	public double getWeightMax() {
		return weightMax;
	}
	public void setWeightMax(double weightMax) {
		this.weightMax = weightMax;
	}
	public boolean isGender() {
		return gender;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	
	
}
