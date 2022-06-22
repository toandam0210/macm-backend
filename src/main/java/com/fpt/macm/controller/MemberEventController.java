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

	@GetMapping("/headculture/getalluserevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllUserEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllUserOfEventByEventId(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@GetMapping("/headculture/getallorganizingcommitteeevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllOrganizingCommitteeEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllOrganizingCommitteeOfEventByEventId(eventId, pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getallmemberevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllMemberEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberOfEventByEventId(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@PutMapping("/headculture/updateuserroleevent")
	ResponseEntity<ResponseMessage> updateUserRoleEvent(@RequestBody List<MemberEventDto> membersEventDto) {
		return new ResponseEntity<ResponseMessage>(memberEventService.updateUserRoleInEvent(membersEventDto),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getallusercanceljoinevent/{eventId}")
	ResponseEntity<ResponseMessage> getAllUserCancelJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllUserCancelJoinEvent(eventId, pageNo, pageSize, sortBy), HttpStatus.OK);
	}

	@PutMapping("/treasurer/updateusereventpaymentstatus/{memberEventId}")
	ResponseEntity<ResponseMessage> updateUserEventPaymentStatus(
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
}
