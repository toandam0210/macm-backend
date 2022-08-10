package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

	@InjectMocks
	RoleService roleService = new RoleServiceImpl();
	
	@Mock
	RoleRepository roleRepository;
	
	private Role role() {
		Role role = new Role();
		role.setId(1);
		role.setName("Head_Club");
		return role;
	}
	
	@Test
	public void addListRoleCaseSuccess() {
		ResponseMessage responseMessage = roleService.addListRole(Arrays.asList(role()));
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getAllRoleCaseSuccess() {
		when(roleRepository.findAll()).thenReturn(Arrays.asList(role()));
		
		ResponseMessage responseMessage = roleService.getAllRole();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllRoleCaseException() {
		when(roleRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = roleService.getAllRole();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getRoleForViceHeadClubCaseSuccess() {
		when(roleRepository.findRoleForViceHead()).thenReturn(Arrays.asList(role()));
		
		ResponseMessage responseMessage = roleService.getRoleForViceHeadClub();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getRoleForViceHeadClubCaseFail() {
		when(roleRepository.findRoleForViceHead()).thenReturn(Arrays.asList());
		
		ResponseMessage responseMessage = roleService.getRoleForViceHeadClub();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getRoleForViceHeadClubCaseException() {
		when(roleRepository.findRoleForViceHead()).thenReturn(null);
		
		ResponseMessage responseMessage = roleService.getRoleForViceHeadClub();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
