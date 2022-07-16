package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.AreaService;

@RestController
@RequestMapping("/api/area")
public class AreaController {
	
	@Autowired
	AreaService areaService;
	
	@GetMapping("/headclub/getallarea")
	ResponseEntity<ResponseMessage> getAllArea () {
		return new ResponseEntity<ResponseMessage>(areaService.getAllArea(), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/addnewarea")
	ResponseEntity<ResponseMessage> addNewArea (@RequestBody Area area) {
		return new ResponseEntity<ResponseMessage>(areaService.addNewArea(area), HttpStatus.OK);
	}
}
