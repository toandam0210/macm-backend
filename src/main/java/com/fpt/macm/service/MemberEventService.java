package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.response.ResponseMessage;

public interface MemberEventService {

	ResponseMessage updateListMemberEventRole(List<MemberEventDto> membersEventDto);
	ResponseMessage getAllMemberCancelJoinEvent(int eventId);
	ResponseMessage updateMemberEventPaymentStatus(String studentId, int memberEventId);
	ResponseMessage getReportPaymentStatusByEventId(int eventId);
	ResponseMessage getAllMemberJoinEventByRoleEventId(int eventId, int filterIndex);
	ResponseMessage getAllRoleByEventId(int eventId);
	ResponseMessage getAllOrganizingCommitteeRoleByEventId(int eventId);
	ResponseMessage getListMemberEventToUpdateRole(int eventId);
	ResponseMessage getListMemberNotJoinEvent(int eventId);
	ResponseMessage addListMemberJoinEvent(int eventId, List<MemberEventDto> listToJoin);
	ResponseMessage registerToJoinEvent(int eventId, String studentId);
	ResponseMessage registerToJoinOrganizingCommittee(int eventId, String studentId, int roleEventId);
	ResponseMessage acceptRequestToJoinEvent(int memberEventId);
	ResponseMessage declineRequestToJoinEvent(int memberEventId);
	ResponseMessage deleteMemberEvent(int memberEventId);
	ResponseMessage getAllEventByStudentId(String studentId);
	
}
