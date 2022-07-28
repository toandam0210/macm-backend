package com.fpt.macm.service;

import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface NotificationService {

	ResponseMessage getAllNotificationByStudentId(String studentId, int pageNo, int pageSize, String sortBy);
//	ResponseMessage getAllNotification(int pageNo, int pageSize, String sortBy);
	ResponseMessage createNotification(Notification notification);
	void sendNotificationToAllUser(Notification notification);
	void sendNotificationToAnUser(User user, Notification notification);
	ResponseMessage checkPaymentStatus(String studentId);
}
