package com.fpt.macm.model.dto;

public class ExhibitionPlayerDto {

	private int id;
	private boolean roleInTeam;
	private int playerId;
	private String playerName;
	private String playerStudentId;
	private boolean playerGender;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public boolean isRoleInTeam() {
		return roleInTeam;
	}

	public void setRoleInTeam(boolean roleInTeam) {
		this.roleInTeam = roleInTeam;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerStudentId() {
		return playerStudentId;
	}

	public void setPlayerStudentId(String playerStudentId) {
		this.playerStudentId = playerStudentId;
	}

	public boolean isPlayerGender() {
		return playerGender;
	}

	public void setPlayerGender(boolean playerGender) {
		this.playerGender = playerGender;
	}

}
