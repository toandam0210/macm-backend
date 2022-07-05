package com.fpt.macm.service;

import com.fpt.macm.model.Notification;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.User;

public interface NotificationService {

	ResponseMessage getAllNotification();
	ResponseMessage createNotification(Notification notification);
	void sendNotificationToAllUser(Notification notification);
	void sendNotificationToAnUser(User user, Notification notification);
}
