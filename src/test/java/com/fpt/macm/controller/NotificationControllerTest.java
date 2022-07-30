package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fpt.macm.model.dto.UserNotificationDto;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.NotificationService;

@SpringBootTest
public class NotificationControllerTest {

	@MockBean
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

	private UserNotificationDto userNotificationDto() {
		UserNotificationDto userNotificationDto = new UserNotificationDto();
		userNotificationDto.setId(1);
		userNotificationDto.setMessage("Test");
		userNotificationDto.setNotificationType(0);
		userNotificationDto.setNotificationTypeId(1);
		userNotificationDto.setCreatedOn(LocalDateTime.now());
		userNotificationDto.setRead(false);
		userNotificationDto.setUserName("dam van toan 01");
		userNotificationDto.setStudentId("HE140855");
		return userNotificationDto;
	}

	private List<String> messages() {
		List<String> messages = new ArrayList<String>();
		messages.add("Membership kỳ Summer2022: 50000VND");
		messages.add("Membership kỳ Summer2022: 100000VND");
		messages.add("Membership kỳ Summer2022: 200000VND");
		return messages;
	}

	@Test
	public void getAllNotificationByStudentIdSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userNotificationDto()));

		when(notificationService.getAllNotificationByStudentId(anyString(), anyInt(), anyInt(), anyString()))
				.thenReturn(responseMessage);

		this.mockMvc
				.perform(get("/api/notification/getallnotificationbystudentid/{studentId}", "HE140855")
						.param("pageNo", "0").param("pageSize", "1000").param("sortBy", "id"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}

	@Test
	public void checkPaymentStatusSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(messages());

		when(notificationService.checkPaymentStatus(anyString())).thenReturn(responseMessage);

		this.mockMvc.perform(get("/api/notification/checkpaymentstatus/{studentId}", "HE140855"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(3));
	}

	@Test
	public void markNotificationAsReadSuccess() throws Exception {
		UserNotificationDto userNotificationDto = userNotificationDto();
		userNotificationDto.setRead(true);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userNotificationDto));

		when(notificationService.markNotificationAsRead(anyInt(), anyString())).thenReturn(responseMessage);

		this.mockMvc
				.perform(put("/api/notification/marknotificationasread/{notificationId}/{studentId}", "1", "HE140855"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}

	@Test
	public void markAllNotificationAsReadSuccess() throws Exception {
		UserNotificationDto userNotificationDto = userNotificationDto();
		userNotificationDto.setRead(true);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userNotificationDto));

		when(notificationService.markAllNotificationAsRead(anyString())).thenReturn(responseMessage);

		this.mockMvc.perform(put("/api/notification/markallnotificationasread/{studentId}", "HE140855"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}

}