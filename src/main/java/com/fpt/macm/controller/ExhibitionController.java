package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.ExhibitionTeamService;

@RestController
@RequestMapping("/api/exhibition")
public class ExhibitionController {
	
	@Autowired
	ExhibitionTeamService exhibitionTeamService;
	
	@PostMapping("/headclub/registerexhibitionteam/{exhibitionTypeId}")
	ResponseEntity<ResponseMessage> registerTeam (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId,
			@RequestParam String name, @RequestBody List<String> listStudentId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.registerTeam(exhibitionTypeId, name, listStudentId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/getteambytype/{exhibitionTypeId}")
	ResponseEntity<ResponseMessage> getTeamByType (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.getTeamByType(exhibitionTypeId), HttpStatus.OK);
	}
	
	@GetMapping("/headclub/gettop3teambytype/{exhibitionTypeId}")
	ResponseEntity<ResponseMessage> getTop3TeamByType (@PathVariable(name = "exhibitionTypeId") int exhibitionTypeId) {
		return new ResponseEntity<ResponseMessage>(exhibitionTeamService.getTop3TeamByType(exhibitionTypeId), HttpStatus.OK);
	}
}
