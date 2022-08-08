package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.fpt.macm.model.dto.AttendanceEventDto;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.AttendanceEventService;

@SpringBootTest
public class AttendanceEventControllerTest {

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	AttendanceEventController attendanceEventController;

	@MockBean
	AttendanceEventService attendanceEventService;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	private Event event() {
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
	
	private Semester semester() {
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
	
	public MemberEvent memberEvent() {
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setEvent(event());
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user());
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		return memberEvent;
	}
	
//	public AttendanceEvent attendanceEvent() {
//		AttendanceEvent attendanceEvent = new AttendanceEvent();
//		attendanceEvent.setId(1);
//		attendanceEvent.setMemberEvent(memberEvent());
//		attendanceEvent.setEvent(event());
//		attendanceEvent.setStatus(2);
//		return attendanceEvent;
//	}
//	
//	public AttendanceEventDto attendanceEventDto() {
//		AttendanceEventDto attendanceEventDto = new AttendanceEventDto();
//		attendanceEventDto.setEventName(attendanceEvent().getEvent().getName());
//		attendanceEventDto.setName(attendanceEvent().getMemberEvent().getUser().getName());
//		attendanceEventDto.setStudentId(attendanceEvent().getMemberEvent().getUser().getStudentId());
//		attendanceEventDto.setStatus(attendanceEvent().getStatus());
//		attendanceEventDto.setDate(eventSchedule().getDate());
//		return attendanceEventDto;
//	}
	
	public EventSchedule eventSchedule() {
		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setId(1);
		eventSchedule.setEvent(event());
		eventSchedule.setDate(LocalDate.now());
		eventSchedule.setStartTime(LocalTime.of(18, 0));
		eventSchedule.setFinishTime(LocalTime.of(20, 0));
		return eventSchedule;
	}
	
//	@Test
//	public void takeAttendanceEventSuccess() throws Exception {
//		AttendanceEvent attendanceEvent = attendanceEvent();
//		attendanceEvent.setStatus(1);
//		
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(attendanceEvent()));
//		
//		when(attendanceEventService.takeAttendanceByMemberEventId(anyInt(), anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc.perform(put("/api/event/headculture/takeattendanceevent/{memberEventId}", "1")
//				.param("status", "1"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.data.size()").value("1"));
//	}
//	
//	@Test
//	public void checkAttendanceByEventIdSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(attendanceEventDto()));
//		
//		when(attendanceEventService.checkAttendanceStatusByEventId(anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc.perform(get("/api/event/headculture/checkattendance/{eventId}", "1"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//				.andExpect(jsonPath("$.data.size()").value("1"));
//	}
}
