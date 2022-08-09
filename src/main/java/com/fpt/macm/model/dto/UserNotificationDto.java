package com.fpt.macm.model.dto;

import java.time.LocalDateTime;

public class UserNotificationDto implements Comparable<UserNotificationDto>{

	private int id;
	private String message;
	private int notificationType;
	private int notificationTypeId;
	private LocalDateTime createdOn;
	private boolean isRead;
	private String userName;
	private String studentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(int notificationType) {
		this.notificationType = notificationType;
	}

	public int getNotificationTypeId() {
		return notificationTypeId;
	}

	public void setNotificationTypeId(int notificationTypeId) {
		this.notificationTypeId = notificationTypeId;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@Override
	public int compareTo(UserNotificationDto o) {
		return o.getCreatedOn().compareTo(this.getCreatedOn());
	}

}
