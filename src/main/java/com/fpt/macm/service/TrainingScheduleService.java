package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.response.ResponseMessage;

public interface TrainingScheduleService {
	ResponseMessage createPreviewTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime);
	ResponseMessage createTrainingSchedule(List<ScheduleDto> listPreview);
	ResponseMessage createTrainingSession(TrainingSchedule trainingSchedule);
	ResponseMessage getListTrainingSchedule();
	ResponseMessage updateTrainingSessionTime(String date, CommonSchedule updateCommonSession);
	ResponseMessage deleteTrainingSession(String date);
	ResponseMessage getTrainingSessionByDate(String date);
	ResponseMessage getTraningScheduleBySemester(int semesterId);
	TrainingSchedule getTrainingScheduleByDate(LocalDate date);
}
