package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.SemesterService;

@RestController
@RequestMapping("/api/semester")	
public class SemesterController {
	@Autowired
	SemesterService semesterService;

	@GetMapping("/currentsemester")
	ResponseEntity<ResponseMessage> getCurrentSemester() {
		return new ResponseEntity<ResponseMessage>(semesterService.getCurrentSemester(), HttpStatus.OK);
	}
	
	@GetMapping("/gettop3semesters")
	ResponseEntity<ResponseMessage> getTop3Semesters() {
		return new ResponseEntity<ResponseMessage>(semesterService.getTop3Semesters(), HttpStatus.OK);
	}
	
	@GetMapping("/getlistmonths")
	ResponseEntity<ResponseMessage> getListMonthsBySemesters(@RequestParam(defaultValue = "") String semester) {
		return new ResponseEntity<ResponseMessage>(semesterService.getListMonthsBySemester(semester), HttpStatus.OK);
	}
}
