package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.service.TournamentService;

@RestController
@RequestMapping("/api/tournament")
public class TournamentController {
	@Autowired
	TournamentService tournamentService;
	
	@PostMapping("/headclub/createtournament")
	ResponseEntity<ResponseMessage> createTournament(@RequestBody Tournament tournament) {
		return new ResponseEntity<ResponseMessage>(tournamentService.createTournament(tournament), HttpStatus.OK);
	}
}
