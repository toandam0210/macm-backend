package com.fpt.macm.service;

import com.fpt.macm.model.Notification;
import com.fpt.macm.model.ResponseMessage;

public interface NotificationService {

	ResponseMessage getAllNotification();
	ResponseMessage createNotification(Notification notification);
	ResponseMessage sendNotificationToAllUser(Notification notification);
	
}
