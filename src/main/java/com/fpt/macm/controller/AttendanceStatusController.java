package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.Message;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.AttendanceStatusService;

@RestController
@RequestMapping("/api/admin/headtechnique")
public class AttendanceStatusController {
	@Autowired
	AttendanceStatusService attendanceStatusService;
	
	@Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
	
	@PutMapping("/takeattendance/{studentId}/{trainingScheduleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> takeAttendanceByStudentId(@PathVariable(name = "studentId") String studentId,
			@PathVariable(name = "trainingScheduleId") int trainingScheduleId, @RequestParam int status) {
		ResponseMessage response = attendanceStatusService.takeAttendanceByStudentId(studentId, status, trainingScheduleId);
		return new ResponseEntity<ResponseMessage>(
				response,
				HttpStatus.OK);
	}

	@GetMapping("/checkattendance/{trainingScheduleId}")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> checkAttendanceByStudentId(
			@PathVariable(name = "trainingScheduleId") int trainingScheduleId) {
		ResponseMessage response = attendanceStatusService.checkAttendanceStatusByTrainingSchedule(trainingScheduleId);
		return new ResponseEntity<ResponseMessage>(
				response, HttpStatus.OK);
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
	
	@GetMapping("/checkattendancestatusbystudentid/{studentId}")
	ResponseEntity<ResponseMessage> checkAttendanceStatusByStudentId(@PathVariable(name = "studentId") String studentId){
		return new ResponseEntity<ResponseMessage>(attendanceStatusService.checkAttendanceStatusByStudentId(studentId), HttpStatus.OK);
	}
	
	@Scheduled(fixedDelay = 10000L)
	 @SendTo("/chatroom/public")
    public void sendPong() {
		simpMessagingTemplate.convertAndSend("/chatroom/public","Pong");
    }
	
	@MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }
}
