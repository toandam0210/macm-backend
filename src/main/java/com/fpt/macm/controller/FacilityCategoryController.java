package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.FacilityCategory;
import com.fpt.macm.model.response.ResponseMessage;
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
	@GetMapping("/headtechnique/getallcategories")
	ResponseEntity<ResponseMessage> getAllFacilityCategory(){
		return new ResponseEntity<ResponseMessage>(facilityCategoryService.getAllCategories(), HttpStatus.OK);
	}
	@PutMapping("/headtechnique/updatecategory/{id}")
	ResponseEntity<ResponseMessage> updateFacilityCategoryById(@RequestBody FacilityCategory facilityCategory,@PathVariable(name = "id") int id){
		return new ResponseEntity<ResponseMessage>(facilityCategoryService.updateCategory(facilityCategory,id), HttpStatus.OK);
	}
	@PutMapping("/headtechnique/delete/{id}")
	ResponseEntity<ResponseMessage> deleteFacilityCategoryById(@PathVariable(name = "id") int id){
		return new ResponseEntity<ResponseMessage>(facilityCategoryService.deleteCategory(id), HttpStatus.OK);
	}
}
