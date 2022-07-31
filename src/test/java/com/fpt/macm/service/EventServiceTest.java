package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

	@InjectMocks
	EventService eventService = new EventServiceImpl();
	
	@Mock
	SemesterService semesterService;
	
	@Mock
	EventScheduleService eventScheduleService;
	
	@Mock
	CommonScheduleService commonScheduleService;
	
	@Mock
	EventRepository eventRepository;
	
	@Mock
	ClubFundRepository clubFundRepository;
	
	@Mock
	EventScheduleRepository eventScheduleRepository;
	
	@Mock
	CommonScheduleRepository commonScheduleRepository;
	
	@Mock
	MemberEventRepository memberEventRepository;
	
	@Mock
	SemesterRepository semesterRepository;
	
	@Mock
	UserRepository userRepository;
	
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
	
	public ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(10000000);
		return clubFund;
	}
	
	public List<CommonSchedule> commonSchedules() {
		List<CommonSchedule> commonSchedules = new ArrayList<CommonSchedule>();
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setId(1);
		commonSchedule.setTitle("Đi Đà Lạt");
		commonSchedule.setDate(LocalDate.of(2022, 10, 30));
		commonSchedule.setStartTime(LocalTime.of(0, 0));
		commonSchedule.setFinishTime(LocalTime.of(12, 0));
		commonSchedule.setType(1);
		commonSchedules.add(commonSchedule);
		commonSchedule.setId(2);
		commonSchedule.setDate(LocalDate.of(2022, 10, 31));
		commonSchedules.add(commonSchedule);
		return commonSchedules;
	}
	
	private User user() {
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
		return user;
	}
	
	private MemberEvent memberEvent() {
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event());
		memberEvent.setRoleEvent(roleEvent());
		memberEvent.setUser(user());
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		return memberEvent;
	}
	
	private RoleEvent roleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		return roleEvent;
	}
	
	@Test
	public void createEventCaseSuccess() {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		
		ResponseMessage returnResponseMessage = eventService.createEvent(event());
		assertEquals(returnResponseMessage.getData().size(), 1);
	}
	
	@Test
	public void createEventCaseFail() {
		when(semesterService.getCurrentSemester()).thenReturn(null);
		
		ResponseMessage returnResponseMessage = eventService.createEvent(event());
		assertEquals(returnResponseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateBeforeEventCaseSuccess() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedules().get(0));
		
		ResponseMessage responseMessage = eventService.updateBeforeEvent(1, event());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateBeforeEventCaseEventInPast() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.updateBeforeEvent(1, event());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateBeforeEventCaseEventScheduleEmpty() {
		List<EventSchedule> eventSchedules = new ArrayList<EventSchedule>();
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.updateBeforeEvent(1, event());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void deleteEventCaseSuccess() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedules().get(0));
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		
		ResponseMessage responseMessage = eventService.deleteEvent(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void deleteEventCaseEventStarted() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.deleteEvent(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void deleteEventCaseEventScheduleEmpty() {
		List<EventSchedule> eventSchedules = new ArrayList<EventSchedule>();
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.deleteEvent(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsByNameCaseNotYet() {
		Page<Event> page = new PageImpl<>(Arrays.asList(event()));
		when(eventRepository.findByName(anyString(), any())).thenReturn(page);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByNameCaseEnded() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		Page<Event> page = new PageImpl<>(Arrays.asList(event()));
		when(eventRepository.findByName(anyString(), any())).thenReturn(page);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByNameCaseOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}
		
		Page<Event> page = new PageImpl<>(Arrays.asList(event()));
		when(eventRepository.findByName(anyString(), any())).thenReturn(page);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByNameCaseStartDateNull() {
		List<EventSchedule> eventSchedules = new ArrayList<EventSchedule>();
		
		Page<Event> page = new PageImpl<>(Arrays.asList(event()));
		when(eventRepository.findByName(anyString(), any())).thenReturn(page);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByNameCasePageResponseEmpty() {
		Page<Event> page = Page.empty();
		when(eventRepository.findByName(anyString(), any())).thenReturn(page);
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsByNameCasePageResponseNull() {
		when(eventRepository.findByName(anyString(), any())).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventByIdCaseSuccess() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		
		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventByIdCaseFail() {
		when(eventRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsByDateCaseFinishDateInFuture() {
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 12, 1), LocalDate.of(2022, 1, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsByDateCaseEventScheduleNull() {
		when(eventScheduleService.getEventScheduleByDate(any())).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsByDateCaseNotYet() {
		when(eventScheduleService.getEventScheduleByDate(any())).thenReturn(eventSchedules().get(0));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByDateCaseEnded() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleService.getEventScheduleByDate(any())).thenReturn(eventSchedules.get(0));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByDateCaseOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}
		
		when(eventScheduleService.getEventScheduleByDate(any())).thenReturn(eventSchedules.get(0));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsByDateCaseFail() {
		when(eventScheduleService.getEventScheduleByDate(any())).thenReturn(new EventSchedule());
		
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1), LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsBySemesterCaseNotYet() {
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsBySemester("", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterCaseNotOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}
		
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsBySemester("Summer2022", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterCaseEnded() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsBySemester("Summer2022", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterCaseStartDateNull() {
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		
		ResponseMessage responseMessage = eventService.getEventsBySemester("", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsBySemesterCaseFail() {
		when(semesterRepository.findTop3Semester()).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.getEventsBySemester("", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateAfterEventCaseUseClubFund() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(memberEventRepository.findMemberEventByEventId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 100000, true, true);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateAfterEventCaseNotUseClubFund() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(memberEventRepository.findMemberEventByEventId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 100000, true, false);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateAfterEventCaseNoIncurredWithoutMoney() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 0, false, false);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateAfterEventCaseNoIncurredWithMoney() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateAfterEventCaseListScheduleNull() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateAfterEventCaseFail() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.updateAfterEvent(1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseJoinEvent() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.of(memberEvent()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseCancelJoinEvent() {
		MemberEvent memberEvent = memberEvent();
		memberEvent.setRegisterStatus(false);
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.of(memberEvent));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseNotJoinEvent() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseEventEnded() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 1, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseEventOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(eventRepository.findBySemester(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventsBySemesterAndStudentIdCaseFail() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
