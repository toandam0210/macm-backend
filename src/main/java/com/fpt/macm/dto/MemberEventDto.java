package com.fpt.macm.dto;

public class MemberEventDto {

	private int id;
	private String userName;
	private String userMail;
	private String userStudentId;
	private boolean registerStatus;
	private RoleEventDto roleEventDto;
	private String roleInClub;
	private double paymentValue;
	private double amountPerRegisterEstimate;
	private double amountPerRegisterActual;

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

	public boolean isRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(boolean registerStatus) {
		this.registerStatus = registerStatus;
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

	public double getPaymentValue() {
		return paymentValue;
	}

	public void setPaymentValue(double paymentValue) {
		this.paymentValue = paymentValue;
	}

	public double getAmountPerRegisterEstimate() {
		return amountPerRegisterEstimate;
	}

	public void setAmountPerRegisterEstimate(double amountPerRegisterEstimate) {
		this.amountPerRegisterEstimate = amountPerRegisterEstimate;
	}

	public double getAmountPerRegisterActual() {
		return amountPerRegisterActual;
	}

	public void setAmountPerRegisterActual(double amountPerRegisterActual) {
		this.amountPerRegisterActual = amountPerRegisterActual;
	}

}
