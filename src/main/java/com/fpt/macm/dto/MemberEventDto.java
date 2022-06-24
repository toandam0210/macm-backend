package com.fpt.macm.dto;

public class MemberEventDto {

	private int id;
	private String userName;
	private String userMail;
	private String userStudentId;
	private boolean attendanceStatus;
	private RoleEventDto roleEventDto;
	private String roleInClub;
	private boolean paymentStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public boolean isAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(boolean attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public RoleEventDto getRoleEventDto() {
		return roleEventDto;
	}

	public void setRoleEventDto(RoleEventDto roleEventDto) {
		this.roleEventDto = roleEventDto;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getRoleInClub() {
		return roleInClub;
	}

	public void setRoleInClub(String roleInClub) {
		this.roleInClub = roleInClub;
	}

}
