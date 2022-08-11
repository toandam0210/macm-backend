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

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.ExhibitionResultService;
import com.fpt.macm.service.ExhibitionTeamService;
import com.fpt.macm.service.ExhibitionTypeService;

@RestController
@RequestMapping("/api/exhibition")
public class ExhibitionController {
	
	@Autowired
	ExhibitionTeamService exhibitionTeamService;
	
	@Autowired
	ExhibitionResultService exhibitionResultService;
	
	@Autowired
	ExhibitionTypeService exhibitionTypeService;
	
	@PostMapping("/headclub/registerexhibitionteam/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> registerTeam (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId,
			@RequestParam String name, @RequestBody List<String> listStudentId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.registerTeam(exhibitionTypeId, name, listStudentId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getteambytype/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> getTeamByType (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.getTeamByType(exhibitionTypeId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/gettop3teambytype/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> getTop3TeamByType (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.getTop3TeamByType(exhibitionTypeId), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/spawntimeandarea/{tournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> spawnTimeAndArea (@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(exhibitionResultService.spawnTimeAndArea(tournamentId), HttpStatus.OK);
	}
	
	@GetMapping("/getlistexhibitiontype/{tournamentId}")
	ResponseEntity<ResponseMessage> getListExhibitionType (@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTypeService.getAllExhibitionType(tournamentId), HttpStatus.OK);
	}
	
	@GetMapping("/getlistexhibitionresult")
	ResponseEntity<ResponseMessage> getExhibitionResult (@RequestParam(defaultValue = "0") int exhibitionTypeId, @RequestParam(defaultValue = "") String date) {
		return new ResponseEntity<ResponseMessage>(exhibitionResultService.getListExhibitionResult(exhibitionTypeId, date), HttpStatus.OK);
	}
	
	@PutMapping("/headclub/updateexhibitionresult/{exhibitionTeamId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateExhibitionResult(@PathVariable(name = "exhibitionTeamId") int exhibitionTeamId, @RequestParam Double score) {
		return new ResponseEntity<ResponseMessage>(exhibitionResultService.updateExhibitionResult(exhibitionTeamId, score), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/listusernotjoinexhibition/{exhibitionTypeId}")
	ResponseEntity<ResponseMessage> listUserNotJoinExhibition(
			@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(
				exhibitionTypeService.getListNotJoinExhibition(exhibitionTypeId), HttpStatus.OK);
	}
}
