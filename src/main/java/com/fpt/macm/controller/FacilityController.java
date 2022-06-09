package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.Facility;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.FacilityService;

@RestController
@RequestMapping("/api/facility")
public class FacilityController {

	@Autowired
	FacilityService facilityService;
	
	@PostMapping("/headtechnique/createnewfacility")
	ResponseEntity<ResponseMessage> createNewFacility(@RequestBody Facility facility){
		return new ResponseEntity<ResponseMessage>(facilityService.createNewFacility(facility), HttpStatus.OK);
	}
}
