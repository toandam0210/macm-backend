package com.fpt.macm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.TrainingScheduleService;

@RestController
@RequestMapping("/api/trainingschedule")
public class TrainingScheduleController {
	@Autowired
	TrainingScheduleService trainingScheduleService;
	
	@GetMapping("/gettrainingschedule")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadCulture','ROLE_ViceHeadCulture','ROLE_HeadCommunication','ROLE_ViceHeadCommunication','ROLE_HeadTechnique','ROLE_ViceHeadTechnique','ROLE_Treasurer')")
	ResponseEntity<ResponseMessage> getTrainingSchedule(){
			return new ResponseEntity<ResponseMessage>(trainingScheduleService.getListTrainingSchedule(), HttpStatus.OK);
	}
	
	@PostMapping("/headtechnique/addnewsession")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> createTrainingSession(@RequestBody TrainingSchedule trainingSchedule) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.createTrainingSession(trainingSchedule), HttpStatus.OK);
	}
	
	@PostMapping("/headtechnique/createpreview")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> createPreview(@RequestParam String startDate, @RequestParam String finishDate, 
			@RequestBody List<String> dayOfWeek, @RequestParam String startTime, @RequestParam String finishTime) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.createPreviewTrainingSchedule(startDate, finishDate, dayOfWeek, startTime, finishTime), HttpStatus.OK);
	}
	
	@PostMapping("/headtechnique/addnewschedule")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> createTrainingSchedule(@RequestBody List<ScheduleDto> listPreview) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.createTrainingSchedule(listPreview), HttpStatus.OK);
	}
	
	@PutMapping("/headtechnique/updatesession")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> updateTrainingSessionTime(@RequestParam String date, @RequestBody CommonSchedule commonSchedule) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.updateTrainingSessionTime(date, commonSchedule), HttpStatus.OK);
	}
	
	@PutMapping("/headtechnique/deletesession")
	@PreAuthorize("hasAnyRole('ROLE_HeadClub','ROLE_ViceHeadClub','ROLE_HeadTechnique','ROLE_ViceHeadTechnique')") 
	ResponseEntity<ResponseMessage> deleteTrainingSession(@RequestParam String date) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.deleteTrainingSession(date), HttpStatus.OK);
	}
	
	@GetMapping("/gettrainingschedulebysemester/{semesterId}")
	ResponseEntity<ResponseMessage> getTrainingScheduleBySemester(@PathVariable(name = "semesterId") int semesterId){
			return new ResponseEntity<ResponseMessage>(trainingScheduleService.getTraningScheduleBySemester(semesterId), HttpStatus.OK);
	}
	
	@GetMapping("/gettrainingsesionbydate")
	ResponseEntity<ResponseMessage> getTrainingSessionByDate(@RequestParam String date) {
			return new ResponseEntity<ResponseMessage>(trainingScheduleService.getTrainingSessionByDate(date), HttpStatus.OK);
	}
}
