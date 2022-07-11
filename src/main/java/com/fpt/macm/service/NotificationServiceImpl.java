package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Notification;
import com.fpt.macm.model.NotificationToUser;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationToUserRepository notificationToUserRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Override
	public ResponseMessage getAllNotification(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<Notification> pageResponse = notificationRepository.findAll(paging);
			List<Notification> notifications = new ArrayList<Notification>();
			
			if (pageResponse != null && pageResponse.hasContent()) {
				notifications = pageResponse.getContent();
			}
			
			responseMessage.setData(notifications);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_016);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		
		return responseMessage;
	}

	@Override
	public ResponseMessage createNotification(Notification notification) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			notification.setCreatedOn(LocalDateTime.now());
			
			notificationRepository.save(notification);
			responseMessage.setData(Arrays.asList(notification));
			responseMessage.setMessage(Constant.MSG_017);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public void sendNotificationToAllUser(Notification notification) {
		// TODO Auto-generated method stub
		try {
			List<User> users = (List<User>) userRepository.findAll();
			
			List<NotificationToUser> notificationToUsers = new ArrayList<NotificationToUser>();
			
			for (User user : users) {
				NotificationToUser notificationToUser = new NotificationToUser();
				
				notificationToUser.setNotification(notification);
				notificationToUser.setUser(user);
				notificationToUser.setRead(false);
				notificationToUser.setCreatedOn(LocalDateTime.now());
				
				notificationToUsers.add(notificationToUser);
				
				notificationToUserRepository.save(notificationToUser);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void sendNotificationToAnUser(User user, Notification notification) {
		// TODO Auto-generated method stub
		try {
			NotificationToUser notificationToUser = new NotificationToUser();
				
			notificationToUser.setNotification(notification);
			notificationToUser.setUser(user);
			notificationToUser.setRead(false);
			notificationToUser.setCreatedOn(LocalDateTime.now());
			notificationToUserRepository.save(notificationToUser);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void createTournamentNotification(int tournamentId, String tournamentName) {
		try {
			Notification notification = new Notification();
			notification.setMessage("Sắp tới có giải đấu " + tournamentName + ".");
			notification.setNotificationType(0);
			notification.setNotificationTypeId(tournamentId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	public void createEventNotification(int eventId, String eventName) {
		try {
			Notification notification = new Notification();
			notification.setMessage("Sắp tới có sự kiện " + eventName + ".");
			notification.setNotificationType(1);
			notification.setNotificationTypeId(eventId);
			notification.setCreatedOn(LocalDateTime.now());
			notificationRepository.save(notification);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	public void createTrainingSessionCreateNotification(LocalDate date) {
		Notification notification = new Notification();
		notification.setMessage("Ngày " + date + " có buổi tập mới được thêm.");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
	}
	
	public void createTrainingSessionUpdateNotification(LocalDate date, LocalTime newStartTime, LocalTime newEndTime) {
		Notification notification = new Notification();
		notification.setMessage("Buổi tập ngày " + date + " thay đổi thời gian tập thành: " + newStartTime + " - " + newEndTime + ".");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
	}

	public void createTrainingSessionDeleteNotification(LocalDate date) {
		Notification notification = new Notification();
		notification.setMessage("Buổi tập ngày " + date + " đã hủy.");
		notification.setNotificationType(2);
		notification.setNotificationTypeId(0);
		notification.setCreatedOn(LocalDateTime.now());
		notificationRepository.save(notification);
	}
}
