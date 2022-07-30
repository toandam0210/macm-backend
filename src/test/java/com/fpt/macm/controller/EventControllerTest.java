package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.fpt.macm.model.dto.EventDto;
import com.fpt.macm.model.dto.UserEventSemesterDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.EventService;

@SpringBootTest
public class EventControllerTest {

	@MockBean
	private EventService eventService;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	private EventController eventController;

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
		event.setRegistrationMemberDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		return event;
	}

	public EventDto eventDto() {
		EventDto eventDto = new EventDto();
		eventDto.setId(event().getId());
		eventDto.setName(event().getName());
		eventDto.setDescription(event().getDescription());
		eventDto.setAmountFromClub(1000000);
		eventDto.setAmountPerMemberRegister(event().getAmountPerRegisterEstimated());
		eventDto.setMaxQuantityComitee(event().getMaxQuantityComitee());
		eventDto.setStatus("Chưa diễn ra");
		eventDto.setTotalAmount(event().getTotalAmountEstimated());
		eventDto.setRegistrationMemberDeadline(event().getRegistrationMemberDeadline());
		eventDto.setRegistrationOrganizingCommitteeDeadline(event().getRegistrationOrganizingCommitteeDeadline());
		eventDto.setStartDate(eventSchedules().get(0).getDate());
		return eventDto;
	}
	
	public List<EventSchedule> eventSchedules() {
		List<EventSchedule> eventSchedules = new ArrayList<EventSchedule>();
		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setId(1);
		eventSchedule.setEvent(event());
		eventSchedule.setDate(LocalDate.of(2022, 10, 30));
		eventSchedule.setStartTime(LocalTime.now());
		eventSchedule.setFinishTime(LocalTime.now().plusHours(8));
		eventSchedules.add(eventSchedule);
		eventSchedule.setId(2);
		eventSchedule.setDate(LocalDate.of(2022, 10, 31));
		eventSchedules.add(eventSchedule);
		return eventSchedules;
	}
	
	public Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 8, 31));
		return semester;
	}
	
	private User user() {
		User user = new User();
		user.setStudentId("HE140860");
		user.setId(1);
		user.setName("dam van toan 06");
		user.setActive(true);
		Role role = new Role();
		role.setId(8);
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		return user;
	}
	
	public UserEventSemesterDto userEventSemesterDto() {
		UserEventSemesterDto userEventSemesterDto = new UserEventSemesterDto();
		userEventSemesterDto.setEventDto(eventDto());
		userEventSemesterDto.setJoin(true);
		userEventSemesterDto.setUserName(user().getName());
		userEventSemesterDto.setStudentId(user().getStudentId());
		return userEventSemesterDto;
	}

	@Test
	public void createEventSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(event()));

		when(eventService.createEvent(any())).thenReturn(responseMessage);

		this.mockMvc
				.perform(post("/api/event/headculture/createevent").content(asJsonString(event()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}

	@Test
	public void getEventByNameSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventDto()));
		
		when(eventService.getEventsByName(anyString(), anyInt(), anyInt(), anyString())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/geteventsbyname")
				.param("name", "Đi Đà Lạt")
				.param("pageNo", "0")
				.param("pageSize", "1000")
				.param("sortBy", "id"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateBeforeEventSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(event()));

		when(eventService.updateBeforeEvent(anyInt(), any())).thenReturn(responseMessage);

		this.mockMvc
				.perform(put("/api/event/headculture/updatebeforeevent/{eventId}","1")
						.content(asJsonString(event()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void deleteEventSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(event()));

		when(eventService.deleteEvent(anyInt())).thenReturn(responseMessage);

		this.mockMvc
				.perform(put("/api/event/headculture/deleteevent/{eventId}","1")
						.content(asJsonString(event()))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getEventByDateSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventDto()));
		
		when(eventService.getEventsByDate(any(), any(), anyInt(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/geteventsbydate")
				.param("startDate", LocalDate.of(2022, 10, 30).toString())
				.param("finishDate", LocalDate.of(2022, 10, 31).toString())
				.param("pageNo", "0")
				.param("pageSize", "1000"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getEventBySemesterSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventDto()));
		
		when(eventService.getEventsBySemester(anyString(), anyInt(), anyInt(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/geteventsbysemester")
				.param("semester", "Summer2022")
				.param("month", "0")
				.param("pageNo", "0")
				.param("pageSize", "1000"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getStartDateOfEventSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(eventSchedules().get(0)));
		
		when(eventService.getStartDateOfEvent(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/headculture/getstartdate/{eventId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateAfterEventSuccess() throws Exception {
		Event event = event();
		event.setTotalAmountActual(200000);
		event.setAmountPerRegisterActual(100000);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(event));
		
		when(eventService.updateAfterEvent(anyInt(), anyDouble(), anyBoolean(), anyBoolean())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(put("/api/event/headculture/updateafterevent/{eventId}", "1")
				.param("money", "100000")
				.param("isIncurred", "true")
				.param("isUseClubFund", "true"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getEventByIdSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(event()));
		
		when(eventService.getEventById(anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/geteventbyid/{eventId}", "1"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getEventBySemesterAndStudentIdSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userEventSemesterDto()));
		
		when(eventService.getEventsBySemesterAndStudentId(anyString(), anyString(), anyInt(), anyInt(), anyInt())).thenReturn(responseMessage);
		
		this.mockMvc
		.perform(get("/api/event/geteventsbysemesterandstudentid/{studentId}", "HE140860")
				.param("semester", "Summer2022")
				.param("month", "0")
				.param("pageNo", "0")
				.param("pageSize", "1000")
				.param("sortBy", "id"))
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
