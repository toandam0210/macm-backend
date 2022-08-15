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

import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.ExhibitionService;

@RestController
@RequestMapping("/api/exhibition")
public class ExhibitionController {

	@Autowired
	ExhibitionService exhibitionService;

	@PostMapping("/headclub/registerexhibitionteam/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> registerTeam(@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId,
			@RequestParam String name, @RequestBody List<String> listStudentId) {
		return new ResponseEntity<ResponseMessage>(
				exhibitionService.registerTeam(exhibitionTypeId, name, listStudentId), HttpStatus.OK);
	}

	@GetMapping("/headclub/getteambytype/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getTeamByType(@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionService.getTeamByType(exhibitionTypeId),
				HttpStatus.OK);
	}

	@GetMapping("/headclub/gettop3teambytype/{exhibitionTypeId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getTop3TeamByType(@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionService.getTop3TeamByType(exhibitionTypeId),
				HttpStatus.OK);
	}

	@GetMapping("/getlistexhibitiontype/{tournamentId}")
	ResponseEntity<ResponseMessage> getListExhibitionType(@PathVariable(name = "tournamentId") int tournamentId) {
		return new ResponseEntity<ResponseMessage>(exhibitionService.getAllExhibitionType(tournamentId),
				HttpStatus.OK);
	}

	@GetMapping("/getlistexhibitionresult")
	ResponseEntity<ResponseMessage> getExhibitionResult(@RequestParam(defaultValue = "0") int exhibitionTypeId,
			@RequestParam(defaultValue = "") String date) {
		return new ResponseEntity<ResponseMessage>(
				exhibitionService.getListExhibitionResult(exhibitionTypeId, date), HttpStatus.OK);
	}

	@PutMapping("/headclub/updateexhibitionresult/{exhibitionTeamId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateExhibitionResult(
			@PathVariable(name = "exhibitionTeamId") int exhibitionTeamId, @RequestParam Double score) {
		return new ResponseEntity<ResponseMessage>(
				exhibitionService.updateExhibitionResult(exhibitionTeamId, score), HttpStatus.OK);
	}

	@GetMapping("/headclub/listusernotjoinexhibition/{exhibitionTypeId}")
	ResponseEntity<ResponseMessage> listUserNotJoinExhibition(
			@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionService.getListNotJoinExhibition(exhibitionTypeId),
				HttpStatus.OK);
	}

	@PutMapping("/headclub/updateteam/{exhibitionTeamId}")
	ResponseEntity<ResponseMessage> updateExhibitionTeam(@PathVariable(name = "exhibitionTeamId") int exhibitionTeamId,
			@RequestBody List<User> teamUsers) {
		return new ResponseEntity<ResponseMessage>(exhibitionService.updateTeam(exhibitionTeamId, teamUsers), HttpStatus.OK);
	}
}
