package com.fpt.macm.dto;

import com.fpt.macm.model.Role;

public class UserDto {
	private String studentId;
	private String email;
	private String phoneNumber;
	private String currentAddress;
	private Role role;
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
}
