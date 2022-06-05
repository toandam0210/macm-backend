package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.ContactService;

@RestController
@RequestMapping("/api")
public class ContactController {

	@Autowired
	ContactService contactService;
	
	@GetMapping("/contact")
	ResponseEntity<ResponseMessage> getAllContact(){
			return new ResponseEntity<ResponseMessage>(contactService.getAllContact(), HttpStatus.OK);
	}
	
	@GetMapping("/contact/socialnetwork")
	ResponseEntity<ResponseMessage> getAllSocialNetwork(){
			return new ResponseEntity<ResponseMessage>(contactService.getAllSocialNetwork(), HttpStatus.OK);
	}
}
