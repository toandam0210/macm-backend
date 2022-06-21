package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface AttendanceEventService {

	ResponseMessage takeAttendanceByMemberEventId(int memberEventId);
	
}
