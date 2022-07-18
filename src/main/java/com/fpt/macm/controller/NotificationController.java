package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.NotificationService;

@RestController
@RequestMapping("/api/notification")
public class NotificationController {

	@Autowired
	NotificationService notificationService;
	
	@GetMapping("/getallnotification")
	ResponseEntity<ResponseMessage> getAllNotification(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
		return new ResponseEntity<ResponseMessage>(notificationService.getAllNotification(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/checkpaymentstatus/{studentId}")
	ResponseEntity<ResponseMessage> checkPaymentStatus(@PathVariable(name = "studentId") String studentId){
		return new ResponseEntity<ResponseMessage>(notificationService.checkPaymentStatus(studentId), HttpStatus.OK);
	}
}
