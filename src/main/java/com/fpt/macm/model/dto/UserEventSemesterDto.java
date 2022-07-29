package com.fpt.macm.model.dto;

public class UserEventSemesterDto implements Comparable<UserEventSemesterDto>{

	private String userName;
	private String studentId;
	private boolean isJoin;
	private EventDto eventDto;

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

	public boolean isJoin() {
		return isJoin;
	}

	public void setJoin(boolean isJoin) {
		this.isJoin = isJoin;
	}

	public EventDto getEventDto() {
		return eventDto;
	}

	public void setEventDto(EventDto eventDto) {
		this.eventDto = eventDto;
	}

	@Override
	public int compareTo(UserEventSemesterDto o) {
		// TODO Auto-generated method stub
		return this.getEventDto().getStartDate().compareTo(o.getEventDto().getStartDate()) * -1;
	}

}
