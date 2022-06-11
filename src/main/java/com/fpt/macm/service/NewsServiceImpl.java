package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
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
			responseMessage.setMessage(Constant.MSG_012);
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
			responseMessage.setMessage(Constant.MSG_013);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getNewsById(int newsId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<News> newsOp = newsRepository.findById(newsId);
			News news = newsOp.get();
			responseMessage.setData(Arrays.asList(news));
			responseMessage.setMessage(Constant.MSG_014);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteNewsById(int newsId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<News> newsOp = newsRepository.findById(newsId);
			News news = newsOp.get();
			newsRepository.delete(news);
			responseMessage.setData(Arrays.asList(news));
			responseMessage.setMessage(Constant.MSG_015);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage editNews(int newsId, News news) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<News> newsOp = newsRepository.findById(newsId);
			News oldNews = newsOp.get();
			oldNews.setTitle(news.getTitle());
			oldNews.setDescription(news.getDescription());
			oldNews.setUpdatedBy("toandv");
			oldNews.setUpdatedOn(LocalDateTime.now());
			newsRepository.save(oldNews);
			responseMessage.setData(Arrays.asList(oldNews));
			responseMessage.setMessage(Constant.MSG_023);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateNewsStatus(int newsId, boolean status) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<News> newsOp = newsRepository.findById(newsId);
			News news = newsOp.get();
			news.setStatus(status);
			news.setUpdatedBy("toandv");
			news.setUpdatedOn(LocalDateTime.now());
			newsRepository.save(news);
			responseMessage.setData(Arrays.asList(news));
			responseMessage.setMessage(Constant.MSG_024);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
