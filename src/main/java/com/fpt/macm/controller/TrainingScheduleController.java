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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.service.TrainingScheduleService;

@RestController
@RequestMapping("/api/trainingschedule")
public class TrainingScheduleController {
	@Autowired
	TrainingScheduleService trainingScheduleService;
	
	@GetMapping("/gettrainingschedule")
	ResponseEntity<ResponseMessage> getTrainingScheduleByMonthAndYear(){
			return new ResponseEntity<ResponseMessage>(trainingScheduleService.getListTrainingSchedule(), HttpStatus.OK);
	}
	
	@PostMapping("/headtechnique/addnewsession")
	ResponseEntity<ResponseMessage> createTrainingSession(@RequestBody TrainingSchedule trainingSchedule) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.createTrainingSession(trainingSchedule), HttpStatus.OK);
	}
	
	@PostMapping("/headtechnique/addnewschedule")
	ResponseEntity<ResponseMessage> createTrainingSchedule(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "finishDate") String finishDate, 
			@RequestBody List<String> dayOfWeek, @RequestParam(name = "startTime") String startTime, @RequestParam(name = "finishTime") String finishTime) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.createTrainingSchedule(startDate, finishDate, dayOfWeek, startTime, finishTime), HttpStatus.OK);
	}
	
	@PutMapping("/headtechnique/updatesession/{trainingId}")
	ResponseEntity<ResponseMessage> updateTrainingSessionTime(@PathVariable(name = "trainingId") int trainingId, @RequestBody TrainingSchedule trainingSchedule) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.updateTrainingSessionTime(trainingId, trainingSchedule), HttpStatus.OK);
	}
	
	@PutMapping("/headtechnique/deletesession/{trainingId}")
	ResponseEntity<ResponseMessage> deleteTrainingSession(@PathVariable(name = "trainingId") int trainingId) {
		return new ResponseEntity<ResponseMessage>(trainingScheduleService.deleteTrainingSession(trainingId), HttpStatus.OK);
	}
}
