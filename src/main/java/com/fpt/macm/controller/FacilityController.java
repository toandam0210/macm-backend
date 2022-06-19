package com.fpt.macm.controller;

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

import com.fpt.macm.model.Facility;
import com.fpt.macm.model.FacilityRequest;
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
	
	@PutMapping("/headtechnique/updatefacilitybyid/{facilityId}")
	ResponseEntity<ResponseMessage> updateFacilityById(@PathVariable(name = "facilityId") int facilityId, @RequestBody Facility facility){
		return new ResponseEntity<ResponseMessage>(facilityService.updateFacilityById(facilityId, facility), HttpStatus.OK);
	}
	
	@GetMapping("/headtechnique/getallfacility")
	ResponseEntity<ResponseMessage> getAllFacility(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
		return new ResponseEntity<ResponseMessage>(facilityService.getAllFacility(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/headtechnique/getfacilityreport")
	ResponseEntity<ResponseMessage> getFacilityReport() {
		return new ResponseEntity<ResponseMessage>(facilityService.getAllReport(), HttpStatus.OK);
	}

	@PostMapping("/headtechnique/createrequesttobuyfacility")
	ResponseEntity<ResponseMessage> createRequestToBuyFacilily(@RequestBody FacilityRequest facilityRequest) {
		return new ResponseEntity<ResponseMessage>(facilityService.createRequestToBuyFacility(facilityRequest),
				HttpStatus.OK);
	}
	
	@GetMapping("/treasurer/getallrequesttobuyfacility")
	ResponseEntity<ResponseMessage> getAllRequestToBuyFacility(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
		return new ResponseEntity<ResponseMessage>(facilityService.getAllRequestToBuyFacility(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
}
