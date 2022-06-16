package com.fpt.macm.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "member_event")
public class MemberEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "eventId")
	private Event event;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "role_in_event")
	private RoleEvent roleEvent;
	
	@Column
	private boolean attendanceStatus;
	
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

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public RoleEvent getRoleEvent() {
		return roleEvent;
	}

	public void setRoleEvent(RoleEvent roleEvent) {
		this.roleEvent = roleEvent;
	}

	public boolean getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(boolean attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
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
