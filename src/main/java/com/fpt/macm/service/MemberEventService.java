package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.dto.MemberNotJoinEventDto;
import com.fpt.macm.model.response.ResponseMessage;

public interface MemberEventService {

	ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto);
	ResponseMessage getAllMemberCancelJoinEvent(int eventId);
	ResponseMessage updateMemberEventPaymentStatus(int memberEventId);
	ResponseMessage getReportPaymentStatusByEventId(int eventId);
	ResponseMessage getAllMemberJoinEventByRoleEventId(int eventId, int filterIndex);
	ResponseMessage getAllRoleByEventId(int eventId);
	ResponseMessage getAllSuggestionRole();
	ResponseMessage getAllOrganizingCommitteeRoleByEventId(int eventId);
	ResponseMessage getListMemberEventToUpdateRole(int eventId);
	ResponseMessage getListMemberNotJoinEvent(int eventId, int pageNo, int pageSize);
	ResponseMessage addListMemberJoinEvent(int eventId, List<MemberNotJoinEventDto> listToJoin);
	ResponseMessage registerToJoinEvent(int eventId, String studentId);
	ResponseMessage registerToJoinOrganizingCommittee(int eventId, String studentId, int roleEventId);
	ResponseMessage cancelToJoinEvent(int eventId, String studentId);
	ResponseMessage getAllEventByStudentId(String studentId);
}
