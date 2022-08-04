package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.EventScheduleService;

@RestController
@RequestMapping("/api/eventschedule")
public class EventScheduleController {
	
	@Autowired
	EventScheduleService eventScheduleService;
	
	@GetMapping("/geteventschedule")
	ResponseEntity<ResponseMessage> getEventSchedule(){
			return new ResponseEntity<ResponseMessage>(eventScheduleService.getListEventSchedule(), HttpStatus.OK);
	}
	
	@GetMapping("/geteventschedulebyevent/{eventId}")
	ResponseEntity<ResponseMessage> getEventScheduleByEvent(@PathVariable(name = "eventId") int eventId){
			return new ResponseEntity<ResponseMessage>(eventScheduleService.getListEventScheduleByEvent(eventId), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/addnewsession/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> createEventSession(@PathVariable(name = "eventId") int eventId, @RequestBody EventSchedule eventSchedule) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createEventSession(eventId, eventSchedule), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/createpreview")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> createPreviewEventSchedule(@RequestParam String eventName, @RequestParam String startDate, 
			@RequestParam String finishDate, @RequestParam String startTime, @RequestParam String finishTime) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createPreviewEventSchedule(eventName, startDate, finishDate, startTime, finishTime), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/addnewschedule/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> createEventSchedule(@PathVariable(name = "eventId") int eventId, 
			@RequestBody List<ScheduleDto> listPreview, @RequestParam Boolean isOverwritten) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createEventSchedule(eventId, listPreview, isOverwritten), HttpStatus.OK);
	}
	
	@GetMapping("/geteventsessionbydate")
	ResponseEntity<ResponseMessage> getEventSessionByDate(@RequestParam String date) {
			return new ResponseEntity<ResponseMessage>(eventScheduleService.getEventSessionByDate(date), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/updatepreview/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> updatePreviewEventSchedule(@PathVariable(name = "eventId") int eventId, @RequestParam String startDate, 
			@RequestParam String finishDate, @RequestParam String startTime, @RequestParam String finishTime) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.updatePreviewEventSchedule(eventId, startDate, finishDate, startTime, finishTime), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/updateschedule/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> updateEventSchedule(@PathVariable(name = "eventId") int eventId, 
			@RequestBody List<ScheduleDto> listPreview, @RequestParam Boolean isOverwritten) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.updateEventSchedule(eventId, listPreview, isOverwritten), HttpStatus.OK);
	}
	
	@GetMapping("/headculture/getperiodtimeofevent/{eventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasure')")
	ResponseEntity<ResponseMessage> getPeriodTimeOfEvent(@PathVariable(name = "eventId") int eventId){
			return new ResponseEntity<ResponseMessage>(eventScheduleService.getPeriodTimeOfEvent(eventId), HttpStatus.OK);
	}
}
