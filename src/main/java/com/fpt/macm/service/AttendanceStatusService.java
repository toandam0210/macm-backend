package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface AttendanceStatusService {
	ResponseMessage takeAttendanceByStudentId(String studentId);
	ResponseMessage checkAttendanceStatusByTrainingSchedule(int trainingScheduleId);
}
