package com.fpt.macm.service;

import java.time.LocalDate;
import com.fpt.macm.model.CommonSchedule;

public interface CommonScheduleService {
	CommonSchedule getCommonSessionByDate(LocalDate date);
}
