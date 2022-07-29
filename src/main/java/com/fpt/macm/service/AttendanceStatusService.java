package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface AttendanceStatusService {
	ResponseMessage takeAttendanceByStudentId(String studentId, int status);
	ResponseMessage checkAttendanceStatusByTrainingSchedule(int trainingScheduleId);
	ResponseMessage attendanceTrainingReport(String semester);
	ResponseMessage getAllAttendanceStatusByStudentIdAndSemester(String studentId, String semesterName);
}
