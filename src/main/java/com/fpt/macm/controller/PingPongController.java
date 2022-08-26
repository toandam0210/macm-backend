package com.fpt.macm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.macm.model.response.Message;

//@RestController
//@EnableScheduling
//public class PingPongController {
//	
//	@Autowired
//    SimpMessagingTemplate template;
//	
//	@MessageMapping("/message")
//    @SendTo("/chatroom/public")
//	@Scheduled(fixedDelay = 10000L)
//    public Message receiveMessage(@Payload Message message){
//		message.setMessage("Pong");
//        return message;
//    }
//}
