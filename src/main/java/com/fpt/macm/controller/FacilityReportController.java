package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.FacilityReportService;

@RestController
@RequestMapping("/api/facility")
public class FacilityReportController {

	@Autowired
	FacilityReportService facilityReportService;

	@GetMapping("/headtechnique/getfacilityreport")
	ResponseEntity<ResponseMessage> getFacilityReport() {
		return new ResponseEntity<ResponseMessage>(facilityReportService.getAllReport(), HttpStatus.OK);
	}

}
