package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.TrainingSchedule;

public interface TrainingScheduleService {
	ResponseMessage createPreviewTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime);
	ResponseMessage createTrainingSchedule(List<ScheduleDto> listPreview);
	ResponseMessage createTrainingSession(TrainingSchedule trainingSchedule);
	ResponseMessage getListTrainingSchedule();
	ResponseMessage updateTrainingSessionTime(String date, CommonSchedule updateCommonSession);
	ResponseMessage deleteTrainingSession(String date);
	ResponseMessage getTrainingSessionByDate(String date);
	ResponseMessage getTraningScheduleBySemester(int semesterId);
	TrainingSchedule getTrainingSessionByDate(LocalDate date);
}
