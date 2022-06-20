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

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.ResponseMessage;
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
	ResponseEntity<ResponseMessage> createEventSession(@PathVariable(name = "eventId") int eventId, @RequestBody EventSchedule eventSchedule) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createEventSession(eventId, eventSchedule), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/createpreview/{eventId}")
	ResponseEntity<ResponseMessage> createPreviewEventSchedule(@PathVariable(name = "eventId") int eventId, @RequestParam String startDate, 
			@RequestParam String finishDate, @RequestParam String startTime, @RequestParam String finishTime) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createPreviewEventSchedule(eventId, startDate, finishDate, startTime, finishTime), HttpStatus.OK);
	}
	
	@PostMapping("/headculture/addnewschedule/{eventId}")
	ResponseEntity<ResponseMessage> createTrainingSchedule(@PathVariable(name = "eventId") int eventId, 
			@RequestBody List<ScheduleDto> listPreview) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.createEventSchedule(eventId, listPreview), HttpStatus.OK);
	}
	
	@PutMapping("/headculture/updatesession/{eventScheduleId}")
	ResponseEntity<ResponseMessage> updateEventSessionTime(@PathVariable(name = "eventscheduleId") int eventscheduleId, @RequestBody CommonSchedule commonSchedule) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.updateEventSessionTime(eventscheduleId, commonSchedule), HttpStatus.OK);
	}
	
	@PutMapping("/headculture/deletesession/{eventScheduleId}")
	ResponseEntity<ResponseMessage> deleteEventSession(@PathVariable(name = "eventscheduleId") int eventscheduleId) {
		return new ResponseEntity<ResponseMessage>(eventScheduleService.deleteEventSession(eventscheduleId), HttpStatus.OK);
	}
	
}