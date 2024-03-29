package com.fpt.macm.model.dto;

import java.time.LocalDateTime;

public class CompetitiveMatchDto implements Comparable<CompetitiveMatchDto> {
	private int id;
	private int round;
	private LocalDateTime time;
	private String area;
	private boolean status;
	private PlayerMatchDto firstPlayer;
	private PlayerMatchDto secondPlayer;
	private int matchNo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRound() {
		return round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public PlayerMatchDto getFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(PlayerMatchDto firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public PlayerMatchDto getSecondPlayer() {
		return secondPlayer;
	}

	public void setSecondPlayer(PlayerMatchDto secondPlayer) {
		this.secondPlayer = secondPlayer;
	}

	@Override
	public int compareTo(CompetitiveMatchDto o) {
		// TODO Auto-generated method stub
		return this.getId() - o.getId();
	}

	public int getMatchNo() {
		return matchNo;
	}

	public void setMatchNo(int matchNo) {
		this.matchNo = matchNo;
	}

}
