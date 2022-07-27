package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.UserEventDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.service.MemberEventService;

@SpringBootTest
public class MemberEventControllerTest {

	@MockBean
	private MemberEventService memberEventService;

	@Mock
	private EventRepository eventRepository;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	MemberEventController memberEventController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	public Optional<Event> createEvent() {
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 7, 29, 0, 0);
		Event event = new Event();
		event.setId(8);
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(0);
		event.setCreatedBy("LinhLHN");
		event.setCreatedOn(LocalDateTime.now());
		event.setDescription("Gẹt gô");
		event.setMaxQuantityComitee(12);
		event.setName("Đi Đà Lạt");
		event.setSemester("Summer2022");
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(0);
		event.setUpdatedBy("LinhLHN");
		event.setUpdatedOn(LocalDateTime.now());
		event.setRegistrationMemberDeadline(dateTimeRegistrationDeadline);
		event.setRegistrationOrganizingCommitteeDeadline(dateTimeRegistrationDeadline);
		Optional<Event> eventOp = Optional.of(event);
		return eventOp;
	}
	
	public UserEventDto createUserEventDto() {
		UserEventDto userEventDto = new UserEventDto();
		userEventDto.setEventId(8);
		userEventDto.setEventName("Đi Đà Lạt");
		userEventDto.setUserName("dam van toan 06");
		userEventDto.setUserStudentId("HE140860");
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(1);
		roleEventDto.setName("ROLE_Member");
		userEventDto.setRoleEventDto(roleEventDto);
		return userEventDto;
	}

	@Test
	public void registerToJoinEventControllerSuccess() throws Exception {
		Optional<Event> event = createEvent();
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
		responseMessage.setData(Arrays.asList(createUserEventDto()));
		
		when(eventRepository.findById(anyInt())).thenReturn(event);
		when(memberEventService.registerToJoinEvent(anyInt(), anyString())).thenReturn(responseMessage);
		
		
		this.mockMvc
		.perform(post("/api/event/registertojoinevent/{eventId}/{studentId}","8","HE140860"))
		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}

}
