package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
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

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.AttendanceStatusDto;
import com.fpt.macm.model.dto.UserAttendanceTrainingReportDto;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.AttendanceStatusService;


@SpringBootTest
public class AttendanceStatusControllerTest {
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	AttendanceStatusController attendanceStatusController;
	
	@MockBean
	private AttendanceStatusService attendanceStatusService;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	private AttendanceStatusDto attendanceStatusDto() {
		AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
		attendanceStatusDto.setDate(LocalDate.now());
		attendanceStatusDto.setName("toan");
		attendanceStatusDto.setStatus(0);
		attendanceStatusDto.setStudentId("HE140855");
		return attendanceStatusDto;
	}
	
	private UserAttendanceTrainingReportDto userAttendanceTrainingReportDto() {
		UserAttendanceTrainingReportDto userAttendanceTrainingReportDto = new UserAttendanceTrainingReportDto();
		userAttendanceTrainingReportDto.setPercentAbsent(50);
		userAttendanceTrainingReportDto.setRoleName("Chu nhiem");
		userAttendanceTrainingReportDto.setStudentId("HE140855");
		userAttendanceTrainingReportDto.setStudentName("toan");
		userAttendanceTrainingReportDto.setTotalAbsent("1");
		return userAttendanceTrainingReportDto;
	}
	
	
//	@Test
//	public void takeAttendanceSuccessTest() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(attendanceStatusDto()));
//		responseMessage.setMessage(Constant.MSG_056);
//		when(attendanceStatusService.takeAttendanceByStudentId(anyString(), anyInt())).thenReturn(responseMessage);
//		this.mockMvc.perform(put("/api/admin/headtechnique/takeattendance/{studentId}", "HE140855").param("status", "1")
//		.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(content()
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.message").value(Constant.MSG_056));
//	}
	
//	@Test
//	public void checkAttendanceSuccessTest() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(attendanceStatusDto()));
//		when(attendanceStatusService.checkAttendanceStatusByTrainingSchedule(anyInt())).thenReturn(responseMessage);
//		this.mockMvc.perform(get("/api/admin/headtechnique/checkattendance/{trainingScheduleId}", 37)
//		.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk())
//		.andExpect(content()
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value("1"));
//	}
	
	@Test
	public void attendanceReportSuccessTest() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userAttendanceTrainingReportDto()));
		when(attendanceStatusService.attendanceTrainingReport(anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/headtechnique/checkattendance/report").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
}
