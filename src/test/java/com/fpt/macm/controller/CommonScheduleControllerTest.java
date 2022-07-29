package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

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

import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.CommonScheduleService;

@SpringBootTest
public class CommonScheduleControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	CommonScheduleController commonScheduleController;

	@MockBean
	private CommonScheduleService commonScheduleService;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setDate(LocalDate.now());
		commonSchedule.setFinishTime(LocalTime.of(20, 0));
		commonSchedule.setId(1);
		commonSchedule.setStartTime(LocalTime.of(18, 0));
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setType(0);
		return commonSchedule;
	}
	
	@Test
	public void getCommonScheduleSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(commonSchedule()));
		when(commonScheduleService.getCommonSchedule()).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/commonschedule/getcommonschedule").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}

	@Test
	public void getCommonScheduleByDateSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(commonSchedule()));
		when(commonScheduleService.getCommonScheduleByDate(anyString())).thenReturn(responseMessage);
		this.mockMvc
				.perform(get("/api/commonschedule/getcommonsessionbydate").param("date", "26/06/2022")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getCommonScheduleBySemesterSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(commonSchedule()));
		when(commonScheduleService.getCommonScheduleBySemester(anyInt())).thenReturn(responseMessage);
		this.mockMvc
				.perform(get("/api/commonschedule/getcommonschedulebysemester/{semesterId}", 1)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value("1"));
	}
}
