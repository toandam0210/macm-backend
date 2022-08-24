package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> getAllArea () {
		return new ResponseEntity<ResponseMessage>(areaService.getAllArea(), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/addnewarea")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> addNewArea (@RequestBody Area area) {
		return new ResponseEntity<ResponseMessage>(areaService.addNewArea(area), HttpStatus.OK);
	}
	
	@PostMapping("/headclub/updatelistarea")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')")
	ResponseEntity<ResponseMessage> updateListArea (@RequestBody List<Area> listArea) {
		return new ResponseEntity<ResponseMessage>(areaService.updateListArea(listArea), HttpStatus.OK);
	}
}
