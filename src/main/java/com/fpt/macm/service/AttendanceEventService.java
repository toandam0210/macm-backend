package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface AttendanceEventService {

	ResponseMessage takeAttendanceByStudentId(String studentId, int status, int eventId, String adminStudentId);
	ResponseMessage checkAttendanceStatusByEventId(int eventId, int status);
	ResponseMessage getListOldEventToTakeAttendanceBySemester(String semesterName);
}
