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
import com.fpt.macm.service.CommonScheduleService;

@RestController
@RequestMapping("/api/commonschedule")
public class CommonScheduleController {
	
	@Autowired
	CommonScheduleService commonScheduleService;
	
	@GetMapping("/getcommonschedule")
	ResponseEntity<ResponseMessage> getCommonSchedule(){
			return new ResponseEntity<ResponseMessage>(commonScheduleService.getCommonSchedule(), HttpStatus.OK);
	}
	
	@GetMapping("/getcommonsessionbydate")
	ResponseEntity<ResponseMessage> getCommonSessionByDate(@RequestParam String date) {
			return new ResponseEntity<ResponseMessage>(commonScheduleService.getCommonSessionByDate(date), HttpStatus.OK);
	}
	
	@GetMapping("/getcommonschedulebysemester/{semesterId}")
	ResponseEntity<ResponseMessage> getCommonScheduleBySemester(@PathVariable(name = "semesterId") int semesterId){
			return new ResponseEntity<ResponseMessage>(commonScheduleService.getCommonScheduleBySemester(semesterId), HttpStatus.OK);
	}
}
