package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;

public interface RoleService {
	ResponseMessage addListRole(List<Role> roles);
}
