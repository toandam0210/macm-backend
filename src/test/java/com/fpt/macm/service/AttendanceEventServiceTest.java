//package com.fpt.macm.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import com.fpt.macm.model.dto.AttendanceEventDto;
//import com.fpt.macm.model.entity.AttendanceEvent;
//import com.fpt.macm.model.entity.Event;
//import com.fpt.macm.model.entity.EventSchedule;
//import com.fpt.macm.model.entity.MemberEvent;
//import com.fpt.macm.model.entity.Role;
//import com.fpt.macm.model.entity.RoleEvent;
//import com.fpt.macm.model.entity.Semester;
//import com.fpt.macm.model.entity.User;
//import com.fpt.macm.model.response.ResponseMessage;
//import com.fpt.macm.repository.AttendanceEventRepository;
//import com.fpt.macm.repository.EventRepository;
//import com.fpt.macm.repository.EventScheduleRepository;
//import com.fpt.macm.repository.MemberEventRepository;
//import com.fpt.macm.repository.SemesterRepository;
//import com.fpt.macm.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class AttendanceEventServiceTest {
//
//	@InjectMocks
//	AttendanceEventService attendanceEventService = new AttendanceEventServiceImpl();
//	
//	@Mock
//	AttendanceEventRepository attendanceEventRepository;
//
//	@Mock
//	MemberEventRepository memberEventRepository;
//
//	@Mock
//	EventScheduleRepository eventScheduleRepository;
//
//	@Mock
//	UserRepository userRepository;
//	
//	@Mock
//	SemesterRepository semesterRepository;
//
//	@Mock
//	SemesterService semesterService;
//	
//	@Mock
//	EventRepository eventRepository;
//	
//	private Event event() {
//		Event event = new Event();
//		event.setId(1);
//		event.setName("Đi Đà Lạt");
//		event.setDescription("Gẹt gô");
//		event.setAmountFromClub(0);
//		event.setAmountPerRegisterActual(0);
//		event.setAmountPerRegisterEstimated(50000);
//		event.setSemester(semester().getName());
//		event.setTotalAmountActual(0);
//		event.setTotalAmountEstimated(100000);
//		event.setRegistrationMemberDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
//		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
//		event.setStatus(true);
//		return event;
//	}
//	
//	private Semester semester() {
//		Semester semester = new Semester();
//		semester.setId(1);
//		semester.setName("Summer2022");
//		semester.setStartDate(LocalDate.of(2022, 5, 1));
//		semester.setEndDate(LocalDate.of(2022, 8, 31));
//		return semester;
//	}
//	
//	private User user() {
//		User user = new User();
//		user.setStudentId("HE140860");
//		user.setId(1);
//		user.setName("dam van toan 06");
//		user.setActive(true);
//		Role role = new Role();
//		role.setId(8);
//		user.setRole(role);
//		user.setCreatedOn(LocalDate.now());
//		user.setCreatedBy("toandv");
//		return user;
//	}
//	
//	public MemberEvent memberEvent() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(1);
//		memberEvent.setEvent(event());
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("ROLE_Member");
//		memberEvent.setEventRole(roleEvent);
//		memberEvent.setUser(user());
//		memberEvent.setPaidBeforeClosing(false);
//		memberEvent.setPaymentValue(0);
//		return memberEvent;
//	}
//	
//	public AttendanceEvent attendanceEvent() {
//		AttendanceEvent attendanceEvent = new AttendanceEvent();
//		attendanceEvent.setId(1);
//		attendanceEvent.setUser(user());
//		attendanceEvent.setEvent(event());
//		attendanceEvent.setStatus(2);
//		return attendanceEvent;
//	}
//	
//	public AttendanceEventDto attendanceEventDto() {
//		AttendanceEventDto attendanceEventDto = new AttendanceEventDto();
//		attendanceEventDto.setEventName(attendanceEvent().getEvent().getName());
//		attendanceEventDto.setName(attendanceEvent().getUser().getName());
//		attendanceEventDto.setStudentId(attendanceEvent().getUser().getStudentId());
//		attendanceEventDto.setStatus(attendanceEvent().getStatus());
//		attendanceEventDto.setDate(eventSchedule().getDate());
//		return attendanceEventDto;
//	}
//	
//	public EventSchedule eventSchedule() {
//		EventSchedule eventSchedule = new EventSchedule();
//		eventSchedule.setId(1);
//		eventSchedule.setEvent(event());
//		eventSchedule.setDate(LocalDate.now());
//		eventSchedule.setStartTime(LocalTime.of(18, 0));
//		eventSchedule.setFinishTime(LocalTime.of(20, 0));
//		return eventSchedule;
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseSuccess() {
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.of(attendanceEvent()));
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseListScheduleEmpty() {
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventSchedule>());
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseEventInFuture() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().plusDays(1));
//		
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule));
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseAlreadyAttend() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().minusDays(1));
//		
//		AttendanceEvent attendanceEvent = attendanceEvent();
//		attendanceEvent.setStatus(1);
//		
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.of(attendanceEvent));
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseEventInPast() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().minusDays(1));
//		
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.of(attendanceEvent()));
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseAttendanceEventEmpty() {
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.empty());
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void takeAttendanceByStudentIdCaseException() {
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(null);
//		
//		ResponseMessage responseMessage = attendanceEventService.takeAttendanceByStudentId(user().getStudentId(), 1, event().getId());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void checkAttendanceStatusByEventIdCaseAttend() {
//		AttendanceEvent attendanceEvent = attendanceEvent();
//		attendanceEvent.setStatus(1);
//		
//		when(attendanceEventRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(attendanceEvent));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
//		
//		ResponseMessage responseMessage = attendanceEventService.checkAttendanceStatusByEventId(1);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void checkAttendanceStatusByEventIdCaseAbsent() {
//		AttendanceEvent attendanceEvent = attendanceEvent();
//		attendanceEvent.setStatus(0);
//		
//		when(attendanceEventRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(attendanceEvent));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
//		
//		ResponseMessage responseMessage = attendanceEventService.checkAttendanceStatusByEventId(1);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void checkAttendanceStatusByEventIdCaseScheduleEmpty() {
//		when(attendanceEventRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(attendanceEvent()));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventSchedule>());
//		
//		ResponseMessage responseMessage = attendanceEventService.checkAttendanceStatusByEventId(1);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void checkAttendanceStatusByEventIdCaseException() {
//		when(attendanceEventRepository.findByEventId(anyInt())).thenReturn(null);
//		
//		ResponseMessage responseMessage = attendanceEventService.checkAttendanceStatusByEventId(1);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void getListOldEventToTakeAttendanceBySemesterCaseSuccess() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().minusDays(1));
//		
//		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
//		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule));
//		
//		ResponseMessage responseMessage = attendanceEventService.getListOldEventToTakeAttendanceBySemester(semester().getName());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void getListOldEventToTakeAttendanceBySemesterCaseSemesterNameNull() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().minusDays(1));
//		
//		ResponseMessage semesterResponse = new ResponseMessage();
//		semesterResponse.setData(Arrays.asList(semester()));
//		
//		when(semesterRepository.findByName(anyString())).thenReturn(Optional.empty());
//		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
//		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule));
//		
//		ResponseMessage responseMessage = attendanceEventService.getListOldEventToTakeAttendanceBySemester("");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void getListOldEventToTakeAttendanceBySemesterCaseEventScheduleEmpty() {
//		EventSchedule eventSchedule = eventSchedule();
//		eventSchedule.setDate(LocalDate.now().minusDays(1));
//		
//		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
//		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventSchedule>());
//		
//		ResponseMessage responseMessage = attendanceEventService.getListOldEventToTakeAttendanceBySemester(semester().getName());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void getListOldEventToTakeAttendanceBySemesterCaseEventScheduleStartDateNotBeforeNow() {
//		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
//		when(eventRepository.findBySemesterOrderByIdAsc(anyString())).thenReturn(Arrays.asList(event()));
//		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
//		
//		ResponseMessage responseMessage = attendanceEventService.getListOldEventToTakeAttendanceBySemester(semester().getName());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void getListOldEventToTakeAttendanceBySemesterCaseException() {
//		ResponseMessage responseMessage = attendanceEventService.getListOldEventToTakeAttendanceBySemester(semester().getName());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//}
