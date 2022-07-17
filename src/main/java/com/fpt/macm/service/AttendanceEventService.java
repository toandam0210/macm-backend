package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface AttendanceEventService {

	ResponseMessage takeAttendanceByMemberEventId(int memberEventId, int status);
	ResponseMessage checkAttendanceStatusByEventId(int eventId);
}
