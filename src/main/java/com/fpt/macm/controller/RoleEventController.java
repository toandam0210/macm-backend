package com.fpt.macm.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.RoleEventService;

@RestController
@RequestMapping("/api/roleevent")
public class RoleEventController {

	@Autowired
	RoleEventService roleEventService;

	@GetMapping("/getallroleevent")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAllRoleEvent() {
		return new ResponseEntity<ResponseMessage>(roleEventService.getAllRoleEvent(), HttpStatus.OK);
	}

	@PostMapping("/addnewroleevent")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> addNewRoleEvent(@RequestBody String newName) {
		return new ResponseEntity<ResponseMessage>(roleEventService.addNewRoleEvent(newName), HttpStatus.OK);
	}

	@PutMapping("/updateroleeventname/{roleEventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateRoleEventName(@PathVariable(name = "roleEventId") int roleEventId,
			@RequestBody String newName) {
		return new ResponseEntity<ResponseMessage>(roleEventService.updateRoleEventName(roleEventId, newName),
				HttpStatus.OK);
	}

	@PutMapping("/updatestatusroleevent/{roleEventId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateStatusRoleEvent(@PathVariable(name = "roleEventId") int roleEventId) {
		return new ResponseEntity<ResponseMessage>(roleEventService.updateStatusRoleEvent(roleEventId), HttpStatus.OK);
	}
}