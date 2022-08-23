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
import com.fpt.macm.service.AttendanceStatusService;

@RestController
@RequestMapping("/api/admin/headtechnique")
public class AttendanceStatusController {
	@Autowired
	AttendanceStatusService attendanceStatusService;

	@PutMapping("/takeattendance/{studentId}/{trainingScheduleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> takeAttendanceByStudentId(@PathVariable(name = "studentId") String studentId,
			@PathVariable(name = "trainingScheduleId") int trainingScheduleId, @RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(
				attendanceStatusService.takeAttendanceByStudentId(studentId, status, trainingScheduleId),
				HttpStatus.OK);
	}

	@GetMapping("/checkattendance/{trainingScheduleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> checkAttendanceByStudentId(
			@PathVariable(name = "trainingScheduleId") int trainingScheduleId) {
		return new ResponseEntity<ResponseMessage>(
				attendanceStatusService.checkAttendanceStatusByTrainingSchedule(trainingScheduleId), HttpStatus.OK);
	}

	@GetMapping("/checkattendance/report")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> userAttendanceReportBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(attendanceStatusService.attendanceTrainingReport(semester),
				HttpStatus.OK);
	}

	@GetMapping("/getallattendancestatusbystudentidandsemester/{studentId}")
	ResponseEntity<ResponseMessage> getAllAttendanceStatusByStudentIdAndSemester(
			@PathVariable(name = "studentId") String studentId, @RequestParam String semester, @RequestParam int month) {
		return new ResponseEntity<ResponseMessage>(
				attendanceStatusService.getAllAttendanceStatusByStudentIdAndSemester(studentId, semester, month),
				HttpStatus.OK);
	}

	@GetMapping("/getlistoldtrainingscheduletotakeattendancebysemester/{semesterName}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getListOldTrainingScheduleToTakeAttendanceBySemester(
			@PathVariable(name = "semesterName") String semesterName) {
		return new ResponseEntity<ResponseMessage>(
				attendanceStatusService.getListOldTrainingScheduleToTakeAttendanceBySemester(semesterName),
				HttpStatus.OK);
	}

	@GetMapping("/getattendancetrainingstatistic/{semesterName}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getAttendanceTrainingStatistic(
			@PathVariable(name = "semesterName") String semesterName, @RequestParam(defaultValue = "0") int roleId) {
		return new ResponseEntity<ResponseMessage>(
				attendanceStatusService.getAttendanceTrainingStatistic(semesterName, roleId), HttpStatus.OK);
	}
}
