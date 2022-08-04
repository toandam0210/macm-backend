package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.EventScheduleService;

@SpringBootTest
public class EventScheduleControllerTest {

	@MockBean
	EventScheduleService eventScheduleService;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	EventScheduleController eventScheduleController;
	
	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	public Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Đi Đà Lạt");
		event.setDescription("Gẹt gô");
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setMaxQuantityComitee(12);
		event.setSemester(semester().getName());
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		event.setRegistrationMemberDeadline(LocalDateTime.now().plusMonths(1));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusMonths(1));
		return event;
	}
	
	public EventSchedule eventSchedule() {
		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setId(1);
		eventSchedule.setEvent(event());
		eventSchedule.setDate(LocalDate.now().plusMonths(1));
		eventSchedule.setStartTime(LocalTime.now());
		eventSchedule.setFinishTime(LocalTime.now().plusHours(8));
		return eventSchedule;
	}
	
	public Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 8, 31));
		return semester;
	}
	
	private ScheduleDto scheduleDto() {
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setTitle("Đi Đà Lạt");
		scheduleDto.setDate(LocalDate.now().plusMonths(1));
		scheduleDto.setStartTime(LocalTime.now());
		scheduleDto.setFinishTime(LocalTime.now().plusHours(8));
		scheduleDto.setExisted(false);
		return scheduleDto;
	}

	@Test
	public void getEventScheduleByEventSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventSchedule()));
		
		when(eventScheduleService.getListEventScheduleByEvent(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/eventschedule/geteventschedulebyevent/{eventId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	
	@Test
	public void createPreviewEventScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(scheduleDto()));
		
		when(eventScheduleService.createPreviewEventSchedule(anyString(),anyString(),anyString(),anyString(),anyString())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(post("/api/eventschedule/headculture/createpreview")
				.param("eventName", "")
				.param("startDate", "")
				.param("finishDate", "")
				.param("startTime", "")
				.param("finishTime", ""))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void createEventScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventSchedule()));
		
		when(eventScheduleService.createEventSchedule(anyInt(), anyList(), anyBoolean())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(post("/api/eventschedule/headculture/addnewschedule/{eventId}", "1")
				.content(asJsonString(Arrays.asList(scheduleDto()))).contentType(MediaType.APPLICATION_JSON)
				.param("isOverwritten", "true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getEventSessionByDateSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventSchedule()));
		
		when(eventScheduleService.getEventSessionByDate(anyString())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/eventschedule/geteventsessionbydate")
				.param("date", "28/08/2022"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updatePreviewEventScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(scheduleDto()));
		
		when(eventScheduleService.updatePreviewEventSchedule(anyInt(), anyString(), anyString(), anyString(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(post("/api/eventschedule/headculture/updatepreview/{eventId}", "1")
				.param("startDate", "")
				.param("finishDate", "")
				.param("startTime", "")
				.param("finishTime", ""))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateEventScheduleSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventSchedule()));
		
		when(eventScheduleService.updateEventSchedule(anyInt(), anyList(), anyBoolean())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(post("/api/eventschedule/headculture/updateschedule/{eventId}", "1")
				.content(asJsonString(Arrays.asList(scheduleDto()))).contentType(MediaType.APPLICATION_JSON)
				.param("isOverwritten", "true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
			String result = mapper.writeValueAsString(obj);

			return result;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
