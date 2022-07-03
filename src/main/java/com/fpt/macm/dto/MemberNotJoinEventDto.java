package com.fpt.macm.dto;

public class MemberNotJoinEventDto {
	
	private int id;
	private int userId;
	private String userName;
	private String userMail;
	private String userStudentId;
	private boolean registeredStatus;
	private String roleInClub;
	private RoleEventDto roleEventDto;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getRoleInClub() {
		return roleInClub;
	}
	
	public void setRoleInClub(String roleInClub) {
		this.roleInClub = roleInClub;
	}

	public RoleEventDto getRoleEventDto() {
		return roleEventDto;
	}

	public void setRoleEventDto(RoleEventDto roleEventDto) {
		this.roleEventDto = roleEventDto;
	}
	
	
}
