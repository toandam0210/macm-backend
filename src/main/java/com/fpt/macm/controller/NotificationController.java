package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/getallnotification")
	ResponseEntity<ResponseMessage> getAllNotification(){
		return new ResponseEntity<ResponseMessage>(notificationService.getAllNotification(), HttpStatus.OK);
	}
	
}
