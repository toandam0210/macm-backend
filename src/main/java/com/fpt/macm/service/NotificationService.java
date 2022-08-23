package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface NotificationService {

	ResponseMessage getAllNotificationByStudentId(String studentId, int pageNo, int pageSize, String sortBy);
	ResponseMessage sendNotificationToAllUser(Notification notification);
	ResponseMessage sendNotificationToAnUser(User user, Notification notification);
	ResponseMessage checkPaymentStatus(String studentId);
	ResponseMessage markNotificationAsRead(int notificationId, String studentId);
	ResponseMessage markAllNotificationAsRead(String studentId);
	ResponseMessage createTournamentCreateNotification(int tournamentId, String tournamentName);
	ResponseMessage createTournamentDeleteNotification(int tournamentId, String tournamentName);
	ResponseMessage createEventCreateNotification(int eventId, String eventName);
	ResponseMessage createEventDeleteNotification(int eventId, String eventName);
	ResponseMessage createTrainingSessionCreateNotification(LocalDate date);
	ResponseMessage createTrainingSessionUpdateNotification(LocalDate date, LocalTime newStartTime, LocalTime newEndTime);
	ResponseMessage createTrainingSessionDeleteNotification(LocalDate date);
	ResponseMessage getAllUnreadNotificationByStudentId(String studentId, int pageNo, int pageSize, String sortBy);
}
