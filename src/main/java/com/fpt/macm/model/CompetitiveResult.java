package com.fpt.macm.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "competitive_type")
public class CompetitiveResult {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private LocalDateTime time;
	
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "match_id", referencedColumnName = "id")
	private CompetitiveMatch match;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "areaId")
	private Area area;
	
	@Column
	private int firstPoint;
	
	@Column
	private int secondPoint;
	
	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public CompetitiveMatch getMatch() {
		return match;
	}

	public void setMatch(CompetitiveMatch match) {
		this.match = match;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public int getFirstPoint() {
		return firstPoint;
	}

	public void setFirstPoint(int firstPoint) {
		this.firstPoint = firstPoint;
	}

	public int getSecondPoint() {
		return secondPoint;
	}

	public void setSecondPoint(int secondPoint) {
		this.secondPoint = secondPoint;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
