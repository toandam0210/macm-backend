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

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.MembershipService;

@RestController
@RequestMapping("/api/admin")
public class MembershipController {
	
	@Autowired
	MembershipService membershipService;
	
	@GetMapping("/treasure/membership/{membershipInfoId}")
	ResponseEntity<ResponseMessage> getListMembershipStatus(@PathVariable(name = "membershipInfoId") int membershipInfoId) {
		return new ResponseEntity<ResponseMessage>(membershipService.getListMemberPayMembershipBySemester(membershipInfoId), HttpStatus.OK);
	}
	
	@PutMapping("/treasure/membership/update/{id}")
	ResponseEntity<ResponseMessage> updateMembershipStatus(@PathVariable(name = "id") int id) {
		return new ResponseEntity<ResponseMessage>(membershipService.updateStatusPaymenMembershipById(id), HttpStatus.OK);
	}
	
	@PutMapping("/treasure/membership/membershipinfo/{semester}")
	ResponseEntity<ResponseMessage> updateMembershipInfo(@RequestParam double amount ,@PathVariable(name = "semester") String semester) {
		return new ResponseEntity<ResponseMessage>(membershipService.updateMembershipBySemester(amount, semester), HttpStatus.OK);
	}
	@GetMapping("/treasure/membership/membershipinfo/{semester}")
	ResponseEntity<ResponseMessage> updateMembershipInfo(@PathVariable(name = "semester") String semester) {
		return new ResponseEntity<ResponseMessage>(membershipService.getMembershipInfoBySemester(semester), HttpStatus.OK);
	}
}
