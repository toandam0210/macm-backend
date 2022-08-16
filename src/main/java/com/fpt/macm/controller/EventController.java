package com.fpt.macm.controller;

import java.time.LocalDate;
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

import com.fpt.macm.model.dto.EventCreateDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

	@Autowired
	EventService eventService;

	@PostMapping("/headculture/createevent/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> createEvent(@PathVariable(name = "studentId") String studentId,
			@RequestBody EventCreateDto eventCreateDto, @RequestParam boolean isOverwritten) {
		return new ResponseEntity<ResponseMessage>(eventService.createEvent(studentId, eventCreateDto, isOverwritten),
				HttpStatus.OK);
	}

	@GetMapping("/geteventsbyname")
	ResponseEntity<ResponseMessage> getEventByName(@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(eventService.getEventsByName(name, pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}

	@PutMapping("/headculture/updatebeforeevent/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateBeforeEvent(@PathVariable(name = "eventId") int id,
			@RequestBody Event event) {
		return new ResponseEntity<ResponseMessage>(eventService.updateBeforeEvent(id, event), HttpStatus.OK);
	}

	@PutMapping("/headculture/deleteevent/{eventId}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> deleteEvent(@PathVariable(name = "eventId") int id,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(eventService.deleteEvent(studentId, id), HttpStatus.OK);
	}

	@GetMapping("/geteventsbydate")
	ResponseEntity<ResponseMessage> getEventByDate(@RequestParam LocalDate startDate,
			@RequestParam LocalDate finishDate, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(
				eventService.getEventsByDate(startDate, finishDate, pageNo, pageSize), HttpStatus.OK);
	}

	@GetMapping("/geteventsbysemester")
	ResponseEntity<ResponseMessage> getEventBySemester(@RequestParam(defaultValue = "") String semester,
			@RequestParam(defaultValue = "0") int month, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(eventService.getEventsBySemester(semester, month, pageNo, pageSize),
				HttpStatus.OK);
	}

	@PutMapping("/headculture/updateafterevent/{eventId}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateAfterEvent(@PathVariable(name = "eventId") int id,
			@PathVariable(name = "studentId") String studentId, @RequestParam double money,
			@RequestParam(defaultValue = "true") boolean isIncurred,
			@RequestParam(defaultValue = "true") boolean isUseClubFund) {
		return new ResponseEntity<ResponseMessage>(
				eventService.updateAfterEvent(studentId, id, money, isIncurred, isUseClubFund), HttpStatus.OK);
	}

	@GetMapping("/geteventbyid/{eventId}")
	ResponseEntity<ResponseMessage> getEventById(@PathVariable(name = "eventId") int id) {
		return new ResponseEntity<ResponseMessage>(eventService.getEventById(id), HttpStatus.OK);
	}

	@GetMapping("/geteventsbysemesterandstudentid/{studentId}")
	ResponseEntity<ResponseMessage> getEventBySemesterAndStudentId(@PathVariable(name = "studentId") String studentId,
			@RequestParam(defaultValue = "") String semester, @RequestParam(defaultValue = "0") int month,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(
				eventService.getEventsBySemesterAndStudentId(semester, studentId, month, pageNo, pageSize),
				HttpStatus.OK);
	}

	@GetMapping("/getalleventhasjoinedbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> getAllEventHasJoinedByStudentId(@PathVariable(name = "studentId") String studentId,
			@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(
				eventService.getAllEventHasJoinedByStudentId(studentId, pageNo, pageSize), HttpStatus.OK);
	}

	@GetMapping("/getallupcomingevent")
	ResponseEntity<ResponseMessage> getAllUpcomingEvent(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(eventService.getAllUpcomingEvent(pageNo, pageSize), HttpStatus.OK);
	}

	@GetMapping("/getallongoingevent")
	ResponseEntity<ResponseMessage> getAllOngoingEvent(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(eventService.getAllOngoingEvent(pageNo, pageSize), HttpStatus.OK);
	}

	@GetMapping("/getallclosedevent")
	ResponseEntity<ResponseMessage> getAllClosedEvent(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize) {
		return new ResponseEntity<ResponseMessage>(eventService.getAllClosedEvent(pageNo, pageSize), HttpStatus.OK);
	}

	@PutMapping("/headculture/editroleevent/{eventId}")
	ResponseEntity<ResponseMessage> editRoleEvent(@PathVariable(name = "eventId") int eventId,
			@RequestBody List<RoleEventDto> rolesEventDto) {
		return new ResponseEntity<ResponseMessage>(eventService.editRoleEvent(eventId, rolesEventDto), HttpStatus.OK);
	}

}
