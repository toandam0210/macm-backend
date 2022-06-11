//package com.fpt.macm.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fpt.macm.model.ResponseMessage;
//import com.fpt.macm.service.UserService;
//
//@RestController
//@RequestMapping("/api")
//public class LoginController {
//	
//	@Autowired
//	UserService userService;
//	
//	@GetMapping("/login")
//	ResponseEntity<ResponseMessage> getUserLogin(){
//		return new ResponseEntity<ResponseMessage>(userService.userLogin(), HttpStatus.OK);
//	}
//}
