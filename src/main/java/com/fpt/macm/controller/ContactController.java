package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.Contact;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.SocialNetwork;
import com.fpt.macm.service.ContactService;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

	@Autowired
	ContactService contactService;
	
	@GetMapping("/getallcontact")
	ResponseEntity<ResponseMessage> getAllContact(){
			return new ResponseEntity<ResponseMessage>(contactService.getAllContact(), HttpStatus.OK);
	}
	
	@GetMapping("/headcommunication/getallsocialnetwork")
	ResponseEntity<ResponseMessage> getAllSocialNetwork(){
			return new ResponseEntity<ResponseMessage>(contactService.getAllSocialNetwork(), HttpStatus.OK);
	}
	
	@PutMapping("/headcommunication/updatecontact")
	ResponseEntity<ResponseMessage> updateContact(@RequestBody Contact contact){
		return new ResponseEntity<ResponseMessage>(contactService.updateContact(contact),HttpStatus.OK);
	}
	
	@PutMapping("/headcommunication/updatesocialnetwork")
	ResponseEntity<ResponseMessage> updateContact(@RequestBody List<SocialNetwork> socialNetworks){
		return new ResponseEntity<ResponseMessage>(contactService.updateSocialNetwork(socialNetworks),HttpStatus.OK);
	}
}
