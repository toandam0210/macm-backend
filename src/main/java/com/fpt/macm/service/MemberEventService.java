package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.dto.MemberEventDto;
import com.fpt.macm.model.ResponseMessage;

public interface MemberEventService {

	ResponseMessage getAllUserOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllOrganizingCommitteeOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllMemberOfEventByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUserRoleInEvent(List<MemberEventDto> membersEventDto);
	ResponseMessage getAllUserCancelJoinEvent(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage updateMemberEventPaymentStatus(int memberEventId);
	ResponseMessage getReportPaymentStatusByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	
}
