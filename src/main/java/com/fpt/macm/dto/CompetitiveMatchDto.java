package com.fpt.macm.dto;

import java.time.LocalDateTime;

import com.fpt.macm.model.Area;
import com.fpt.macm.model.User;

public class CompetitiveMatchDto {
	private int id;
	private int round;
	private String firstStudentId;
	private String secondStudentId;
	private LocalDateTime time;
	private String area;
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
	
	public String getFirstStudentId() {
		return firstStudentId;
	}
	
	public void setFirstStudentId(String firstStudentId) {
		this.firstStudentId = firstStudentId;
	}
	
	public String getSecondStudentId() {
		return secondStudentId;
	}
	
	public void setSecondStudentId(String secondStudentId) {
		this.secondStudentId = secondStudentId;
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
