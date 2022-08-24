package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.MemberEventService;

@RestController
@RequestMapping("/api/event")
public class MemberEventController {

	@Autowired
	MemberEventService memberEventService;

	@PutMapping("/headculture/updatelistmembereventrole")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateListMemberEventRole(@RequestBody List<MemberEventDto> membersEventDto) {
		return new ResponseEntity<ResponseMessage>(memberEventService.updateListMemberEventRole(membersEventDto),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getallmembercanceljoinevent/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllMemberCancelJoinEvent(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getAllMemberCancelJoinEvent(eventId),
				HttpStatus.OK);
	}

	@PutMapping("/treasurer/updatemembereventpaymentstatus/{memberEventId}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateMemberEventPaymentStatus(
			@PathVariable(name = "memberEventId") int memberEventId,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.updateMemberEventPaymentStatus(studentId, memberEventId), HttpStatus.OK);
	}

	@GetMapping("treasurer/getreportpaymentstatus/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getReportPaymentStatus(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getReportPaymentStatusByEventId(eventId),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getmemberjoinevent/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllMemberJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestParam(defaultValue = "0") int filterIndex) {
		return new ResponseEntity<ResponseMessage>(
				memberEventService.getAllMemberJoinEventByRoleEventId(eventId, filterIndex), HttpStatus.OK);
	}

	@GetMapping("/headculture/getlistmembereventtoupdaterole/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getListMemberEventToUpdateRole(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getListMemberEventToUpdateRole(eventId),
				HttpStatus.OK);
	}

	@GetMapping("/getallrolebyeventid/{eventId}")
	ResponseEntity<ResponseMessage> getAllRoleByEventId(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getAllRoleByEventId(eventId), HttpStatus.OK);
	}

	@GetMapping("/getallorganizingcommitteerolebyeventid/{eventId}")
	ResponseEntity<ResponseMessage> getAllOrganizingCommitteeRoleByEventId(
			@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getAllOrganizingCommitteeRoleByEventId(eventId),
				HttpStatus.OK);
	}

	@GetMapping("/headculture/getlistmembernotjoin/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getListMemberNotJoinEvent(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getListMemberNotJoinEvent(eventId),
				HttpStatus.OK);
	}

	@PostMapping("/headculture/addlistmemberjoin/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> addListMemberJoinEvent(@PathVariable(name = "eventId") int eventId,
			@RequestBody List<MemberEventDto> listToJoin) {
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

	@PutMapping("/acceptrequesttojoinevent/{memberEventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> acceptRequestToJoinEvent(@PathVariable(name = "memberEventId") int memberEventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.acceptRequestToJoinEvent(memberEventId),
				HttpStatus.OK);
	}

	@PutMapping("/declinerequesttojoinevent/{memberEventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> declineRequestToJoinEvent(@PathVariable(name = "memberEventId") int memberEventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.declineRequestToJoinEvent(memberEventId),
				HttpStatus.OK);
	}

	@PutMapping("/deletememberevent/{memberEventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> deleteMemberEvent(@PathVariable(name = "memberEventId") int memberEventId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.deleteMemberEvent(memberEventId), HttpStatus.OK);
	}

	@GetMapping("/getalleventbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getAllEventByStudentId(@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(memberEventService.getAllEventByStudentId(studentId), HttpStatus.OK);
	}
}
