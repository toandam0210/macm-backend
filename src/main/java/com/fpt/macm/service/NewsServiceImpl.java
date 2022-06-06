package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.News;
import com.fpt.macm.model.Notification;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.NewsRepository;

@Service
public class NewsServiceImpl implements NewsService{

	@Autowired
	NewsRepository newsRepository;
	
	@Autowired
	NotificationService notificationService;
	
	@Override
	public ResponseMessage createNews(News news, boolean isSendNotification) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			news.setStatus(true);
			news.setCreatedBy("toandv");
			news.setCreatedOn(LocalDateTime.now());
			newsRepository.save(news);
			
			if(isSendNotification) {
				Notification notification = new Notification();
				notification.setMessage(news.getTitle());
				
				notificationService.createNotification(notification);
				notificationService.sendNotificationToAllUser(notification);
			}
			
			responseMessage.setData(Arrays.asList(news));
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
