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

import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CompetitiveService;

@RestController
@RequestMapping("/api/competitive")
public class CompetitiveController {

	@Autowired
	CompetitiveService competitiveService;

	@PostMapping("/headclub/addnewcompetitiveplayer/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> addNewCompetitivePlayer(
			@PathVariable(name = "competitiveTypeId") int competitiveTypeId, @RequestBody List<User> users) {
		return new ResponseEntity<ResponseMessage>(competitiveService.addNewCompetitivePlayer(users, competitiveTypeId),
				HttpStatus.OK);
	}

	@PutMapping("/headclub/updateweightplayer/{competitivePlayerId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateWeightForCompetitivePlayer(
			@PathVariable(name = "competitivePlayerId") int competitivePlayerId, @RequestParam double weight) {
		return new ResponseEntity<ResponseMessage>(
				competitiveService.updateWeightForCompetitivePlayer(competitivePlayerId, weight), HttpStatus.OK);
	}

	@PutMapping("/headclub/deletecompetitiveplayer/{competitivePlayerId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> deleteCompetitivePlayer(
			@PathVariable(name = "competitivePlayerId") int competitivePlayerId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.deleteCompetitivePlayer(competitivePlayerId),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/getlistplayerbracket/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getListPlayerBracket(
			@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.getListPlayer(competitiveTypeId), HttpStatus.OK);
	}

	@PutMapping("/headclub/updateresultmatch/{matchId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateResultMatch(@PathVariable(name = "matchId") int matchId,
			@RequestParam(defaultValue = "0") int firstPoint, @RequestParam(defaultValue = "0") int secondPoint) {
		return new ResponseEntity<ResponseMessage>(
				competitiveService.updateResultMatch(matchId, firstPoint, secondPoint), HttpStatus.OK);
	}

	@GetMapping("/headclub/listmatchs/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> listMatchs(@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.listMatchs(competitiveTypeId), HttpStatus.OK);
	}

	@PutMapping("/headclub/updatelistmatchsplayer")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateListMatchsPlayer(@RequestBody List<CompetitiveMatchDto> listUpdated) {
		return new ResponseEntity<ResponseMessage>(competitiveService.updateListMatchsPlayer(listUpdated),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/listusernotjoincompetitive/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> listUserNotJoinCompetitive(
			@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.getListNotJoinCompetitive(competitiveTypeId),
				HttpStatus.OK);
	}

	@GetMapping("/getResult/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> getResultByType(@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.getResultByType(competitiveTypeId),
				HttpStatus.OK);
	}

	@GetMapping("/treasurer/getallcompetitivetype/{tournamentId}")
	ResponseEntity<ResponseMessage> getAllCompetitiveType(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(competitiveService.getAllCompetitiveType(tournamentId),
				HttpStatus.OK);
	}

}
