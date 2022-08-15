package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface DashboardService {
	ResponseMessage getCollaboratorReport();
	ResponseMessage attendanceReport(String semester);
	ResponseMessage eventReport();
	ResponseMessage statusMemberReport();
	ResponseMessage feeReport(String semester);
	ResponseMessage getAllUpcomingActivities();
	ResponseMessage activityReport(String semester);
	
}
