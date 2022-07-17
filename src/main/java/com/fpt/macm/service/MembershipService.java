package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface MembershipService {
	ResponseMessage getListMemberPayMembershipBySemester(int membershipInfoId);
	ResponseMessage updateStatusPaymenMembershipById(int id);
	ResponseMessage updateMembershipBySemester(double amount, String semester);
	ResponseMessage getMembershipInfoBySemester(String semester);
	ResponseMessage getReportMembershipPaymentStatus(int membershipInfoId, int pageNo, int pageSize, String sortBy);
}
