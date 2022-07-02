package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface SemesterService {
	ResponseMessage getCurrentSemester();
	ResponseMessage getTop3Semesters();
	ResponseMessage getListMonthsBySemester(String semester);
}
