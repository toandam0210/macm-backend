//package com.fpt.macm.controller;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fpt.macm.constant.Constant;
//import com.fpt.macm.model.dto.EventPaymentStatusReportDto;
//import com.fpt.macm.model.dto.MemberEventDto;
//import com.fpt.macm.model.dto.MemberNotJoinEventDto;
//import com.fpt.macm.model.dto.RoleEventDto;
//import com.fpt.macm.model.dto.UserEventDto;
//import com.fpt.macm.model.entity.Event;
//import com.fpt.macm.model.entity.MemberEvent;
//import com.fpt.macm.model.entity.Role;
//import com.fpt.macm.model.entity.RoleEvent;
//import com.fpt.macm.model.entity.User;
//import com.fpt.macm.model.response.ResponseMessage;
//import com.fpt.macm.repository.EventRepository;
//import com.fpt.macm.service.MemberEventService;
//import com.fpt.macm.utils.Utils;
//
//@SpringBootTest
//public class MemberEventControllerTest {
//
//	@MockBean
//	private MemberEventService memberEventService;
//
//	@Mock
//	private EventRepository eventRepository;
//	
//	@Autowired
//	private WebApplicationContext context;
//
//	private MockMvc mockMvc;
//
//	@InjectMocks
//	MemberEventController memberEventController;
//
//	@BeforeEach
//	public void setup() throws Exception {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
//	}
//	
//	public UserEventDto createUserEventDto() {
//		UserEventDto userEventDto = new UserEventDto();
//		userEventDto.setEventId(8);
//		userEventDto.setEventName("Đi Đà Lạt");
//		userEventDto.setUserName("dam van toan 06");
//		userEventDto.setUserStudentId("HE140860");
//		RoleEventDto roleEventDto = new RoleEventDto();
//		roleEventDto.setId(1);
//		roleEventDto.setName("ROLE_Member");
//		userEventDto.setRoleEventDto(roleEventDto);
//		return userEventDto;
//	}
//	
//	public MemberEvent createMemberEvent() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(37);
//		Event event = new Event();
//		event.setId(8);
//		memberEvent.setEvent(event);
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("ROLE_Member");
//		memberEvent.setRoleEvent(roleEvent);
//		User user = new User();
//		user.setId(15);
//		user.setStudentId("HE141278");
//		memberEvent.setUser(user);
//		memberEvent.setPaidBeforeClosing(false);
//		memberEvent.setPaymentValue(0);
//		return memberEvent;
//	}
//	
//	public MemberEvent createMemberEventUpdatedRegisterStatus() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(37);
//		Event event = new Event();
//		event.setId(8);
//		memberEvent.setEvent(event);
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("ROLE_Member");
//		memberEvent.setRoleEvent(roleEvent);
//		User user = new User();
//		user.setId(100);
//		user.setStudentId("HA140855");
//		memberEvent.setUser(user);
//		memberEvent.setPaidBeforeClosing(false);
//		memberEvent.setPaymentValue(0);
//		memberEvent.setRegisterStatus(true);
//		return memberEvent;
//	}
//	
//	public MemberEvent createMemberEventUpdatedPaymentStatus() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(37);
//		Event event = new Event();
//		event.setId(8);
//		memberEvent.setEvent(event);
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("ROLE_Member");
//		memberEvent.setRoleEvent(roleEvent);
//		User user = new User();
//		user.setId(15);
//		user.setStudentId("HE141278");
//		memberEvent.setUser(user);
//		memberEvent.setPaidBeforeClosing(true);
//		memberEvent.setPaymentValue(50000);
//		return memberEvent;
//	}
//	
//	public MemberEventDto createMemberEventDto() {
//		MemberEventDto memberEventDto = new MemberEventDto();
//		memberEventDto.setId(37);
//		RoleEventDto roleEventDto = new RoleEventDto();
//		roleEventDto.setId(1);
//		roleEventDto.setName("ROLE_Member");
//		memberEventDto.setRoleEventDto(roleEventDto);
//		memberEventDto.setUserStudentId("HE141278");
//		memberEventDto.setPaymentValue(0);
//		return memberEventDto;
//	}
//	
//	public MemberEventDto createMemberEventDtoUpdatedRole() {
//		MemberEventDto memberEventDto = new MemberEventDto();
//		memberEventDto.setId(37);
//		RoleEventDto roleEventDto = new RoleEventDto();
//		roleEventDto.setId(2);
//		roleEventDto.setName("ROLE_MemberCommunication");
//		memberEventDto.setRoleEventDto(roleEventDto);
//		memberEventDto.setUserStudentId("HE141278");
//		memberEventDto.setPaymentValue(0);
//		return memberEventDto;
//	}
//	
//	public List<MemberEventDto> createListMemberCancelToJoinEvent() {
//		List<MemberEventDto> membersEvent = new ArrayList<MemberEventDto>();
//		MemberEventDto memberEvent = new MemberEventDto();
//		memberEvent.setId(37);
//		RoleEventDto roleEventDto = new RoleEventDto();
//		roleEventDto.setId(1);
//		roleEventDto.setName("ROLE_Member");
//		memberEvent.setRoleEventDto(roleEventDto);
//		memberEvent.setUserStudentId("HE141278");
//		memberEvent.setRegisterStatus(false);
//		memberEvent.setPaymentValue(0);
//		membersEvent.add(memberEvent);
//		return membersEvent;
//	}
//	
//	public List<EventPaymentStatusReportDto> createListEventPaymentStatusReportDto(){
//		List<EventPaymentStatusReportDto> eventPaymentStatusReportsDto = new ArrayList<EventPaymentStatusReportDto>();
//		EventPaymentStatusReportDto eventPaymentStatusReportDto = new EventPaymentStatusReportDto();
//		eventPaymentStatusReportDto.setId(1);
//		eventPaymentStatusReportDto.setEventId(8);
//		eventPaymentStatusReportDto.setUserStudentId("HE141278");
//		eventPaymentStatusReportsDto.add(eventPaymentStatusReportDto);
//		return eventPaymentStatusReportsDto;
//	}
//	
//	public RoleEventDto createRoleEventDto() {
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName(Constant.ROLE_EVENT_MEMBER);
//		RoleEventDto roleEventDto = new RoleEventDto();
//		roleEventDto.setId(roleEvent.getId());
//		roleEventDto.setName(roleEvent.getName());
//		Utils.convertNameOfEventRole(roleEvent, roleEventDto);
//		return roleEventDto;
//	}
//	
//	public MemberNotJoinEventDto createMemberNotJoinEventDto() {
//		Role role = new Role();
//		role.setId(1);
//		User user = new User();
//		user.setRole(role);
//		user.setId(100);
//		user.setName("Dam Van Toan 100");
//		user.setEmail("toandvha140855@fpt.edu.vn");
//		MemberNotJoinEventDto memberNotJoinEventDto = new MemberNotJoinEventDto();
//		memberNotJoinEventDto.setUserId(user.getId());
//		memberNotJoinEventDto.setUserName(user.getName());
//		memberNotJoinEventDto.setUserStudentId(user.getStudentId());
//		memberNotJoinEventDto.setUserMail(user.getEmail());
//		memberNotJoinEventDto.setRegisteredStatus(false);
//		memberNotJoinEventDto.setRoleInClub(Utils.convertRoleFromDbToExcel(user.getRole()));
//		memberNotJoinEventDto.setRoleEventDto(createRoleEventDto());
//		return memberNotJoinEventDto;
//	}
//	
//	@Test
//	public void updateListMemberEventRoleSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEventDtoUpdatedRole()));
//		
//		when(memberEventService.updateListMemberEventRole(anyList())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(put("/api/event/headculture/updatelistmembereventrole")
//				.content(asJsonString(Arrays.asList(createMemberEventDtoUpdatedRole())))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getAllMemberCancelToJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(createListMemberCancelToJoinEvent());
//		
//		when(memberEventService.getAllMemberCancelJoinEvent(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/headculture/getallmembercanceljoinevent/{eventId}","8")
//				.param("pageNo", "0")
//				.param("pageSize", "1000")
//				.param("sortBy", "id"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void updateMemberEventPaymentStatusSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEventUpdatedPaymentStatus()));
//		
//		when(memberEventService.updateMemberEventPaymentStatus(anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(put("/api/event/treasurer/updatemembereventpaymentstatus/{memberEventId}","1")
//				.content(asJsonString(Arrays.asList(createMemberEventUpdatedPaymentStatus())))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getReportPaymentStatusSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(createListEventPaymentStatusReportDto());
//		
//		when(memberEventService.getReportPaymentStatusByEventId(anyInt(), anyInt(), anyInt(), anyString())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/treasurer/getreportpaymentstatus/{eventId}","8")
//				.param("pageNo", "0")
//				.param("pageSize", "1000")
//				.param("sortBy", "id"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getAllMemberJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEventDto()));
//		
//		when(memberEventService.getAllMemberJoinEventByRoleEventId(anyInt(), anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/headculture/getmemberjoinevent/{eventId}","8")
//				.param("filterIndex", "0"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getListMemberEventToUpdateRoleSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEventDto()));
//		
//		when(memberEventService.getListMemberEventToUpdateRole(anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/headculture/getlistmembereventtoupdaterole/{eventId}","8"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getAllEventRoleSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createRoleEventDto()));
//		
//		when(memberEventService.getAllEventRole()).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/headculture/getallroleevent"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getListMemberNotJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberNotJoinEventDto()));
//		
//		when(memberEventService.getListMemberNotJoinEvent(anyInt(), anyInt(), anyInt())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/headculture/getlistmembernotjoin/{eventId}","8")
//				.param("pageNo", "0")
//				.param("pageSize", "1000"))
//		.andExpect(status().isOk())
//		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void addListMemberJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEventUpdatedRegisterStatus()));
//		
//		when(memberEventService.addListMemberJoinEvent(anyInt(), anyList())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(post("/api/event/headculture/addlistmemberjoin/{eventId}","8")
//				.content(asJsonString(Arrays.asList(createMemberNotJoinEventDto())))
//				.contentType(MediaType.APPLICATION_JSON))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//
//	@Test
//	public void registerToJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createUserEventDto()));
//		
//		when(memberEventService.registerToJoinEvent(anyInt(), anyString())).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(post("/api/event/registertojoinevent/{eventId}/{studentId}","8","HE140860"))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void registerToJoinOrganizingCommitteeSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createUserEventDto()));
//		
//		when(memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 2)).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(post("/api/event/registertojoinorganizingcommittee/{eventId}/{studentId}/{roleEventId}","8","HE140860","2"))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void cancelToJoinEventSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createMemberEvent()));
//		
//		when(memberEventService.cancelToJoinEvent(8, "HE141278")).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(put("/api/event/canceltojoinevent/{eventId}/{studentId}","8","HE141278"))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	@Test
//	public void getAllEventByStudentIdSuccess() throws Exception {
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(Arrays.asList(createUserEventDto()));
//		
//		when(memberEventService.getAllEventByStudentId("HE140860")).thenReturn(responseMessage);
//		
//		this.mockMvc
//		.perform(get("/api/event/getalleventbystudentid/{studentId}","HE140860"))
//		.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		.andExpect(jsonPath("$.data.size()").value(1));
//	}
//	
//	public static String asJsonString(final Object obj) {
//	    try {
//	      return new ObjectMapper().writeValueAsString(obj);
//	    } catch (Exception e) {
//	      throw new RuntimeException(e);
//	    }
//	  }
//
//}
