package com.fpt.macm.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String name;
	
	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime finishTime;
	
	@Column
	private String description;
	
	@Column
	private int maxQuantityComitee;
	
	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<MemberEvent> membersEvent = new HashSet<MemberEvent>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(LocalDateTime finishTime) {
		this.finishTime = finishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxQuantityComitee() {
		return maxQuantityComitee;
	}

	public void setMaxQuantityComitee(int maxQuantityComitee) {
		this.maxQuantityComitee = maxQuantityComitee;
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

	public Set<MemberEvent> getMembersEvent() {
		return membersEvent;
	}

	public void setMembersEvent(Set<MemberEvent> membersEvent) {
		this.membersEvent = membersEvent;
	}
	
	
}
