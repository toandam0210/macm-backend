package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.MemberEventDto;
import com.fpt.macm.dto.MemberNotJoinEventDto;
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
			@RequestParam(defaultValue = "0") int filterIndex) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberJoinEventByRoleEventId(eventId, filterIndex), HttpStatus.OK);
	}

	@GetMapping("/headculture/getlistmembereventtoupdaterole/{eventId}")
	ResponseEntity<ResponseMessage> getListMemberEventToUpdateRole(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getListMemberEventToUpdateRole(eventId),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getallroleevent")
	ResponseEntity<ResponseMessage> getAllRoleEvent() {
		return new ResponseEntity<ResponseMessage>(memberEventService.getAllEventRole(), HttpStatus.OK);
	}

	@GetMapping("/headculture/getlistmembernotjoin/{eventId}")
	ResponseEntity<ResponseMessage> getListMemberNotJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getListMemberNotJoinEvent(eventId, pageNo, pageSize), HttpStatus.OK);
	}

	@PostMapping("/headculture/addlistmemberjoin/{eventId}")
	ResponseEntity<ResponseMessage> addListMemberJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestBody List<MemberNotJoinEventDto> listToJoin) {
		return new ResponseEntity<ResponseMessage>(memberEventService.addListMemberJoinEvent(eventId, listToJoin),
				HttpStatus.OK);
	}

	@PostMapping("/registertojoinevent/{eventId}/{studentId}")
	ResponseEntity<ResponseMessage> registerToJoinEvent(@PathVariable(name = "eventId") int eventId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.registerToJoinEvent(eventId, studentId),
				HttpStatus.OK);
	}

	@PostMapping("/registertojoinorganizingcommittee/{eventId}/{studentId}/{roleEventId}")
	ResponseEntity<ResponseMessage> registerToJoinOrganizingCommittee(@PathVariable(name = "eventId") int eventId,
			@PathVariable(name = "studentId") String studentId, @PathVariable(name = "roleEventId") int roleEventId) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.registerToJoinOrganizingCommittee(eventId, studentId, roleEventId), HttpStatus.OK);
	}

	@PutMapping("/canceltojoinevent/{eventId}/{studentId}")
	ResponseEntity<ResponseMessage> cancelToJoinEvent(@PathVariable(name = "eventId") int eventId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.cancelToJoinEvent(eventId, studentId),
				HttpStatus.OK);
	}
}
