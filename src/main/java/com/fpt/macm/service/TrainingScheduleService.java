package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.TrainingSchedule;

public interface TrainingScheduleService {
	ResponseMessage createTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime);
	ResponseMessage createTrainingSession(TrainingSchedule trainingSchedule);
	ResponseMessage getListTrainingSchedule();
	ResponseMessage updateTrainingSessionTime(int trainingId, TrainingSchedule updateTraningSession);
	ResponseMessage deleteTrainingSession(int trainingId);
}
