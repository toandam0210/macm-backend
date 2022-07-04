package com.fpt.macm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SemesterControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	SemesterController semesterController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void getListMonthsBySemestersSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/semester/getlistmonths").param("semester", "Spring")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("4"));
	}
	@Test
	public void getListMonthsBySemestersSuccessTest2() throws Exception {
		this.mockMvc.perform(get("/api/semester/getlistmonths").param("semester", "Summer")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("4"));
	}
	@Test
	public void getListMonthsBySemestersSuccessTest3() throws Exception {
		this.mockMvc.perform(get("/api/semester/getlistmonths").param("semester", "Fall")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("4"));
	}
	@Test
	public void getTop3SemestersSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/semester/gettop3semesters")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("2"));
	}
	
	@Test
	public void getCurrentSemestersSuccessTest() throws Exception {
		this.mockMvc.perform(get("/api/semester/currentsemester")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
}
