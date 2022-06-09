package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.FacilityCategory;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.FacilityCategoryService;


@RestController
@RequestMapping("/api/facilitycategory")
public class FacilityCategoryController {
	@Autowired
	FacilityCategoryService facilityCategoryService;
	
	@PostMapping("/headtechnique/addnewcategories")
	ResponseEntity<ResponseMessage> addNewCategories(@RequestBody List<FacilityCategory> facilityCategories){
		return new ResponseEntity<ResponseMessage>(facilityCategoryService.addNewCategory(facilityCategories), HttpStatus.OK);
	}
}
