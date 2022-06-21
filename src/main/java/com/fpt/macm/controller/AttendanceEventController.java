package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.AttendanceEventService;

@RestController
@RequestMapping("/api/event/headculture")
public class AttendanceEventController {

	@Autowired
	AttendanceEventService attendanceEventService;
	
	@PutMapping("/takeattendanceevent/{memberEventId}")
	ResponseEntity<ResponseMessage> takeAttendanceEvent(@PathVariable(name = "memberEventId") int memberEventId){
		return new ResponseEntity<ResponseMessage>(attendanceEventService.takeAttendanceByMemberEventId(memberEventId), HttpStatus.OK);
	}
	
}
