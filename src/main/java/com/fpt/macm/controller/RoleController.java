package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@PostMapping("/addroles")
	ResponseEntity<ResponseMessage> addRole(@RequestBody List<Role> roles){
		return new ResponseEntity<ResponseMessage>(roleService.addListRole(roles), HttpStatus.OK);
	}
}
