package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.DashboardService;

@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

	@Autowired
	DashboardService dashboardService;

	@GetMapping("/attendance")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> attendanceReportBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(dashboardService.attendanceReport(semester), HttpStatus.OK);
	}
	
	@GetMapping("/collaborator")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getCollaboratorReport() {
		return new ResponseEntity<ResponseMessage>(dashboardService.getCollaboratorReport(), HttpStatus.OK);
	}
	
	@GetMapping("/event")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getEventReport() {
		return new ResponseEntity<ResponseMessage>(dashboardService.eventReport(), HttpStatus.OK);
	}
	
	@GetMapping("/member/status")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getUserStatusReport() {
		return new ResponseEntity<ResponseMessage>(dashboardService.statusMemberReport(), HttpStatus.OK);
	}
	
	@GetMapping("/fee")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> feeReportBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(dashboardService.feeReport(semester), HttpStatus.OK);
	}
	
	@GetMapping("/getallupcomingactivities/{filterIndex}")
	ResponseEntity<ResponseMessage> getAllUpcomingActivities(@PathVariable(name = "filterIndex") int filterIndex) {
		return new ResponseEntity<ResponseMessage>(dashboardService.getAllUpcomingActivities(filterIndex), HttpStatus.OK);
	}
	
	@GetMapping("/activityreport")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getActivityReport(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(dashboardService.activityReport(semester), HttpStatus.OK);
	}
}
