package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface AttendanceStatusService {
	ResponseMessage takeAttendanceByStudentId(String studentId, int status, int trainingScheduleId, String adminStudentId);
	ResponseMessage checkAttendanceStatusByTrainingSchedule(int trainingScheduleId, int status);
	ResponseMessage attendanceTrainingReport(String semester);
	ResponseMessage getAllAttendanceStatusByStudentIdAndSemester(String studentId, String semesterName, int month);
	ResponseMessage getListOldTrainingScheduleToTakeAttendanceBySemester(String semesterName);
	ResponseMessage getAttendanceTrainingStatistic(String semesterName, int roleId);
	ResponseMessage checkAttendanceStatusByStudentId(String studentId);
}
