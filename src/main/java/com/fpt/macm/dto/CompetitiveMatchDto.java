package com.fpt.macm.dto;

import java.time.LocalDateTime;

import com.fpt.macm.model.Area;
import com.fpt.macm.model.User;

public class CompetitiveMatchDto {
	private int id;
	private int round;
	private User firstPlayer;
	private User secondPlayer;
	private LocalDateTime time;
	private Area area;
	private Integer firstPoint;
	private Integer secondPoint;
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
	public User getFirstPlayer() {
		return firstPlayer;
	}
	public void setFirstPlayer(User firstPlayer) {
		this.firstPlayer = firstPlayer;
	}
	public User getSecondPlayer() {
		return secondPlayer;
	}
	public void setSecondPlayer(User secondPlayer) {
		this.secondPlayer = secondPlayer;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public Integer getFirstPoint() {
		return firstPoint;
	}
	public void setFirstPoint(Integer firstPoint) {
		this.firstPoint = firstPoint;
	}
	public Integer getSecondPoint() {
		return secondPoint;
	}
	public void setSecondPoint(Integer secondPoint) {
		this.secondPoint = secondPoint;
	}
	
}
