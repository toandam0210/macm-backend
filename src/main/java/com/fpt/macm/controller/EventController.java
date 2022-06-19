package com.fpt.macm.controller;

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

import com.fpt.macm.model.Event;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {

	@Autowired
	EventService eventService;
	
	@PostMapping("/headculture/createevent")
	ResponseEntity<ResponseMessage> createEvent(@RequestBody Event event) {
		return new ResponseEntity<ResponseMessage>(eventService.createEvent(event), HttpStatus.OK);
	}
	
	@GetMapping("/geteventsbyname")
	ResponseEntity<ResponseMessage> getEventByName(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(eventService.getEventsByName(name, pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@PutMapping("/headculture/updateevent")
	ResponseEntity<ResponseMessage> updateEvent(@PathVariable(name = "id") int id, @RequestBody Event event) {
		return new ResponseEntity<ResponseMessage>(eventService.updateEvent(id, event), HttpStatus.OK);
	}
	
	@PutMapping("/headculture/deleteevent")
	ResponseEntity<ResponseMessage> deleteEvent(@PathVariable(name = "id") int id) {
		return new ResponseEntity<ResponseMessage>(eventService.deleteEvent(id), HttpStatus.OK);
	}
	
	@GetMapping("/geteventsbydate")
	ResponseEntity<ResponseMessage> getEventByDate(@RequestParam String startDate, @RequestParam String finishDate) {
		return new ResponseEntity<ResponseMessage>(eventService.getEventsByDate(startDate, finishDate), HttpStatus.OK);
	}
	
	@GetMapping("/headculture/updatesession/{eventId}")
	ResponseEntity<ResponseMessage> getStartDateOfEvent(@PathVariable(name = "eventId") int eventId) {
		return new ResponseEntity<ResponseMessage>(eventService.getStartDateOfEvent(eventId), HttpStatus.OK);
	}
}
