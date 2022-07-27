package com.fpt.macm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fpt.macm.service.NotificationService;

@SpringBootTest
public class NotificationControllerTest {

	@Mock
	private NotificationService notificationService;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	NotificationController notificationController;
	
	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
}
