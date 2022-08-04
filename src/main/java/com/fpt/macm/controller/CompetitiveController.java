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
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CompetitiveMatchService;
import com.fpt.macm.service.CompetitivePlayerBracketService;
import com.fpt.macm.service.CompetitivePlayerService;
import com.fpt.macm.service.CompetitiveResultService;



@RestController
@RequestMapping("/api/competitive")
public class CompetitiveController {
	
	@Autowired
	CompetitivePlayerService competitivePlayerService;
	
	@Autowired
	CompetitiveMatchService competitiveMatchService;
	
	@Autowired
	CompetitivePlayerBracketService competitivePlayerBracketService;
	
	@Autowired
	CompetitiveResultService competitiveResultService;
	
	@PostMapping("/headclub/addnewcompetitiveplayer/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> addNewCompetitivePlayer (@PathVariable(name = "tournamentId") int tournamentId,
			@RequestParam int userId, @RequestParam (defaultValue = "0") double weight) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.addNewCompetitivePlayer(userId, tournamentId, weight), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updateweightplayer/{competitivePlayerId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateWeightForCompetitivePlayer (@PathVariable(name = "competitivePlayerId") int competitivePlayerId,
			@RequestParam double weight) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.updateWeightForCompetitivePlayer(competitivePlayerId, weight), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/deletecompetitiveplayer/{competitivePlayerId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> deleteCompetitivePlayer (@PathVariable(name = "competitivePlayerId") int competitivePlayerId) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.deleteCompetitivePlayer(competitivePlayerId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getlistplayerbracket/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> getListPlayerBracket (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerBracketService.getListPlayerBracket(competitiveTypeId), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/spawnmatchs/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> spawnMatchs (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.spawnMatchs(competitiveTypeId), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updatetimeandplacematch/{matchId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateTimeAndPlaceMatch (@PathVariable(name = "matchId") int matchId,
			@RequestBody CompetitiveResult newResult) {
		return new ResponseEntity<ResponseMessage>(competitiveResultService.updateTimeAndArea(matchId, newResult), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updateresultmatch/{matchId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateResultMatch (@PathVariable(name = "matchId") int matchId, @RequestParam (defaultValue = "0") int firstPoint, @RequestParam (defaultValue = "0") int secondPoint) {
		return new ResponseEntity<ResponseMessage>(competitiveResultService.updateResultMatch(matchId, firstPoint, secondPoint), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/listmatchs/{competitiveTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> listMatchs (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.listMatchs(competitiveTypeId), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updatelistmatchsplayer")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateListMatchsPlayer (@RequestBody List<CompetitiveMatchDto> listUpdated) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.updateListMatchsPlayer(listUpdated), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/confirmlistmatchsplayer/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> confirmListMatchsPlayer (@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.confirmListMatchsPlayer(tournamentId), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/spawntimeandarea/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> spawnTimeAndArea (@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(competitiveResultService.spawnTimeAndArea(tournamentId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/listusernotjoincompetitive/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> listUserNotJoinCompetitive (@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.listUserNotJoinCompetitive(tournamentId), HttpStatus.OK);
	}
}
