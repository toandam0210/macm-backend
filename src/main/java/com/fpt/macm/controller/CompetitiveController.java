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

import com.fpt.macm.dto.CompetitiveMatchDto;
import com.fpt.macm.model.ResponseMessage;
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
	ResponseEntity<ResponseMessage> addNewCompetitivePlayer (@PathVariable(name = "tournamentId") int tournamentId,
			@RequestParam int userId, @RequestParam (defaultValue = "0") double weight) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.addNewCompetitivePlayer(userId, tournamentId, weight), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updateweightplayer/{competitivePlayerId}")
	ResponseEntity<ResponseMessage> updateWeightForCompetitivePlayer (@PathVariable(name = "competitivePlayerId") int competitivePlayerId,
			@RequestParam double weight) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.updateWeightForCompetitivePlayer(competitivePlayerId, weight), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/deletecompetitiveplayer/{competitivePlayerId}")
	ResponseEntity<ResponseMessage> deleteCompetitivePlayer (@PathVariable(name = "competitivePlayerId") int competitivePlayerId) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerService.deleteCompetitivePlayer(competitivePlayerId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getlistplayerbracket/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> getListPlayerBracket (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitivePlayerBracketService.getListPlayerBracket(competitiveTypeId), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/spawnmatchs/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> spawnMatchs (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.spawnMatchs(competitiveTypeId), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/updatetimeandplacematch/{matchId}")
	ResponseEntity<ResponseMessage> updateTimeAndPlaceMatch (@PathVariable(name = "matchId") int matchId,
			@RequestParam int areaId, @RequestParam String time) {
		return new ResponseEntity<ResponseMessage>(competitiveResultService.updateTimeAndPlaceMatch(matchId, areaId, time), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updateresultmatch/{matchId}")
	ResponseEntity<ResponseMessage> updateResultMatch (@PathVariable(name = "matchId") int matchId, @RequestParam (defaultValue = "0") int firstPoint, @RequestParam (defaultValue = "0") int secondPoint) {
		return new ResponseEntity<ResponseMessage>(competitiveResultService.updateResultMatch(matchId, firstPoint, secondPoint), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/listmatchs/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> listMatchs (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.listMatchs(competitiveTypeId), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updatelistmatchsplayer")
	ResponseEntity<ResponseMessage> updateListMatchsPlayer (@RequestBody List<CompetitiveMatchDto> listUpdated) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.updateListMatchsPlayer(listUpdated), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/previewmatchsplayer/{competitiveTypeId}")
	ResponseEntity<ResponseMessage> previewMatchsPlayer (@PathVariable(name = "competitiveTypeId") int competitiveTypeId) {
		return new ResponseEntity<ResponseMessage>(competitiveMatchService.previewMatchsPlayer(competitiveTypeId), HttpStatus.OK);
	}
}
