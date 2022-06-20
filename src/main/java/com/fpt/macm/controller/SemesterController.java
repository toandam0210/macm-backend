package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.SemesterService;

@RestController
@RequestMapping("/api/admin")	
public class SemesterController {
	@Autowired
	SemesterService semesterService;

	@GetMapping("/semester/currentsemester")
	ResponseEntity<ResponseMessage> getCurrentSemester() {
		return new ResponseEntity<ResponseMessage>(semesterService.getCurrentSemester(), HttpStatus.OK);
	}
}
