package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Notification;
import com.fpt.macm.model.NotificationToUser;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	NotificationRepository notificationRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NotificationToUserRepository notificationToUserRepository;
	
	@Override
	public ResponseMessage getAllNotification() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Notification> notification = notificationRepository.findAll();
			responseMessage.setData(notification);
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
	public ResponseMessage sendNotificationToAllUser(Notification notification) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
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
			
			
			responseMessage.setData(notificationToUsers);
			responseMessage.setMessage(Constant.MSG_018);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		
		return responseMessage;
	}

}
