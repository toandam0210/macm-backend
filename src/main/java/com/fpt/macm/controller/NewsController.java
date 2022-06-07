package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.News;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	@PostMapping("/communication/createnews")
	ResponseEntity<ResponseMessage> createNews(@RequestBody News news, @RequestParam boolean isSendNotification){
		return new ResponseEntity<ResponseMessage>(newsService.createNews(news, isSendNotification), HttpStatus.OK);
	}
	
	@GetMapping("/communication/getallnews")
	ResponseEntity<ResponseMessage> getAllNews(){
		return new ResponseEntity<ResponseMessage>(newsService.getAllNews(), HttpStatus.OK);
	}
	
}
