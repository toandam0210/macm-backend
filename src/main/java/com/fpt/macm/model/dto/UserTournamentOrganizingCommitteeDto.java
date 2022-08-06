package com.fpt.macm.model.dto;

import com.fpt.macm.model.entity.User;

public class UserTournamentOrganizingCommitteeDto {

	private User user;
	private int roleId;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
