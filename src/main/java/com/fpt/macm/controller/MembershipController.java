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
import com.fpt.macm.service.MembershipService;

@RestController
@RequestMapping("/api/admin")
public class MembershipController {

	@Autowired
	MembershipService membershipService;

	@GetMapping("/treasure/membership/{membershipInfoId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getListMembershipStatus(
			@PathVariable(name = "membershipInfoId") int membershipInfoId) {
		return new ResponseEntity<ResponseMessage>(
				membershipService.getListMemberPayMembershipBySemester(membershipInfoId), HttpStatus.OK);
	}

	@PutMapping("/treasure/membership/update/{id}/{studentId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateMembershipStatus(@PathVariable(name = "id") int id,
			@PathVariable(name = "studentId") String studentId) {
		return new ResponseEntity<ResponseMessage>(membershipService.updateStatusPaymenMembershipById(studentId, id),
				HttpStatus.OK);
	}

	@PutMapping("/treasure/membership/membershipinfo/{semester}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateMembershipInfo(@RequestParam double amount,
			@PathVariable(name = "semester") String semester) {
		return new ResponseEntity<ResponseMessage>(membershipService.updateMembershipBySemester(amount, semester),
				HttpStatus.OK);
	}

	@GetMapping("/treasure/membership/membershipinfo/{semester}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> updateMembershipInfo(@PathVariable(name = "semester") String semester) {
		return new ResponseEntity<ResponseMessage>(membershipService.getMembershipInfoBySemester(semester),
				HttpStatus.OK);
	}

	@GetMapping("/treasure/membership/getreportmembershippaymentstatus/{membershipInfoId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getReportMembershipPaymentStatus(
			@PathVariable(name = "membershipInfoId") int membershipInfoId, @RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return new ResponseEntity<ResponseMessage>(
				membershipService.getReportMembershipPaymentStatus(membershipInfoId, pageNo, pageSize, sortBy),
				HttpStatus.OK);
	}
}
