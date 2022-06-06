package com.fpt.macm.service;

import com.fpt.macm.model.News;
import com.fpt.macm.model.ResponseMessage;

public interface NewsService {

	ResponseMessage createNews(News news, boolean isSendNotification);
	
}
