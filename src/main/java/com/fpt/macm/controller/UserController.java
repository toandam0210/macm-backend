package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.service.UserService;

@RestController
@RequestMapping("/api/admin/hr")
public class UserController {

	
	@Autowired
	UserService userSerivce;
	
	@GetMapping("/getbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getStudentByStudentId(@PathVariable(name = "studentId") String studentId){
			return new ResponseEntity<ResponseMessage>(userSerivce.getUserByStudentId(studentId), HttpStatus.OK);
	}
	
	@GetMapping("/viceheadclub/getalladmin")
	ResponseEntity<ResponseMessage> getAllAdminForViceHeadClub(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
			return new ResponseEntity<ResponseMessage>(userSerivce.getAllAdminForViceHeadClub(pageNo,pageSize,sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getalladmin")
	ResponseEntity<ResponseMessage> getAllAdminForHeadClub(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
			return new ResponseEntity<ResponseMessage>(userSerivce.getAllAdminForHeadClub(pageNo,pageSize,sortBy), HttpStatus.OK);
	}
		
	@PutMapping("/updateuser/{studentId}")
	ResponseEntity<ResponseMessage> updateAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestBody UserDto userDto){
		return new ResponseEntity<ResponseMessage>(userSerivce.updateUser(studentId, userDto), HttpStatus.OK);
	}
	
	@PutMapping("/deleteadmin/{studentId}")
	ResponseEntity<ResponseMessage> deleteAdminByStudentId(@PathVariable(name = "studentId") String studentId, @RequestBody Role role){
		return new ResponseEntity<ResponseMessage>(userSerivce.deleteAdmin(studentId, role), HttpStatus.OK);
	}
	
	@PostMapping("/uploadfilemember")
	ResponseEntity<ResponseMessage> addListMemberFromCsv(@RequestParam("file") MultipartFile file) throws Exception{
		return new ResponseEntity<ResponseMessage>(userSerivce.addListMemberAndCollaboratorFromFileCsv(file),HttpStatus.OK);
	}
	
	@GetMapping("/getallmemberandcollaborator")
	ResponseEntity<ResponseMessage> getAllMemberAndCollaborator(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
			return new ResponseEntity<ResponseMessage>(userSerivce.getAllMemberAndCollaborator(pageNo,pageSize,sortBy), HttpStatus.OK);
	}
	
	@PostMapping("/adduser")
	ResponseEntity<ResponseMessage> addNewMember(@RequestBody User user){
		return new ResponseEntity<ResponseMessage>(userSerivce.addAnMemberOrCollaborator(user),HttpStatus.OK);
	}
	
	@PutMapping("/updatestatus")
	ResponseEntity<ResponseMessage> updateListStatusForUser(@RequestBody List<User> users){
		return new ResponseEntity<ResponseMessage>(userSerivce.updateListStatusForUser(users),HttpStatus.OK);
	}
	
}
