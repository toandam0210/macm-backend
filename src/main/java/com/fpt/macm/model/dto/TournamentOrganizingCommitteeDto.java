package com.fpt.macm.model.dto;

public class TournamentOrganizingCommitteeDto implements Comparable<TournamentOrganizingCommitteeDto>{

	private int id;
	private String userName;
	private String userStudentId;
	private RoleEventDto roleTournamentDto;
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

	public String getUserStudentId() {
		return userStudentId;
	}

	public void setUserStudentId(String userStudentId) {
		this.userStudentId = userStudentId;
	}

	public RoleEventDto getRoleTournamentDto() {
		return roleTournamentDto;
	}

	public void setRoleTournamentDto(RoleEventDto roleTournamentDto) {
		this.roleTournamentDto = roleTournamentDto;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	@Override
	public int compareTo(TournamentOrganizingCommitteeDto o) {
		return o.getId() - this.getId();
	}

}
