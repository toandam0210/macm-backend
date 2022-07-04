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
import com.fpt.macm.service.AttendanceStatusService;

@RestController
@RequestMapping("/api/admin/headtechnique")
public class AttendanceStatusController {
	@Autowired
	AttendanceStatusService attendanceStatusService;
	
	@PutMapping("/takeattendance/{studentId}")
	ResponseEntity<ResponseMessage> takeAttendanceByStudentId(@PathVariable(name = "studentId") String studentId, @RequestParam int status) {
		return new ResponseEntity<ResponseMessage>(attendanceStatusService.takeAttendanceByStudentId(studentId, status), HttpStatus.OK);
	}
	
	@GetMapping("/checkattendance/{trainingScheduleId}")
	ResponseEntity<ResponseMessage> checkAttendanceByStudentId(@PathVariable(name = "trainingScheduleId") int trainingScheduleId) {
		return new ResponseEntity<ResponseMessage>(attendanceStatusService.checkAttendanceStatusByTrainingSchedule(trainingScheduleId), HttpStatus.OK);
	}
	@GetMapping("/checkattendance/report")
	ResponseEntity<ResponseMessage> attendanceReportBySemester(@RequestParam String semester) {
		return new ResponseEntity<ResponseMessage>(attendanceStatusService.attendanceTrainingReport(semester), HttpStatus.OK);
	}
}
