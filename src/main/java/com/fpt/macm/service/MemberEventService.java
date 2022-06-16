package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface MemberEventService {

	ResponseMessage getAllMemberOfEvent(int eventId, int pageNo, int pageSize, String sortBy);
	
}
