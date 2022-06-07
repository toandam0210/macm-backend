package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

	@Override
	public ResponseMessage getAllNews(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<News> pageResponse = newsRepository.findAll(paging);
			List<News> newsList = new ArrayList<News>();
			if (pageResponse != null && pageResponse.hasContent()) {
				newsList = pageResponse.getContent();
			}
			responseMessage.setData(newsList);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
