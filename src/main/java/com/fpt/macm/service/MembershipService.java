package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface MembershipService {
	ResponseMessage getListMemberPayMembershipBySemester(int membershipInfoId);
	ResponseMessage updateStatusPaymenMembershipById(int id);
	ResponseMessage updateMembershipBySemester(double amount, String semester);
}
