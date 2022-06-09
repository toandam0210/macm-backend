package com.fpt.macm.service;

import com.fpt.macm.model.News;
import com.fpt.macm.model.ResponseMessage;

public interface NewsService {

	ResponseMessage createNews(News news, boolean isSendNotification);
	ResponseMessage getAllNews(int pageNo, int pageSize, String sortBy);
	ResponseMessage getNewsById(int newsId);
	ResponseMessage deleteNewsById(int newsId);
	ResponseMessage editNews(int newsId, News news);
	ResponseMessage updateNewsStatus(int newsId, boolean status);
}