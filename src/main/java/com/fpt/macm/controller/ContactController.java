package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.Contact;
import com.fpt.macm.model.response.ResponseMessage;
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
	
	@GetMapping("/getallsocialnetwork")
	ResponseEntity<ResponseMessage> getAllSocialNetwork(){
			return new ResponseEntity<ResponseMessage>(contactService.getAllSocialNetwork(), HttpStatus.OK);
	}
	
	@PutMapping("/headcommunication/updatecontact")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> updateContact(@RequestBody Contact contact){
		return new ResponseEntity<ResponseMessage>(contactService.updateContact(contact),HttpStatus.OK);
	}

}
