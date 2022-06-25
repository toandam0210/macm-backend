package com.fpt.macm.service;

import java.time.LocalDate;

import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.ResponseMessage;

public interface CommonScheduleService {
	CommonSchedule getCommonSessionByDate(LocalDate date);
	ResponseMessage getCommonSchedule();
	ResponseMessage getCommonSessionByDate(String date);
}
