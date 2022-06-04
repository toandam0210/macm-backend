package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.service.AdminService;

@RestController
@RequestMapping("/api/admin/vicehead")
public class ViceHeadClubController {
	
	@Autowired
	AdminService userService;
	
	@GetMapping("/getbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getStudentByStudentId(@PathVariable(name = "studentId") String studentId){
			return new ResponseEntity<ResponseMessage>(userService.getUserByStudentId(studentId), HttpStatus.OK);
	}
	
	@PutMapping("/updaterolebystudentid/{studentId}")
	ResponseEntity<ResponseMessage> updateMemberToAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestBody Role role){
		return new ResponseEntity<ResponseMessage>(userService.updateMemberToAdminByStudentId(studentId, role), HttpStatus.OK);
	}
	
	@GetMapping("/getalladmin")
	ResponseEntity<ResponseMessage> getAllAdmin(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
			return new ResponseEntity<ResponseMessage>(userService.getAllAdmin(pageNo,pageSize,sortBy), HttpStatus.OK);
	}
	
	@PutMapping("/deleteadmin/{studentId}")
	ResponseEntity<ResponseMessage> updateAdminToMemberByStudentId(@PathVariable(name = "studentId") String studentId, @RequestBody Role role){
		return new ResponseEntity<ResponseMessage>(userService.updateAdminToMemberByStudentId(studentId, role), HttpStatus.OK);
	}
	
	@PutMapping("/updateadmin/{studentId}")
	ResponseEntity<ResponseMessage> updateAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestBody UserDto userDto){
		return new ResponseEntity<ResponseMessage>(userService.updateAdmin(studentId, userDto), HttpStatus.OK);
	}
	
}
