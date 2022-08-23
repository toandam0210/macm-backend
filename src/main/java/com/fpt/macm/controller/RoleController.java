package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.response.ResponseMessage;
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
	
	@GetMapping("/getroles")
	ResponseEntity<ResponseMessage> getAllRoles(){
		return new ResponseEntity<ResponseMessage>(roleService.getAllRole(), HttpStatus.OK);
	}
	@GetMapping("/viceheadclub/getroles")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getRolesForViceHeadClub(){
		return new ResponseEntity<ResponseMessage>(roleService.getRoleForViceHeadClub(), HttpStatus.OK);
	}
}
