package com.fpt.macm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RoleRepository;
import com.fpt.macm.utils.Utils;

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
			for (Role role : roles) {
				Utils.convertNameOfRole(role);
			}
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
