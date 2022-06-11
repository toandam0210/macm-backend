package com.fpt.macm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleRepository roleRepository;

	@Override
	public ResponseMessage addListRole(List<Role> roles) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			roleRepository.saveAll(roles);
			responseMessage.setData(roles);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllRole() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Role> roles = roleRepository.findAll();
			responseMessage.setData(roles);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getRoleForViceHeadClub() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Role> roles = roleRepository.findRoleForViceHead();
			responseMessage.setData(roles);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
