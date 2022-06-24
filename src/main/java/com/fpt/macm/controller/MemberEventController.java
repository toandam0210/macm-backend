package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.MemberEventDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.MemberEventService;

@RestController
@RequestMapping("/api/event")
public class MemberEventController {

	@Autowired
	MemberEventService memberEventService;

	@PutMapping("/headculture/updatelistmembereventrole")
	ResponseEntity<ResponseMessage> updateListMemberEventRole(@RequestBody List<MemberEventDto> membersEventDto) {
		return new ResponseEntity<ResponseMessage>(memberEventService.updateListMemberEventRole(membersEventDto),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getallmembercanceljoinevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllMemberCancelJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberCancelJoinEvent(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatemembereventpaymentstatus/{memberEventId}")
	ResponseEntity<ResponseMessage> updateMemberEventPaymentStatus(
			@PathVariable(name = "memberEventId") int memberEventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.updateMemberEventPaymentStatus(memberEventId),
				HttpStatus.OK);
	}

	@GetMapping("treasurer/getreportpaymentstatus/{eventId}")
	ResponseEntity<ResponseMessage> getReportPaymentStatus(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getReportPaymentStatusByEventId(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@GetMapping("/headculture/getmemberjoinevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllMemberJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int filterIndex, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberJoinEventByRoleEventId(eventId, filterIndex, pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}
}
