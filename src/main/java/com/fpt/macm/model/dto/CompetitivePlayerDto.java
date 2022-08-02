package com.fpt.macm.model.dto;

public class CompetitivePlayerDto {

	private int id;
	private String playerName;
	private boolean playerGender;
	private String playerStudentId;
	private double weight;
	private double weightMin;
	private double weightMax;
	private int competitiveTypeId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isPlayerGender() {
		return playerGender;
	}

	public void setPlayerGender(boolean playerGender) {
		this.playerGender = playerGender;
	}

	public String getPlayerStudentId() {
		return playerStudentId;
	}

	public void setPlayerStudentId(String playerStudentId) {
		this.playerStudentId = playerStudentId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
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

	public int getCompetitiveTypeId() {
		return competitiveTypeId;
	}

	public void setCompetitiveTypeId(int competitiveTypeId) {
		this.competitiveTypeId = competitiveTypeId;
	}

}
