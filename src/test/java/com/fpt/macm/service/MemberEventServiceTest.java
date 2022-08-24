//package com.fpt.macm.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.Sort;
//
//import com.fpt.macm.constant.Constant;
//import com.fpt.macm.model.dto.MemberEventDto;
//import com.fpt.macm.model.dto.EventRoleDto;
//import com.fpt.macm.model.entity.AttendanceEvent;
//import com.fpt.macm.model.entity.ClubFund;
//import com.fpt.macm.model.entity.Event;
//import com.fpt.macm.model.entity.EventPaymentStatusReport;
//import com.fpt.macm.model.entity.EventRole;
//import com.fpt.macm.model.entity.MemberEvent;
//import com.fpt.macm.model.entity.Role;
//import com.fpt.macm.model.entity.RoleEvent;
//import com.fpt.macm.model.entity.User;
//import com.fpt.macm.model.response.ResponseMessage;
//import com.fpt.macm.repository.AttendanceEventRepository;
//import com.fpt.macm.repository.ClubFundRepository;
//import com.fpt.macm.repository.EventPaymentStatusReportRepository;
//import com.fpt.macm.repository.EventRepository;
//import com.fpt.macm.repository.EventRoleRepository;
//import com.fpt.macm.repository.MemberEventRepository;
//import com.fpt.macm.repository.RoleEventRepository;
//import com.fpt.macm.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class MemberEventServiceTest {
//
//	@InjectMocks
//	MemberEventService memberEventService = new MemberEventServiceImpl();
//
//	@Mock
//	MemberEventRepository memberEventRepository;
//
//	@Mock
//	ClubFundRepository clubFundRepository;
//
//	@Mock
//	EventPaymentStatusReportRepository eventPaymentStatusReportRepository;
//
//	@Mock
//	EventRepository eventRepository;
//
//	@Mock
//	RoleEventRepository roleEventRepository;
//
//	@Mock
//	UserRepository userRepository;
//
//	@Mock
//	AttendanceEventRepository attendanceEventRepository;
//
//	@Mock
//	EventRoleRepository eventRoleRepository;
//
//	@Mock
//	ClubFundService clubFundService;
//
//	private User user() {
//		User user = new User();
//		user.setId(1);
//		user.setStudentId("HE140000");
//		user.setName("Nguyen Van A");
//		user.setActive(true);
//		Role role = new Role();
//		role.setId(8);
//		user.setRole(role);
//		return user;
//	}
//
//	public Event event() {
//		Event event = new Event();
//		event.setId(1);
//		event.setName("Sự kiện 1");
//		event.setDescription("ABC");
//		event.setAmountFromClub(0);
//		event.setAmountPerRegisterActual(0);
//		event.setAmountPerRegisterEstimated(50000);
//		event.setSemester("Summer2022");
//		event.setTotalAmountActual(0);
//		event.setTotalAmountEstimated(100000);
//		event.setRegistrationMemberDeadline(LocalDateTime.now().plusMonths(1));
//		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusMonths(1));
//		event.setStatus(true);
//		return event;
//	}
//
//	private MemberEvent memberEvent() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(1);
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
//		memberEvent.setEvent(event());
//		memberEvent.setRoleEvent(roleEvent());
//		memberEvent.setUser(user());
//		memberEvent.setPaidBeforeClosing(false);
//		memberEvent.setPaymentValue(0);
//		return memberEvent;
//	}
//
//	private RoleEvent roleEvent() {
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("Thành viên tham gia");
//		return roleEvent;
//	}
//
//	private MemberEventDto memberEventDto() {
//		MemberEventDto memberEventDto = new MemberEventDto();
//		memberEventDto.setId(memberEvent().getId());
//		memberEventDto.setRoleEventDto(roleEventDto());
//		return memberEventDto;
//	}
//
//	private EventRoleDto roleEventDto() {
//		EventRoleDto roleEventDto = new EventRoleDto();
//		roleEventDto.setId(roleEvent().getId());
//		roleEventDto.setName(roleEvent().getName());
//		roleEventDto.setMaxQuantity(eventRole().getQuantity());
//		roleEventDto.setAvailableQuantity(10);
//		return roleEventDto;
//	}
//
//	private EventRole eventRole() {
//		EventRole eventRole = new EventRole();
//		eventRole.setId(1);
//		eventRole.setEvent(event());
//		eventRole.setQuantity(10);
//		eventRole.setRoleEvent(roleEvent());
//		return eventRole;
//	}
//
//	private ClubFund clubFund() {
//		ClubFund clubFund = new ClubFund();
//		clubFund.setId(1);
//		clubFund.setFundAmount(100000000);
//		return clubFund;
//	}
//
//	private EventPaymentStatusReport eventPaymentStatusReport() {
//		EventPaymentStatusReport eventPaymentStatusReport = new EventPaymentStatusReport();
//		eventPaymentStatusReport.setId(1);
//		eventPaymentStatusReport.setEvent(event());
//		eventPaymentStatusReport.setUser(user());
//		eventPaymentStatusReport.setPaymentValue(50000);
//		eventPaymentStatusReport.setFundChange(50000);
//		eventPaymentStatusReport.setFundBalance(1000000);
//		return eventPaymentStatusReport;
//	}
//
//	private AttendanceEvent attendanceEvent() {
//		AttendanceEvent attendanceEvent = new AttendanceEvent();
//		attendanceEvent.setId(1);
//		attendanceEvent.setEvent(event());
//		attendanceEvent.setUser(user());
//		attendanceEvent.setStatus(2);
//		return attendanceEvent;
//	}
//
//	@Test
//	public void updateListMemberEventRoleCaseSuccess() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getRoleEvent().setId(2);
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.updateListMemberEventRole(Arrays.asList(memberEventDto()));
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateListMemberEventRoleCaseNothingChange() {
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.updateListMemberEventRole(Arrays.asList(memberEventDto()));
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void updateListMemberEventRoleCaseException() {
//		when(memberEventRepository.findById(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.updateListMemberEventRole(Arrays.asList(memberEventDto()));
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberCancelJoinEventCaseSuccess() {
//		when(memberEventRepository.findByEventIdAndRegisterStatus(anyInt(), anyString()))
//				.thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberCancelJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllMemberCancelJoinEventCaseListEmpty() {
//		when(memberEventRepository.findByEventIdAndRegisterStatus(anyInt(), anyString()))
//				.thenReturn(new ArrayList<MemberEvent>());
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberCancelJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberCancelJoinEventCaseException() {
//		when(memberEventRepository.findByEventIdAndRegisterStatus(anyInt(), anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberCancelJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualEq0AndNotPaid() {
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent()));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualEq0AndPaid() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setPaymentValue(event().getAmountPerRegisterEstimated());
//		memberEvent.setPaidBeforeClosing(true);
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualNotEq0AndNotPaid() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(100000);
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualNotEq0AndNotPaidEnough() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(200000);
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterEstimated());
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualNotEq0AndPaidEnoughBeforeClosing() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(200000);
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual());
//		memberEvent.setPaidBeforeClosing(true);
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualNotEq0AndPaidEnoughAfterClosing() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(200000);
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual());
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualNotEq0AndPaidOverActual() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(200000);
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual() + 1);
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualEqEstimateAndPaidEnough() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterEstimated());
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual());
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualEqEstimateAndPaidOverActual() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterEstimated());
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual() + 1);
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseAmountPerRegisterActualSmallerThanEstimate() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(memberEvent.getEvent().getAmountPerRegisterEstimated() - 1);
//		memberEvent.setPaymentValue(memberEvent.getEvent().getAmountPerRegisterActual());
//
//		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent));
//		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void updateMemberEventPaymentStatusCaseException() {
//		when(memberEventRepository.findById(anyInt())).thenReturn(null);
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//
//		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(user().getStudentId(),
//				memberEvent().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getReportPaymentStatusByEventIdCaseSuccess() {
//		when(eventPaymentStatusReportRepository.findByEventId(anyInt()))
//				.thenReturn(Arrays.asList(eventPaymentStatusReport()));
//
//		ResponseMessage responseMessage = memberEventService.getReportPaymentStatusByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getReportPaymentStatusByEventIdCaseEmpty() {
//		when(eventPaymentStatusReportRepository.findByEventId(anyInt()))
//				.thenReturn(new ArrayList<EventPaymentStatusReport>());
//
//		ResponseMessage responseMessage = memberEventService.getReportPaymentStatusByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getReportPaymentStatusByEventIdCaseException() {
//		when(eventPaymentStatusReportRepository.findByEventId(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getReportPaymentStatusByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseFilter0() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 0);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseRegisterStatusFalseAndFilter0() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 0);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseFilter1() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 1);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseRegisterStatusFalseAndFilter1() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 1);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseRoleEventIdNotEq1AndFilter1() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getRoleEvent().setId(2);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 1);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseFilter2() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getRoleEvent().setId(2);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 2);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseRegisterStatusFalseAndFilter2() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseRoleEventIdEq1AndFilter1() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getRoleEvent().setId(1);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseFilterDefault() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), -1);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseFilterDefaultAndRegisterStatusFalse() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), -1);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllMemberJoinEventByRoleEventIdCaseException() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(event().getId(), 0);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllRoleByEventIdCaseSuccess() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
//
//		ResponseMessage responseMessage = memberEventService.getAllRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllRoleByEventIdCaseEmpty() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventRole>());
//
//		ResponseMessage responseMessage = memberEventService.getAllRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllRoleByEventIdCaseException() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllSuggestionRoleCaseSuccess() {
//		List<RoleEvent> rolesEvent = new ArrayList<RoleEvent>();
//		rolesEvent.add(roleEvent());
//		rolesEvent.add(roleEvent());
//		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(rolesEvent);
//
//		ResponseMessage responseMessage = memberEventService.getAllSuggestionRole();
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllSuggestionRoleCaseEmpty() {
//		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(new ArrayList<RoleEvent>());
//
//		ResponseMessage responseMessage = memberEventService.getAllSuggestionRole();
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllSuggestionRoleCaseException() {
//		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllSuggestionRole();
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseSuccess() {
//		EventRole eventRole = eventRole();
//		eventRole.getRoleEvent().setId(2);
//
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole));
//		when(memberEventRepository.findOrganizingCommitteeByEventId(anyInt(), anyInt()))
//				.thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseOrganizingCommitteeEmpty() {
//		EventRole eventRole = eventRole();
//		eventRole.getRoleEvent().setId(2);
//
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole));
//		when(memberEventRepository.findOrganizingCommitteeByEventId(anyInt(), anyInt()))
//				.thenReturn(new ArrayList<MemberEvent>());
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseQuantityEq0() {
//		EventRole eventRole = eventRole();
//		eventRole.getRoleEvent().setId(2);
//		eventRole.setQuantity(0);
//
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole));
//		when(memberEventRepository.findOrganizingCommitteeByEventId(anyInt(), anyInt()))
//				.thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseRoleEventIdEq1() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseEmpty() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventRole>());
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllOrganizingCommitteeRoleByEventIdCaseException() {
//		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllOrganizingCommitteeRoleByEventId(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberEventToUpdateRoleCaseSuccess() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getListMemberEventToUpdateRoleCaseRegisterStatusFalse() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberEventToUpdateRoleCaseUserNotActive() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getUser().setActive(false);
//
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberEventToUpdateRoleCaseEmpty() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(new ArrayList<MemberEvent>());
//
//		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberEventToUpdateRoleCaseException() {
//		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberNotJoinEventCaseSuccess() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getListMemberNotJoinEventCaseRegisterStatusTrue() {
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getListMemberNotJoinEventCaseEmpty() {
//		List<User> users = new ArrayList<User>();
//		users.add(user());
//		users.add(user());
//		when(userRepository.findAll()).thenReturn(users);
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
//
//		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getListMemberNotJoinEventCaseException() {
//		when(userRepository.findAll()).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void addListMemberJoinEventCaseSuccess() {
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
//
//		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(event().getId(),
//				Arrays.asList(memberEventDto()));
//		;
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void addListMemberJoinEventCaseRegisterStatusTrue() {
//		MemberEventDto memberNotJoinEventDto = memberEventDto();
//		memberNotJoinEventDto.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(event().getId(),
//				Arrays.asList(memberNotJoinEventDto));
//		;
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void addListMemberJoinEventCaseEmpty() {
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//
//		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(event().getId(),
//				new ArrayList<MemberEventDto>());
//		;
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void addListMemberJoinEventCaseException() {
//		when(eventRepository.findById(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(event().getId(),
//				new ArrayList<MemberEventDto>());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinEventCaseSuccess() {
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(event().getId(),
//				user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void registerToJoinEventCaseAlreadyRegister() {
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(event().getId(),
//				user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinEventCaseHaveCanceled() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(event().getId(),
//				user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void registerToJoinEventCaseOverDeadline() {
//		Event event = event();
//		event.setRegistrationMemberDeadline(LocalDateTime.now().minusDays(1));
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(event().getId(),
//				user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinEventCaseException() {
//		when(eventRepository.findById(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(event().getId(),
//				user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseSuccess() {
//		RoleEvent roleEvent = roleEvent();
//		roleEvent.setId(2);
//
//		EventRole eventRole = eventRole();
//		eventRole.setRoleEvent(roleEvent);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
//		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseAfterDeadline() {
//		Event event = event();
//		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().minusDays(1));
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseInvalidRole() {
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
//		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole()));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseAlreadyJoin() {
//		RoleEvent roleEvent = roleEvent();
//		roleEvent.setId(2);
//
//		EventRole eventRole = eventRole();
//		eventRole.setRoleEvent(roleEvent);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
//		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseAlreadyCancel() {
//		RoleEvent roleEvent = roleEvent();
//		roleEvent.setId(2);
//
//		EventRole eventRole = eventRole();
//		eventRole.setRoleEvent(roleEvent);
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
//		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
//				.thenReturn(Optional.of(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseMaxQuantity() {
//		RoleEvent roleEvent = roleEvent();
//		roleEvent.setId(2);
//
//		EventRole eventRole = eventRole();
//		eventRole.setRoleEvent(roleEvent);
//		eventRole.setQuantity(0);
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
//		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
//		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole));
//		when(memberEventRepository.findOrganizingCommitteeByEventId(anyInt(), anyInt()))
//				.thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void registerToJoinOrganizingCommitteeCaseException() {
//		when(eventRepository.findById(anyInt())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(event().getId(),
//				user().getStudentId(), 2);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
////	@Test
////	public void cancelToJoinEventCaseSuccess() {
////		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
////		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
////		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
////				.thenReturn(Optional.of(memberEvent()));
////		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt()))
////				.thenReturn(Optional.of(attendanceEvent()));
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 1);
////	}
////
////	@Test
////	public void cancelToJoinEventCaseAttendanceEventEmpty() {
////		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
////		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
////		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
////				.thenReturn(Optional.of(memberEvent()));
////		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.empty());
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 1);
////	}
////
////	@Test
////	public void cancelToJoinEventCaseRoleOrganizingCommittee() {
////		MemberEvent memberEvent = memberEvent();
////		memberEvent.getRoleEvent().setId(2);
////
////		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
////		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
////		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
////				.thenReturn(Optional.of(memberEvent));
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 0);
////	}
////
////	@Test
////	public void cancelToJoinEventCaseMemberEventEmpty() {
////		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
////		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
////		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 0);
////	}
////
////	@Test
////	public void cancelToJoinEventCaseAfterDeadline() {
////		Event event = event();
////		event.setRegistrationMemberDeadline(LocalDateTime.now().minusDays(1));
////
////		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 0);
////	}
////
////	@Test
////	public void cancelToJoinEventCaseException() {
////		when(eventRepository.findById(anyInt())).thenReturn(null);
////
////		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(event().getId(), user().getStudentId());
////		assertEquals(responseMessage.getData().size(), 0);
////	}
//
//	@Test
//	public void getAllEventByStudentIdCaseSuccess() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = memberEventService.getAllEventByStudentId(user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllEventByStudentIdCaseRegisterStatusFalse() {
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = memberEventService.getAllEventByStudentId(user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllEventByStudentIdCaseMemberEventEmpty() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(new ArrayList<MemberEvent>());
//
//		ResponseMessage responseMessage = memberEventService.getAllEventByStudentId(user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllEventByStudentIdCaseException() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = memberEventService.getAllEventByStudentId(user().getStudentId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//}
