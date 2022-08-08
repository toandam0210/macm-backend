package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.AttendanceEventService;

@RestController
@RequestMapping("/api/event/headculture")
public class AttendanceEventController {

	@Autowired
	AttendanceEventService attendanceEventService;

	@PutMapping("/takeattendanceevent/{eventId}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> takeAttendanceEvent(@PathVariable(name = "eventId") int eventId,
			@PathVariable(name = "studentId") String studentId, @RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(
				attendanceEventService.takeAttendanceByStudentId(studentId, status, eventId), HttpStatus.OK);
	}

	@GetMapping("/checkattendance/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> checkAttendanceByEventId(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(attendanceEventService.checkAttendanceStatusByEventId(eventId),
				HttpStatus.OK);
	}

	@GetMapping("/getlistoldeventtotakeattendancebysemester/{semesterName}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getListOldEventToTakeAttendanceBySemester(
			@PathVariable(name = "semesterName") String semesterName) {
		return new ResponseEntity<ResponseMessage>(
				attendanceEventService.getListOldEventToTakeAttendanceBySemester(semesterName), HttpStatus.OK);
	}
}
