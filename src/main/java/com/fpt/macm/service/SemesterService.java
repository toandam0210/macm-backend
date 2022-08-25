package com.fpt.macm.service;

import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;

public interface SemesterService {
	ResponseMessage getCurrentSemester();
	ResponseMessage getTop4Semesters();
	ResponseMessage getListMonthsBySemester(String semester);
	public ResponseMessage updateSemester(int semesterId, Semester semester);
}
