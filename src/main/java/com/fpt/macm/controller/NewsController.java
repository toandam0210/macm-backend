package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.entity.News;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

	@Autowired
	NewsService newsService;
	
	@PostMapping("/headcommunication/createnews")
	ResponseEntity<ResponseMessage> createNews(@RequestBody News news, @RequestParam boolean isSendNotification){
		return new ResponseEntity<ResponseMessage>(newsService.createNews(news, isSendNotification), HttpStatus.OK);
	}
	
	@GetMapping("/getallnews")
	ResponseEntity<ResponseMessage> getAllNews(@RequestParam(defaultValue = "0") int pageNo,
			@RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "id") String sortBy){
		return new ResponseEntity<ResponseMessage>(newsService.getAllNews(pageNo, pageSize, sortBy), HttpStatus.OK);
	}
	
	@GetMapping("/getnewsbyid/{newsId}")
	ResponseEntity<ResponseMessage> getNewsById(@PathVariable(name = "newsId") int newsId){
		return new ResponseEntity<ResponseMessage>(newsService.getNewsById(newsId), HttpStatus.OK);
	}
	
	@DeleteMapping("/headcommunication/deletenewsbyid/{newsId}")
	ResponseEntity<ResponseMessage> deleteNewsById(@PathVariable(name = "newsId") int newsId){
		return new ResponseEntity<ResponseMessage>(newsService.deleteNewsById(newsId), HttpStatus.OK);
	}
	
	@PutMapping("/headcommunication/editnews/{newsId}")
	ResponseEntity<ResponseMessage> editNews(@PathVariable(name = "newsId") int newsId, @RequestBody News news){
		return new ResponseEntity<ResponseMessage>(newsService.editNews(newsId, news), HttpStatus.OK);
	}
	
	@PutMapping("/headcommunication/updatenewsstatus/{newsId}")
	ResponseEntity<ResponseMessage> updateNewsStatus(@PathVariable(name = "newsId") int newsId, @RequestParam boolean status){
		return new ResponseEntity<ResponseMessage>(newsService.updateNewsStatus(newsId, status), HttpStatus.OK);
	}
}
