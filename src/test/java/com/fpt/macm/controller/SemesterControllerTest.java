package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.SemesterService;

@SpringBootTest
public class SemesterControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	SemesterController semesterController;
	
	@MockBean
	SemesterService semesterService;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}
	private List<Integer> months(){
		List<Integer> months = new ArrayList<Integer>();
		Integer [] list = {5, 6, 7, 8};
		months = Arrays.asList(list);
		return months;
	}
	@Test
	public void getListMonthsBySemestersSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(months());
		when(semesterService.getListMonthsBySemester(anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/semester/getlistmonths").param("semester", "Summer")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("4"));
	}
	@Test
	public void getTop3SemestersSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getTop3Semesters()).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/semester/gettop3semesters")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getCurrentSemestersSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/semester/currentsemester")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
}
