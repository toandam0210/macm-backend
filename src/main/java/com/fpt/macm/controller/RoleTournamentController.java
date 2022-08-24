package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.RoleTournamentService;

@RestController
@RequestMapping("/api/tournament")
public class RoleTournamentController {

	@Autowired
	RoleTournamentService roleTournamentService;
	
	@GetMapping("/getallroletournament")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllRoleTournament() {
		return new ResponseEntity<ResponseMessage>(roleTournamentService.getAllRoleTournament(), HttpStatus.OK);
	}

	@PostMapping("/addnewroletournament")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> addNewRoleTournament(@RequestBody String newName) {
		return new ResponseEntity<ResponseMessage>(roleTournamentService.addNewRoleTournament(newName), HttpStatus.OK);
	}

	@PutMapping("/updateroletournamentname/{roleTournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateRoleEventName(@PathVariable(name = "roleTournamentId") int roleTournamentId,
			@RequestBody String newName) {
		return new ResponseEntity<ResponseMessage>(roleTournamentService.updateRoleTournamentName(roleTournamentId, newName),
				HttpStatus.OK);
	}

	@DeleteMapping("/deleteroletournament/{roleTournamentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> deleteRoleTournament(@PathVariable(name = "roleTournamentId") int roleTournamentId) {
		return new ResponseEntity<ResponseMessage>(roleTournamentService.deleteRoleTournament(roleTournamentId), HttpStatus.OK);
	}
	
}
