package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.FacilityRequest;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.FacilityRequestService;

@RestController
@RequestMapping("/api/facility")
public class FacilityRequestController {

	@Autowired
	FacilityRequestService facilityRequestService;

	@PostMapping("/headtechnique/createrequesttobuyfacility")
	ResponseEntity<ResponseMessage> createRequestToBuyFacilily(@RequestBody FacilityRequest facilityRequest) {
		return new ResponseEntity<ResponseMessage>(facilityRequestService.createRequestToBuyFacility(facilityRequest),
				HttpStatus.OK);
	}

}
