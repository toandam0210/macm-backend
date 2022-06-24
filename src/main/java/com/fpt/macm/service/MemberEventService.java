package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.dto.MemberEventDto;
import com.fpt.macm.model.ResponseMessage;

public interface MemberEventService {

	ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto);
	ResponseMessage getAllMemberCancelJoinEvent(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage updateMemberEventPaymentStatus(int memberEventId);
	ResponseMessage getReportPaymentStatusByEventId(int eventId, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllMemberJoinEventByRoleEventId(int eventId, int filterIndex, int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllEventRole();
	ResponseMessage getListMemberEventToUpdateRole(int eventId);
	
}
