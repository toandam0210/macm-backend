package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.RoleEvent;

public interface MemberEventService {

	ResponseMessage getAllUserOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllOrganizingCommitteeOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllMemberOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUserRoleInEvent(int memberEventId, RoleEvent roleEvent);
	ResponseMessage getAllUserCancelJoinEvent(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage updateMemberEventPaymentStatus(int memberEventId);
	
}
