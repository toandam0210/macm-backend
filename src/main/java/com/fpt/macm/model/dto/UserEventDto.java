package com.fpt.macm.model.dto;

public class UserEventDto {
	private String userName;
	private String userStudentId;
	private EventRoleDto eventRoleDto;
	private int eventId;
	private String eventName;
	private String registerStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStudentId() {
		return userStudentId;
	}

	public void setUserStudentId(String userStudentId) {
		this.userStudentId = userStudentId;
	}

	public EventRoleDto getEventRoleDto() {
		return eventRoleDto;
	}

	public void setEventRoleDto(EventRoleDto eventRoleDto) {
		this.eventRoleDto = eventRoleDto;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

}
