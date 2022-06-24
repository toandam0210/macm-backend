package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.TournamentScheduleService;

@RestController
@RequestMapping("/api/tournamentschedule")
public class TournamentScheduleController {
	@Autowired
	TournamentScheduleService tournamentScheduleService;
	
	@PostMapping("/headclub/createpreview")
	ResponseEntity<ResponseMessage> createPreviewTournamentSchedule(@RequestParam String tournamentName, @RequestParam String startDate, 
			@RequestParam String finishDate, @RequestParam String startTime, @RequestParam String finishTime) {
		return new ResponseEntity<ResponseMessage>(tournamentScheduleService.createPreviewTournamentSchedule(tournamentName, startDate, finishDate, startTime, finishTime), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/addnewschedule/{tournamentId}")
	ResponseEntity<ResponseMessage> createTournamentSchedule(@PathVariable(name = "tournamentId") int tournamentId, 
			@RequestBody List<ScheduleDto> listPreview, @RequestParam boolean isOverwritten) {
		return new ResponseEntity<ResponseMessage>(tournamentScheduleService.createTournamenttSchedule(tournamentId, listPreview, isOverwritten), HttpStatus.OK);
	}
}
