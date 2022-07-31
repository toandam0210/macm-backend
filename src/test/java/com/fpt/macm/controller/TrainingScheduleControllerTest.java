package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.TrainingScheduleService;

@SpringBootTest
public class TrainingScheduleControllerTest {

	@MockBean
	TrainingScheduleService trainingScheduleService;
	
	@InjectMocks
	TrainingScheduleController trainingScheduleController;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	private TrainingSchedule trainingSchedule() {
		TrainingSchedule trainingSchedule = new TrainingSchedule();
		trainingSchedule.setId(1);
		trainingSchedule.setDate(LocalDate.now());
		trainingSchedule.setStartTime(LocalTime.of(18, 0));
		trainingSchedule.setFinishTime(LocalTime.of(20, 0));
		return trainingSchedule;
	}
	
	private ScheduleDto scheduleDto() {
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setTitle("Lịch tập");
		scheduleDto.setExisted(false);
		scheduleDto.setDate(LocalDate.now());
		scheduleDto.setStartTime(LocalTime.of(18, 0));
		scheduleDto.setFinishTime(LocalTime.of(20, 0));
		return scheduleDto;
	}
	
	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setId(1);
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setDate(LocalDate.now());
		commonSchedule.setStartTime(LocalTime.of(18, 0));
		commonSchedule.setFinishTime(LocalTime.of(20, 0));
		commonSchedule.setType(0);
		return commonSchedule;
	}
	
	@Test
	public void getTrainingScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.getListTrainingSchedule()).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/trainingschedule/gettrainingschedule"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTrainingSessionSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.createTrainingSession(any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/trainingschedule/headtechnique/addnewsession")
				.content(asJsonString(trainingSchedule())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createPreviewSuccess() throws Exception {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.now().getDayOfWeek().toString());
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(scheduleDto()));
		
		when(trainingScheduleService.createPreviewTrainingSchedule(anyString(), anyString(), anyList(), anyString(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/trainingschedule/headtechnique/createpreview")
				.param("startDate", "")
				.param("finishDate", "")
				.content(asJsonString(dayOfWeeks)).contentType(MediaType.APPLICATION_JSON)
				.param("startTime", "")
				.param("finishTime", ""))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void createTrainingScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.createTrainingSchedule(anyList())).thenReturn(responseMessage);
		
		this.mockMvc.perform(post("/api/trainingschedule/headtechnique/addnewschedule")
				.content(asJsonString(Arrays.asList(scheduleDto()))).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void updateTrainingSessionTimeSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.updateTrainingSessionTime(anyString(), any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/trainingschedule/headtechnique/updatesession")
				.param("date", "")
				.content(asJsonString(commonSchedule())).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void deleteTrainingSessionSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.deleteTrainingSession(any())).thenReturn(responseMessage);
		
		this.mockMvc.perform(put("/api/trainingschedule/headtechnique/deletesession")
				.param("date", ""))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTrainingScheduleBySemesterSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.getTraningScheduleBySemester(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/trainingschedule/gettrainingschedulebysemester/{semesterId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	@Test
	public void getTrainingSessionByDateSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(trainingSchedule()));
		
		when(trainingScheduleService.getTrainingSessionByDate(anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/trainingschedule/gettrainingsesionbydate")
				.param("date", ""))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value("1"));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	    	ObjectMapper mapper = JsonMapper.builder()
	    			   .addModule(new JavaTimeModule())
	    			   .build();
	    	String result = mapper.writeValueAsString(obj);

	      return result;
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }
	
}
