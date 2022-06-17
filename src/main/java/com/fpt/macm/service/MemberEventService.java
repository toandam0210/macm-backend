package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface MemberEventService {

	ResponseMessage getAllUserOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllOrganizingCommitteeOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllMemberOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	//ResponseMessage updateUserRoleInEvent(List<MemberEvent> listMemberEvent);
	
}
