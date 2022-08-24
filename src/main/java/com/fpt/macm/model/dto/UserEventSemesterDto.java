package com.fpt.macm.model.dto;

public class UserEventSemesterDto implements Comparable<UserEventSemesterDto> {

	private String userName;
	private String studentId;
	private EventDto eventDto;
	private String registerStatus;

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

	public EventDto getEventDto() {
		return eventDto;
	}

	public void setEventDto(EventDto eventDto) {
		this.eventDto = eventDto;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	@Override
	public int compareTo(UserEventSemesterDto o) {
		// TODO Auto-generated method stub
		return this.getEventDto().getStartDate().compareTo(o.getEventDto().getStartDate()) * -1;
	}

}
