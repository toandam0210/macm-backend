package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.dto.MemberNotJoinEventDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.UserEventDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventPaymentStatusReport;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@ExtendWith(MockitoExtension.class)
public class MemberEventServiceTest {

	@InjectMocks
	MemberEventService memberEventService = new MemberEventServiceImpl();

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	EventRepository eventRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleEventRepository roleEventRepository;
	
	@Mock
	private ClubFundRepository clubFundRepository;
	
	@Mock
	private EventPaymentStatusReportRepository eventPaymentStatusReportRepository;

	public Optional<Event> createEvent() {
		Event event = new Event();
		event.setId(8);
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setCreatedBy("LinhLHN");
		event.setCreatedOn(LocalDateTime.now());
		event.setDescription("Gẹt gô");
		event.setMaxQuantityComitee(12);
		event.setName("Đi Đà Lạt");
		event.setSemester("Summer2022");
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 10, 30, 0, 0);
		event.setRegistrationMemberDeadline(dateTimeRegistrationDeadline);
		event.setRegistrationOrganizingCommitteeDeadline(dateTimeRegistrationDeadline);
		Optional<Event> eventOp = Optional.of(event);
		return eventOp;
	}
	
	public Optional<Event> createEventHasClosedWithPaidMore() {
		Event event = new Event();
		event.setId(8);
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(100000);
		event.setAmountPerRegisterEstimated(50000);
		event.setCreatedBy("LinhLHN");
		event.setCreatedOn(LocalDateTime.now());
		event.setDescription("Gẹt gô");
		event.setMaxQuantityComitee(12);
		event.setName("Đi Đà Lạt");
		event.setSemester("Summer2022");
		event.setTotalAmountActual(200000);
		event.setTotalAmountEstimated(100000);
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 10, 30, 0, 0);
		event.setRegistrationMemberDeadline(dateTimeRegistrationDeadline);
		event.setRegistrationOrganizingCommitteeDeadline(dateTimeRegistrationDeadline);
		Optional<Event> eventOp = Optional.of(event);
		return eventOp;
	}
	
	public Optional<Event> createEventHasClosedWithoutPaidMore() {
		Event event = new Event();
		event.setId(8);
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(50000);
		event.setAmountPerRegisterEstimated(50000);
		event.setCreatedBy("LinhLHN");
		event.setCreatedOn(LocalDateTime.now());
		event.setDescription("Gẹt gô");
		event.setMaxQuantityComitee(12);
		event.setName("Đi Đà Lạt");
		event.setSemester("Summer2022");
		event.setTotalAmountActual(100000);
		event.setTotalAmountEstimated(100000);
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 10, 30, 0, 0);
		event.setRegistrationMemberDeadline(dateTimeRegistrationDeadline);
		event.setRegistrationOrganizingCommitteeDeadline(dateTimeRegistrationDeadline);
		Optional<Event> eventOp = Optional.of(event);
		return eventOp;
	}

	public Optional<Event> createEventHasDone() {
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 7, 1, 0, 0);
		Event event = new Event();
		event.setId(100);
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

	private Optional<User> createUser() {
		User user = new User();
		user.setStudentId("HE140860");
		user.setId(15);
		user.setName("dam van toan 06");
		user.setActive(true);
		Role role = new Role();
		role.setId(8);
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		Optional<User> userOp = Optional.of(user);
		return userOp;
	}
	
	private Optional<MemberEvent> createMemberEvent() {
		Event event = createEvent().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasPaidBeforeClosingEvent() {
		Event event = createEvent().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(true);
		memberEvent.setPaymentValue(50000);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasPaidBeforeClosingEventAndPaidMore() {
		Event event = createEventHasClosedWithPaidMore().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(true);
		memberEvent.setPaymentValue(50000);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasNotPaidBeforeClosingEvent() {
		Event event = createEventHasClosedWithPaidMore().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasPaidBeforeClosingEventWithoutPaidMore() {
		Event event = createEventHasClosedWithoutPaidMore().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(true);
		memberEvent.setPaymentValue(50000);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasPaidBeforeClosingEventWithPaidMoreAndPaidEnough() {
		Event event = createEventHasClosedWithPaidMore().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(true);
		memberEvent.setPaymentValue(100000);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private Optional<MemberEvent> createMemberEventHasNotPaidBeforeClosingEventWithPaidMoreAndPaidEnough() {
		Event event = createEventHasClosedWithPaidMore().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(100000);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}
	
	private MemberEventDto createMemberEventDto(){
		MemberEventDto memberEventDto = new MemberEventDto();
		memberEventDto.setId(1);
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(2);
		roleEventDto.setName(Constant.ROLE_EVENT_MEMBER_COMMUNICATION_VN);
		memberEventDto.setRoleEventDto(roleEventDto);
		return memberEventDto;
		
	}

	private Optional<MemberEvent> createMemberEventOrganizingCommittee() {
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		Event event = new Event();
		event.setId(8);
		memberEvent.setEvent(event);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(2);
		roleEvent.setName("ROLE_MemberCommunication");
		memberEvent.setRoleEvent(roleEvent);
		User user = new User();
		user.setId(15);
		memberEvent.setUser(user);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		return memberEventOp;
	}

	private List<MemberEvent> createListMemberEvent() {
		List<MemberEvent> membersEvent = new ArrayList<>();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		Event event = new Event();
		event.setId(8);
		memberEvent.setEvent(event);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		memberEvent.setRegisterStatus(false);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		memberEvent.setRoleEvent(roleEvent);
		User user = new User();
		user.setId(1);
		memberEvent.setUser(user);
		membersEvent.add(memberEvent);
		return membersEvent;
	}

	private List<MemberEvent> createListMemberHasJoinedEvent() {
		List<MemberEvent> membersEvent = new ArrayList<>();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setEvent(createEvent().get());
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		memberEvent.setRegisterStatus(true);
		memberEvent.setRoleEvent(createRoleEvent().get());
		memberEvent.setUser(createUser().get());
		membersEvent.add(memberEvent);
		return membersEvent;
	}

	private List<MemberEvent> createListMemberHasCanceledToJoinEvent() {
		List<MemberEvent> membersEvent = new ArrayList<>();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		Event event = new Event();
		event.setId(8);
		memberEvent.setEvent(event);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		memberEvent.setRegisterStatus(false);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		memberEvent.setRoleEvent(roleEvent);
		User user = new User();
		user.setId(15);
		memberEvent.setUser(user);
		membersEvent.add(memberEvent);
		return membersEvent;
	}

	private List<MemberEvent> createListOrganizingCommitteeEvent() {
		List<MemberEvent> membersEvent = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			MemberEvent memberEvent = new MemberEvent();
			memberEvent.setId(100);
			memberEvent.setEvent(createEvent().get());
			memberEvent.setPaidBeforeClosing(false);
			memberEvent.setPaymentValue(0);
			memberEvent.setRegisterStatus(true);
			RoleEvent roleEvent = new RoleEvent();
			roleEvent.setId(2);
			roleEvent.setName("ROLE_MemberCommunication");
			memberEvent.setRoleEvent(roleEvent);
			memberEvent.setUser(createUser().get());
			membersEvent.add(memberEvent);
		}
		return membersEvent;
	}

	private Optional<RoleEvent> createRoleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		Optional<RoleEvent> roleEventOp = Optional.of(roleEvent);
		return roleEventOp;
	}

	private Optional<RoleEvent> createRoleEventOrganizingCommittee() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(2);
		roleEvent.setName("ROLE_MemberCommunication");
		Optional<RoleEvent> roleEventOp = Optional.of(roleEvent);
		return roleEventOp;
	}
	
	private Page<MemberEvent> createMemberCancelJoinEvent(){
		Event event = createEvent().get();
		RoleEvent roleEvent = createRoleEvent().get();
		User user = createUser().get();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(false);
		memberEvent.setEvent(event);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUser(user);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		Page<MemberEvent> page = new PageImpl<>(Arrays.asList(memberEvent));
		return page;
	}
	
	private ClubFund createClubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(100000000);
		return clubFund;
	}
	
	private Page<EventPaymentStatusReport> createEventPaymentStatusReport() {
		EventPaymentStatusReport eventPaymentStatusReport = new EventPaymentStatusReport();
		eventPaymentStatusReport.setEvent(createEvent().get());
		eventPaymentStatusReport.setFundBalance(100000);
		eventPaymentStatusReport.setFundChange(50000);
		eventPaymentStatusReport.setId(1);
		eventPaymentStatusReport.setPaymentValue(50000);
		eventPaymentStatusReport.setUser(createUser().get());
		Page<EventPaymentStatusReport> page = new PageImpl<>(Arrays.asList(eventPaymentStatusReport));
		return page;
	}
	
	private MemberNotJoinEventDto createMemberNotJoinEventDto() {
		RoleEvent roleEvent = createRoleEvent().get();
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(roleEvent.getId());
		roleEventDto.setName(roleEvent.getName());
		Utils.convertNameOfEventRole(roleEvent, roleEventDto);
		User user = createUser().get();
		
		MemberNotJoinEventDto memberNotJoinEventDto = new MemberNotJoinEventDto();
		memberNotJoinEventDto.setRegisteredStatus(true);
		memberNotJoinEventDto.setRoleEventDto(roleEventDto);
		memberNotJoinEventDto.setUserId(user.getId());
		memberNotJoinEventDto.setUserMail(user.getEmail());
		memberNotJoinEventDto.setUserName(user.getName());
		memberNotJoinEventDto.setUserStudentId(user.getStudentId());
		memberNotJoinEventDto.setRoleInClub(user.getRole().getName());
		return memberNotJoinEventDto;
	}
	
	@Test
	void updateListMemberEventRoleSuccess() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEvent());
		
		ResponseMessage responseMessage = memberEventService.updateListMemberEventRole(Arrays.asList(createMemberEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getAllMemberCancelJoinEventSuccess() throws Exception {
		when(memberEventRepository.findByEventIdAndRegisterStatus(anyInt(), anyBoolean(),any())).thenReturn(createMemberCancelJoinEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllMemberCancelJoinEvent(8, 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasNotPaidPaymentStatusBeforeClosingEvent() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEvent());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasPaidPaymentStatusBeforeClosingEvent() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasPaidBeforeClosingEvent());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasPaidPaymentStatusBeforeClosingEventAndPaidMore() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasPaidBeforeClosingEventAndPaidMore());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasNotPaidPaymentStatusBeforeClosingEventAndPaidMore() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasNotPaidBeforeClosingEvent());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasPaidPaymentStatusBeforeClosingEventWithoutPaidMore() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasPaidBeforeClosingEventWithoutPaidMore());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasPaidBeforeClosingEventWithPaidMoreAndPainEnough() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasPaidBeforeClosingEventWithPaidMoreAndPaidEnough());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void updateMemberEventHasNotPaidBeforeClosingEventWithPaidMoreAndPainEnough() throws Exception {
		when(memberEventRepository.findById(anyInt())).thenReturn(createMemberEventHasNotPaidBeforeClosingEventWithPaidMoreAndPaidEnough());
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(createClubFund()));
		
		ResponseMessage responseMessage = memberEventService.updateMemberEventPaymentStatus(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getReportPaymentStatusByEventIdSuccess() throws Exception {
		when(eventPaymentStatusReportRepository.findByEventId(anyInt(), any())).thenReturn(createEventPaymentStatusReport());
		
		ResponseMessage responseMessage = memberEventService.getReportPaymentStatusByEventId(8, 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	void getAllMemberJoinEventFilterIndex0() throws Exception {
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberHasJoinedEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(8, 0);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getAllMemberJoinEventFilterIndex1() throws Exception {
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberHasJoinedEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(8, 1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getAllMemberJoinEventFilterIndex2() throws Exception {
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListOrganizingCommitteeEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(8, 2);
		assertEquals(responseMessage.getData().size(), 12);
	}
	
	@Test
	void getAllMemberJoinEventFilterIndexDefault() throws Exception {
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberHasJoinedEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllMemberJoinEventByRoleEventId(8, -1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getAllRoleEventSuccess() throws Exception {
		when(roleEventRepository.findAll()).thenReturn(Arrays.asList(createRoleEvent().get()));
		
		ResponseMessage responseMessage = memberEventService.getAllEventRole();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getListMemberEventToUpdateRoleSuccess() throws Exception {
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListOrganizingCommitteeEvent());
		
		ResponseMessage responseMessage = memberEventService.getListMemberEventToUpdateRole(8);
		assertEquals(responseMessage.getData().size(), 12);
	}
	
	@Test
	void getListMemberNotJoinEventHasCancelToJoinSuccess() throws Exception {
		MemberEvent memberEvent = createMemberEvent().get();
		memberEvent.setRegisterStatus(false);
		Optional<MemberEvent> memberEventOp = Optional.of(memberEvent);
		
		when(userRepository.findAll()).thenReturn(Arrays.asList(createUser().get()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(memberEventOp);
		
		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(8, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getListMemberNotJoinEventHasCancelToJoinFail() throws Exception {
		when(userRepository.findAll()).thenReturn(Arrays.asList(createUser().get()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(createMemberEvent());
		
		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(8, 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	void getListMemberNotJoinEventSuccess() throws Exception {
		Optional<MemberEvent> memberEventOp = Optional.empty();
		
		when(userRepository.findAll()).thenReturn(Arrays.asList(createUser().get()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(memberEventOp);
		
		ResponseMessage responseMessage = memberEventService.getListMemberNotJoinEvent(8, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void addListMemberNotJoinEventHasCancelJoinSuccess() throws Exception {
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(createMemberEvent());
		when(roleEventRepository.findMemberRole()).thenReturn(createRoleEvent());
		
		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(8, Arrays.asList(createMemberNotJoinEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void addListMemberNotJoinEventSuccess() throws Exception {
		MemberNotJoinEventDto memberNotJoinEventDto = createMemberNotJoinEventDto();
		memberNotJoinEventDto.setRegisteredStatus(false);
		
		when(roleEventRepository.findMemberRole()).thenReturn(createRoleEvent());
		when(userRepository.findById(anyInt())).thenReturn(createUser());
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		
		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(8, Arrays.asList(memberNotJoinEventDto));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void addListMemberNotJoinEventFail() throws Exception {
		List<MemberNotJoinEventDto> listToJoin = new ArrayList<MemberNotJoinEventDto>();
		
		ResponseMessage responseMessage = memberEventService.addListMemberJoinEvent(8, listToJoin);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	
	
	
	
	

	@Test
	void registerToJoinEventSuccess() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberEvent());
		when(roleEventRepository.findMemberRole()).thenReturn(createRoleEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	void registerToJoinEventHasAlreadyJoin() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberHasJoinedEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinEventHasCanceledToJoinSuccess() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt()))
				.thenReturn(createListMemberHasCanceledToJoinEvent());
		when(roleEventRepository.findMemberRole()).thenReturn(createRoleEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	void registerToJoinEventHasDone() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEventHasDone());

		ResponseMessage responseMessage = memberEventService.registerToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinOrganizingCommitteeEventHasDone() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEventHasDone());

		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 2);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinOrganizingCommitteeInvalidRoleEvent() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(roleEventRepository.findById(anyInt())).thenReturn(createRoleEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinOrganizingCommitteeHasAlreadyJoin() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(roleEventRepository.findById(anyInt())).thenReturn(createRoleEventOrganizingCommittee());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberHasJoinedEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 2);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinOrganizingCommitteeOfEventHasAlreadyMaxQuantity() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(roleEventRepository.findById(anyInt())).thenReturn(createRoleEventOrganizingCommittee());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt()))
				.thenReturn(createListOrganizingCommitteeEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 2);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void registerToJoinOrganizingCommitteeSuccess() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(roleEventRepository.findById(anyInt())).thenReturn(createRoleEventOrganizingCommittee());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberEvent());

		ResponseMessage responseMessage = memberEventService.registerToJoinOrganizingCommittee(8, "HE140860", 2);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	void cancelToJoinEventHasClosedRegistration() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEventHasDone());

		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	void cancelToJoinEventOrganizingCommittee() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(createMemberEventOrganizingCommittee());
	
		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	void cancelToJoinEventSuccess() throws Exception {
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(createMemberEvent());
	
		ResponseMessage responseMessage = memberEventService.cancelToJoinEvent(8, "HE140860");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	void getAllEventByStudentIdSuccess() throws Exception {
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(createListMemberHasJoinedEvent());
		
		ResponseMessage responseMessage = memberEventService.getAllEventByStudentId("HE140860");
		assertEquals(responseMessage.getData().size(), 1);
	}
}
