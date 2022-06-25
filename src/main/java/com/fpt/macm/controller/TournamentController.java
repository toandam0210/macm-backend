package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.dto.TournamentDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
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
	
	@GetMapping("/headclub/getalltournamentorganizingcommittee/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllTournamentOrganizingCommittee(@PathVariable(name = "tournamentId") int tournamentId){
		return new ResponseEntity<ResponseMessage>(tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(tournamentId), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updatetournamentorganizingcommitteerole")
	ResponseEntity<ResponseMessage> updateTournamentOrganizingCommitteeRole(@RequestBody List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto){
		return new ResponseEntity<ResponseMessage>(tournamentService.updateTournamentOrganizingCommitteeRole(tournamentOrganizingCommitteesDto), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/update/{tournamentId}")
	ResponseEntity<ResponseMessage> updateTournament(@PathVariable(name = "tournamentId") int tournamentId,  @RequestBody TournamentDto tournamentDto){
		return new ResponseEntity<ResponseMessage>(tournamentService.updateTournament(tournamentId,tournamentDto), HttpStatus.OK);
	}
	
	@DeleteMapping("/headclub/delete/{tournamentId}")
	ResponseEntity<ResponseMessage> delete(@PathVariable(name = "tournamentId") int tournamentId){
		return new ResponseEntity<ResponseMessage>(tournamentService.deleteTournamentById(tournamentId), HttpStatus.OK);
	}
	
}
