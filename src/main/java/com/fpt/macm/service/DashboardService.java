package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface DashboardService {
	ResponseMessage getCollaboratorReport();
	ResponseMessage attendanceReport(String semester, int month);
	ResponseMessage eventReport();
	ResponseMessage statusMemberReport(String semester);
	ResponseMessage feeReport(String semester);
	ResponseMessage getAllUpcomingActivities(int filterIndex);
	ResponseMessage activityReport(String semester);
	
}
