package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface DashboardService {
	ResponseMessage getCollaboratorReport();
	ResponseMessage attendanceReport(String semester);
	ResponseMessage eventReport();
	ResponseMessage statusMemberReport();
	ResponseMessage feeReport(String semester);
}
