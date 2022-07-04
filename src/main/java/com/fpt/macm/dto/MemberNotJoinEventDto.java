package com.fpt.macm.dto;

public class MemberNotJoinEventDto {
	private int userId;
	private String userName;
	private String userMail;
	private String userStudentId;
	private boolean registeredStatus;
	private RoleEventDto roleEventDto;
	private String roleInClub;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUserStudentId() {
		return userStudentId;
	}
	public void setUserStudentId(String userStudentId) {
		this.userStudentId = userStudentId;
	}
	public boolean isRegisteredStatus() {
		return registeredStatus;
	}
	public void setRegisteredStatus(boolean registeredStatus) {
		this.registeredStatus = registeredStatus;
	}
	public RoleEventDto getRoleEventDto() {
		return roleEventDto;
	}
	public void setRoleEventDto(RoleEventDto roleEventDto) {
		this.roleEventDto = roleEventDto;
	}
	public String getRoleInClub() {
		return roleInClub;
	}
	public void setRoleInClub(String roleInClub) {
		this.roleInClub = roleInClub;
	}
	
}
