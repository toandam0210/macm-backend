package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.response.ResponseMessage;

public interface RoleService {
	ResponseMessage addListRole(List<Role> roles);
	ResponseMessage getAllRole();
	ResponseMessage getRoleForViceHeadClub();
}
