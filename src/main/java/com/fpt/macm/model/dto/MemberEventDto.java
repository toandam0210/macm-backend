package com.fpt.macm.model.dto;

public class MemberEventDto implements Comparable<MemberEventDto> {

	private int id;
	private int userId;
	private String userName;
	private String userMail;
	private String userStudentId;
	private String registerStatus;
	private EventRoleDto eventRoleDto;
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

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	public EventRoleDto getEventRoleDto() {
		return eventRoleDto;
	}

	public void setEventRoleDto(EventRoleDto eventRoleDto) {
		this.eventRoleDto = eventRoleDto;
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public int compareTo(MemberEventDto o) {
		return o.getId() - this.getId();
	}

}
