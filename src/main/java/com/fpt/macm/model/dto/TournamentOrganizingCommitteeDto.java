package com.fpt.macm.model.dto;

public class TournamentOrganizingCommitteeDto implements Comparable<TournamentOrganizingCommitteeDto> {

	private int id;
	private String userName;
	private String userStudentId;
	private TournamentRoleDto tournamentRoleDto;
	private boolean paymentStatus;
	private String registerStatus;

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

	public TournamentRoleDto getTournamentRoleDto() {
		return tournamentRoleDto;
	}

	public void setTournamentRoleDto(TournamentRoleDto tournamentRoleDto) {
		this.tournamentRoleDto = tournamentRoleDto;
	}

	public boolean isPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(boolean paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

	@Override
	public int compareTo(TournamentOrganizingCommitteeDto o) {
		return o.getId() - this.getId();
	}

}
