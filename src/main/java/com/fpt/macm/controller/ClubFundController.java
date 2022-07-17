package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	ResponseEntity<ResponseMessage> getClubFund(){
		return new ResponseEntity<ResponseMessage>(clubFundService.getClubFund(), HttpStatus.OK);
	}
	
	@PutMapping("/deposittoclubfund/{amount}")
	ResponseEntity<ResponseMessage> depositToClubFund(@PathVariable(name = "amount") int amount, @RequestParam(defaultValue = "") String note){
		return new ResponseEntity<ResponseMessage>(clubFundService.depositToClubFund(amount, note), HttpStatus.OK);
	}
	
	@PutMapping("/withdrawfromclubfund/{amount}")
	ResponseEntity<ResponseMessage> withdrawFromClubFund(@PathVariable(name = "amount") int amount, @RequestParam(defaultValue = "") String note){
		return new ResponseEntity<ResponseMessage>(clubFundService.withdrawFromClubFund(amount, note), HttpStatus.OK);
	}
	
	@GetMapping("/getclubfundreport")
	ResponseEntity<ResponseMessage> getClubFundReport(){
		return new ResponseEntity<ResponseMessage>(clubFundService.getClubFundReport(), HttpStatus.OK);
	}
}
