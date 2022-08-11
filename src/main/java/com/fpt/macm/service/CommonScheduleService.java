package com.fpt.macm.service;

import java.time.LocalDate;

import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.response.ResponseMessage;

public interface CommonScheduleService {
	CommonSchedule getCommonSessionByDate(LocalDate date);
	ResponseMessage getCommonSchedule();
	ResponseMessage getCommonScheduleByDate(String date);
	ResponseMessage getCommonScheduleBySemester(int semesterId);
}
