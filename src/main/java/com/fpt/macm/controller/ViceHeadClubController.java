package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.service.UserService;

@RestController
@RequestMapping("/api/vicehead")
public class ViceHeadClubController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/getbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getStudentByStudentId(@PathVariable(name = "studentId") String studentId){
			return new ResponseEntity<ResponseMessage>(userService.getStudentByStudentId(studentId), HttpStatus.OK);
	}
	
	@PutMapping("/updaterolebystudentid/{studentId}")
	ResponseEntity<ResponseMessage> addNewMark(@PathVariable(name = "studentId") String studentId, @RequestBody Role role){
		return new ResponseEntity<ResponseMessage>(userService.updateMemberToAdminByStudentId(studentId, role), HttpStatus.OK);
	}
}
