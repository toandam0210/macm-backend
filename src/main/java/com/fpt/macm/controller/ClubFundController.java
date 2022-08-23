package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.ClubFundService;

@RestController
@RequestMapping("/api/admin/treasurer")
public class ClubFundController {

	@Autowired
	ClubFundService clubFundService;
	
	@GetMapping("/getclubfund")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')") 
	ResponseEntity<ResponseMessage> getClubFund(){
		return new ResponseEntity<ResponseMessage>(clubFundService.getClubFund(), HttpStatus.OK);
	}
	
	@PutMapping("/deposittoclubfund/{amount}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')") 
	ResponseEntity<ResponseMessage> depositToClubFund(@PathVariable(name = "amount") int amount, @PathVariable(name = "studentId") String studentId, @RequestParam(defaultValue = "") String note){
		return new ResponseEntity<ResponseMessage>(clubFundService.depositToClubFund(studentId, amount, note), HttpStatus.OK);
	}
	
	@PutMapping("/withdrawfromclubfund/{amount}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')") 
	ResponseEntity<ResponseMessage> withdrawFromClubFund(@PathVariable(name = "amount") int amount, @PathVariable(name = "studentId") String studentId, @RequestParam(defaultValue = "") String note){
		return new ResponseEntity<ResponseMessage>(clubFundService.withdrawFromClubFund(studentId, amount, note), HttpStatus.OK);
	}
	
	@GetMapping("/getclubfundreport")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')") 
	ResponseEntity<ResponseMessage> getClubFundReport(){
		return new ResponseEntity<ResponseMessage>(clubFundService.getClubFundReport(), HttpStatus.OK);
	}
}
