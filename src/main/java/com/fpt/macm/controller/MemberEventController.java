package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.MemberEventService;

@RestController
@RequestMapping("/api/event")
public class MemberEventController {

	@Autowired
	MemberEventService memberEventService;

	@GetMapping("/headculture/getallmemberevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllMemberEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberOfEvent(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

}
