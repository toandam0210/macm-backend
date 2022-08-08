package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface AttendanceEventService {

	ResponseMessage takeAttendanceByStudentId(String studentId, int status, int eventId);
	ResponseMessage checkAttendanceStatusByEventId(int eventId);
	ResponseMessage getListOldEventToTakeAttendanceBySemester(String semesterName);
}
