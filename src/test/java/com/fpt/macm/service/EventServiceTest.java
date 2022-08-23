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
import org.springframework.data.domain.Sort;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.EventCreateDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventRole;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventRoleRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
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
	EventScheduleRepository eventScheduleRepository;

	@Mock
	CommonScheduleRepository commonScheduleRepository;

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	SemesterRepository semesterRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	NotificationService notificationService;

	@Mock
	RoleEventRepository roleEventRepository;

	@Mock
	TrainingScheduleService trainingScheduleService;

	@Mock
	AttendanceStatusRepository attendanceStatusRepository;

	@Mock
	EventRoleRepository eventRoleRepository;

	@Mock
	TrainingScheduleRepository trainingScheduleRepository;

	@Mock
	ClubFundService clubFundService;

	public Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Đi Đà Lạt");
		event.setDescription("Gẹt gô");
		event.setAmountFromClub(10000);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setSemester(semester().getName());
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		event.setRegistrationMemberDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		event.setStatus(true);
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
		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
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

	private ScheduleDto scheduleDto() {
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setTitle("Đi Đà Lạt");
		scheduleDto.setDate(LocalDate.now().plusMonths(1));
		scheduleDto.setStartTime(LocalTime.now());
		scheduleDto.setFinishTime(LocalTime.now().plusHours(8));
		scheduleDto.setExisted(false);
		return scheduleDto;
	}

	private EventRole eventRole() {
		EventRole eventRole = new EventRole();
		eventRole.setId(1);
		eventRole.setEvent(event());
		eventRole.setRoleEvent(roleEvent());
		eventRole.setQuantity(10);
		return eventRole;
	}

	private RoleEventDto roleEventDto() {
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(roleEvent().getId());
		roleEventDto.setName(roleEvent().getName());
		roleEventDto.setMaxQuantity(eventRole().getQuantity());
		roleEventDto.setAvailableQuantity(10);
		return roleEventDto;
	}

	private EventCreateDto eventCreateDto() {
		EventCreateDto eventCreateDto = new EventCreateDto();
		eventCreateDto.setEvent(event());
		eventCreateDto.setListPreview(Arrays.asList(scheduleDto()));
		eventCreateDto.setRolesEventDto(Arrays.asList(roleEventDto()));
		return eventCreateDto;
	}

	private TrainingSchedule trainingSchedule() {
		TrainingSchedule trainingSchedule = new TrainingSchedule();
		trainingSchedule.setId(1);
		trainingSchedule.setDate(LocalDate.now());
		trainingSchedule.setStartTime(LocalTime.now().minusHours(1));
		trainingSchedule.setFinishTime(LocalTime.now().plusHours(1));
		return trainingSchedule;
	}

	private AttendanceStatus attendanceStatus() {
		AttendanceStatus attendanceStatus = new AttendanceStatus();
		attendanceStatus.setId(1);
		attendanceStatus.setTrainingSchedule(trainingSchedule());
		attendanceStatus.setUser(user());
		attendanceStatus.setStatus(2);
		return attendanceStatus;
	}

	@Test
	public void createEventCaseSuccess() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));

		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(eventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(event()));
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.of(roleEvent()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto(), false);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void createEventCaseRoleEventEmpty() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));

		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(eventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(event()));
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(roleEvent()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto(), false);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void createEventCaseDuplicateWithTrainingScheduleAndNotOverwrite() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.getListPreview().get(0).setExisted(true);
		eventCreateDto.getListPreview().get(0).setTitle("Trùng với Lịch tập");
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void createEventCaseDuplicateWithTrainingScheduleAndOverwrite() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.getListPreview().get(0).setExisted(true);
		eventCreateDto.getListPreview().get(0).setTitle("Trùng với Lịch tập");

		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));

		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(eventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(event()));
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.of(roleEvent()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedules().get(0));
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt()))
				.thenReturn(Arrays.asList(attendanceStatus()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, true);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void createEventCaseNotDuplicateWithTrainingSchedule() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.getListPreview().get(0).setExisted(true);
		eventCreateDto.getListPreview().get(0).setTitle("Trùng với Giải đấu");
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void createEventCaseEventNull() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.setEvent(null);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		
		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void createEventCaseListPreviewNull() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.setListPreview(null);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		
		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void createEventCaseListPreviewEmpty() {
		EventCreateDto eventCreateDto = eventCreateDto();
		eventCreateDto.setListPreview(new ArrayList<ScheduleDto>());
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void createEventCaseException() {
		ResponseMessage responseMessage = eventService.createEvent(user().getStudentId(), eventCreateDto(), false);
		assertEquals(responseMessage.getData().size(), 0);
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
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.of(commonSchedules().get(0)));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.deleteEvent(user().getStudentId(), 1);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void deleteEventCaseEventEmpty() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage responseMessage = eventService.deleteEvent(user().getStudentId(), 1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void deleteEventCaseEventStarted() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}

		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.deleteEvent(user().getStudentId(), 1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void deleteEventCaseEventScheduleEmpty() {
		List<EventSchedule> eventSchedules = new ArrayList<EventSchedule>();

		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.deleteEvent(user().getStudentId(), 1);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void deleteEventCaseException() {
		when(eventRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage responseMessage = eventService.deleteEvent(user().getStudentId(), 1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventByIdCaseSuccess() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));

		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventByIdCaseStatusFalse() {
		Event event = event();
		event.setStatus(false);

		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));

		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventByIdCaseEventEmpty() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventByIdCaseException() {
		when(eventRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage responseMessage = eventService.getEventById(1);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsByDateCaseFinishDateInFuture() {
		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 12, 1),
				LocalDate.of(2022, 1, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsByDateCaseEventScheduleNull() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(null);

		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsByDateCaseNotYet() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.of(eventSchedules().get(0)));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());

		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsByDateCaseEnded() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}

		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.of(eventSchedules().get(0)));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsByDateCaseOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}

		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.of(eventSchedules().get(0)));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsByDateCaseFail() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.of(new EventSchedule()));

		ResponseMessage responseMessage = eventService.getEventsByDate(LocalDate.of(2022, 1, 1),
				LocalDate.of(2022, 12, 1), 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsBySemesterCaseNotYet() {
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());

		ResponseMessage responseMessage = eventService.getEventsBySemester("", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterCaseMonthNotEq0() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.of(2022, 1, 1));
		}

		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsBySemester("", 1, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterCaseDeleted() {
		Event event = event();
		event.setStatus(false);

		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event));

		ResponseMessage responseMessage = eventService.getEventsBySemester("", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsBySemesterCaseNotOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}

		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
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

		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsBySemester("Summer2022", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterCaseStartDateNull() {
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));

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
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 100000, true, true);
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
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 100000, true, false);
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
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 0, false, false);
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
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void updateAfterEventCaseListScheduleNull() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(null);

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void updateAfterEventCaseFail() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());

		ResponseMessage responseMessage = eventService.updateAfterEvent(user().getStudentId(), 1, 100000, false, false);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getEventsBySemesterAndStudentIdCaseJoinEvent() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
				.thenReturn(Optional.of(memberEvent()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());

		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterAndStudentIdCaseCancelJoinEvent() {
		MemberEvent memberEvent = memberEvent();
		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt()))
				.thenReturn(Optional.of(memberEvent));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());

		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("", "HE140855", 0, 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterAndStudentIdCaseNotJoinEvent() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
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
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 1, 0,
				1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterAndStudentIdCaseEventOnGoing() {
		List<EventSchedule> eventSchedules = eventSchedules();
		for (EventSchedule eventSchedule : eventSchedules) {
			eventSchedule.setDate(LocalDate.now());
		}

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);

		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 0, 0,
				1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getEventsBySemesterAndStudentIdCaseFail() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.empty());

		ResponseMessage responseMessage = eventService.getEventsBySemesterAndStudentId("Summer2022", "HE140855", 0, 0,
				1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentId() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentIdCaseAmountPerRegisterActualNotEq0() {
		Event event = event();
		event.setAmountPerRegisterActual(100);

		MemberEvent memberEvent = memberEvent();
		memberEvent.setEvent(event);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules());
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentIdCaseMemberEventEmpty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList());
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentIdCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentIdCaseEventEnd() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(1).setDate(LocalDate.now().minusDays(1));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllEventHasJoinedByStudentIdCaseHappenning() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllEventHasJoinedByStudentId("HE140855", 0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllUpcomingEvent() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().plusDays(1));
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllUpcomingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllUpcomingEventCaseNotFound() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().minusDays(1));
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllUpcomingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllUpcomingEventCaseEventEmpty() {
		when(eventRepository.findAll()).thenReturn(Arrays.asList());
		ResponseMessage responseMessage = eventService.getAllUpcomingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllUpcomingEventCaseException() {
		when(eventRepository.findAll()).thenReturn(null);
		ResponseMessage responseMessage = eventService.getAllUpcomingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllOngoingEvent() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllOngoingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 2);
	}

	@Test
	public void testGetAllOngoingEventCaseNotFound() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().minusDays(1));
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllOngoingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllOngoingEventCaseEventEmpty() {
		when(eventRepository.findAll()).thenReturn(Arrays.asList());
		ResponseMessage responseMessage = eventService.getAllOngoingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllOngoingEventCaseException() {
		when(eventRepository.findAll()).thenReturn(null);
		ResponseMessage responseMessage = eventService.getAllOngoingEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllClosedEvent() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllClosedEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllClosedEventCaseNotFound() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().minusDays(1));
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getAllClosedEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetAllClosedEventCaseEventEmpty() {
		when(eventRepository.findAll()).thenReturn(Arrays.asList());
		ResponseMessage responseMessage = eventService.getAllClosedEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetAllClosedEventCaseException() {
		when(eventRepository.findAll()).thenReturn(null);
		ResponseMessage responseMessage = eventService.getAllClosedEvent(0, 1000);
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetEventsByName() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findByName(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetEventsByNameCaseSortId() {
		List<Event> events = new ArrayList<Event>();
		events.add(event());
		events.add(event());

		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findByName(anyString())).thenReturn(events);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 2);
	}

	@Test
	public void testGetEventsByNameCaseSortName() {
		List<Event> events = new ArrayList<Event>();
		events.add(event());
		events.add(event());

		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findByName(anyString())).thenReturn(events);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "name");
		assertEquals(responseMessage.getData().size(), 2);
	}

	@Test
	public void testGetEventsByNameCaseSortDefault() {
		List<Event> events = new ArrayList<Event>();
		events.add(event());
		events.add(event());

		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now());
		when(eventRepository.findByName(anyString())).thenReturn(events);
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "");
		assertEquals(responseMessage.getData().size(), 2);
	}

	@Test
	public void testGetEventsByNameCaseNotHappenning() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().plusDays(1));
		when(eventRepository.findByName(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "name");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetEventsByNameCaseEnd() {
		List<EventSchedule> eventSchedules = eventSchedules();
		eventSchedules.get(0).setDate(LocalDate.now().minusDays(1));
		when(eventRepository.findByName(anyString())).thenReturn(Arrays.asList(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(eventSchedules);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetEventsByNameCaseStartDateNull() {
		when(eventRepository.findByName(anyString())).thenReturn(Arrays.asList(event()));
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testGetEventsByNameCaseException() {
		when(eventRepository.findByName(anyString())).thenReturn(null);
		ResponseMessage responseMessage = eventService.getEventsByName("Đi Đà Lạt", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void testGetEndDateCaseNull() {
		LocalDate date = eventService.getEndDate(1);
		assertEquals(date, null);
	}

	@Test
	public void testGetStartDateCaseNull() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(null);

		LocalDate date = eventService.getStartDate(1);
		assertEquals(date, null);
	}
	
	@Test
	public void editRoleEventCaseDelete() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
		
		ResponseMessage responseMessage = eventService.editRoleEvent(event().getId(), Arrays.asList());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void editRoleEventCaseUpdateQuantity() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.of(eventRole()));
		
		ResponseMessage responseMessage = eventService.editRoleEvent(event().getId(), Arrays.asList(roleEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void editRoleEventCaseCreateEventRole() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.of(roleEvent()));
		ResponseMessage responseMessage = eventService.editRoleEvent(event().getId(), Arrays.asList(roleEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void editRoleEventCaseCreateRoleEvent() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventRoleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventRole()));
		when(eventRoleRepository.findByRoleEventIdAndEventId(anyInt(), anyInt())).thenReturn(Optional.empty());
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(roleEvent()));
		
		ResponseMessage responseMessage = eventService.editRoleEvent(event().getId(), Arrays.asList(roleEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void editRoleEventCaseException() {
		when(eventRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventService.editRoleEvent(event().getId(), Arrays.asList(roleEventDto()));
		assertEquals(responseMessage.getData().size(), 0);
	}
}
