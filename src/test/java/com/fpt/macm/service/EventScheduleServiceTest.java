package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class EventScheduleServiceTest {

	@InjectMocks
	EventScheduleService eventScheduleService = new EventScheduleServiceImpl();
	
	@Mock
	EventScheduleRepository eventScheduleRepository;

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	EventRepository eventRepository;

	@Mock
	CommonScheduleRepository commonScheduleRepository;

	@Mock
	TrainingScheduleRepository trainingScheduleRepository;

	@Mock
	CommonScheduleService commonScheduleService;

	@Mock
	TrainingScheduleService trainingScheduleService;

	@Mock
	SemesterService semesterService;

	@Mock
	NotificationService notificationService;
	
	@Mock
	AttendanceStatusRepository attendanceStatusRepository;
	
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
	
	public Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Đi Đà Lạt");
		event.setDescription("Gẹt gô");
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setSemester(semester().getName());
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		event.setRegistrationMemberDeadline(LocalDateTime.now().plusMonths(1));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusMonths(1));
		event.setStatus(true);
		return event;
	}
	
	public EventSchedule eventSchedule() {
		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setId(1);
		eventSchedule.setEvent(event());
		eventSchedule.setDate(LocalDate.now().plusMonths(1));
		eventSchedule.setStartTime(LocalTime.now());
		eventSchedule.setFinishTime(LocalTime.now().plusHours(1));
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
	
	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setId(1);
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setDate(LocalDate.now().plusMonths(1));
		commonSchedule.setType(0);
		commonSchedule.setStartTime(LocalTime.now());
		commonSchedule.setFinishTime(LocalTime.now().plusHours(8));
		return commonSchedule;
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
	public void createPreviewEventScheduleCaseStartDateAfterEndDate() {
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "30/08/2022", "29/08/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewEventScheduleCaseStartTimeAfterFinishTime() {
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "29/08/2022", "29/08/2022", "18:00", "06:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewEventScheduleCaseStartDateInPast() {
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "29/01/2022", "29/01/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewEventScheduleCaseNotInCurrentSemester() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "29/12/2022", "29/12/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewEventScheduleCaseCommonScheduleNull() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "26/08/2022", "28/08/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void createPreviewEventScheduleCaseCommonScheduleNotNull() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "26/08/2022", "28/08/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void createPreviewEventScheduleCaseException() {
		when(semesterService.getCurrentSemester()).thenReturn(null);
		
		ResponseMessage responseMessage = eventScheduleService.createPreviewEventSchedule("Đi Đà Lạt", "26/08/2022", "28/08/2022", "06:00", "18:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListEventScheduleByEventCaseSuccess() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		
		ResponseMessage responseMessage = eventScheduleService.getListEventScheduleByEvent(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListEventScheduleByEventCaseListScheduleEmpty() {
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(new ArrayList<EventSchedule>());
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		
		ResponseMessage responseMessage = eventScheduleService.getListEventScheduleByEvent(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListEventScheduleByEventCaseEventEmpty() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = eventScheduleService.getListEventScheduleByEvent(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListEventScheduleByEventCaseException() {
		when(eventRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventScheduleService.getListEventScheduleByEvent(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventSessionByDateCaseSuccess() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.of(eventSchedule()));
		
		ResponseMessage responseMessage = eventScheduleService.getEventSessionByDate("28/08/2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getEventSessionByDateCaseScheduleEmpty() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = eventScheduleService.getEventSessionByDate("28/08/2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getEventSessionByDateCaseException() {
		when(eventScheduleRepository.findByDate(any())).thenReturn(null);
		
		ResponseMessage responseMessage = eventScheduleService.getEventSessionByDate("28/08/2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseStartDateAfterEndDate() {
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "30/08/2022", "29/08/2022", "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseStartTimeAfterFinishTime() {
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "30/08/2022", "30/08/2022", "16:00", "08:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseDateInPast() {
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "30/01/2022", "30/01/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseNotInSemester() {
		ResponseMessage semesterResponseMessage = new ResponseMessage();
		semesterResponseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponseMessage);
		
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "28/12/2022", "31/12/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseCommonScheduleNull() {
		ResponseMessage semesterResponseMessage = new ResponseMessage();
		semesterResponseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponseMessage);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "28/08/2022", "30/08/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseCommonScheduleExsitedTrue() {
		CommonSchedule commonSchedule = commonSchedule();
		commonSchedule.setTitle(event().getName());
		
		ResponseMessage semesterResponseMessage = new ResponseMessage();
		semesterResponseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponseMessage);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule);
		
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "28/08/2022", "30/08/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseCommonScheduleExsitedFalse() {
		ResponseMessage semesterResponseMessage = new ResponseMessage();
		semesterResponseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponseMessage);
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "28/08/2022", "30/08/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void updatePreviewEventScheduleCaseException() {
		ResponseMessage semesterResponseMessage = new ResponseMessage();
		semesterResponseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponseMessage);
		when(eventRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventScheduleService.updatePreviewEventSchedule(1, "28/08/2022", "30/08/2022", "08:00", "09:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	
	@Test
	public void updateEventScheduleCaseScheduleDtoNotExist() {
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, Arrays.asList(scheduleDto()), false);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateEventScheduleCaseScheduleDtoExistAndOverwrite() {
		ScheduleDto scheduleDto = scheduleDto();
		scheduleDto.setExisted(true);
		scheduleDto.setTitle("Trùng với Lịch tập");
		
		when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event()));
		when(eventScheduleRepository.findByEventId(anyInt())).thenReturn(Arrays.asList(eventSchedule()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(attendanceStatus()));
		
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, Arrays.asList(scheduleDto), true);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateEventScheduleCaseScheduleDtoExistAndNotOverwrite() {
		ScheduleDto scheduleDto = scheduleDto();
		scheduleDto.setExisted(true);
		scheduleDto.setTitle("Trùng với Lịch tập");
		
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, Arrays.asList(scheduleDto), false);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateEventScheduleCaseScheduleDtoExistAndOverwriteAndInterupt() {
		ScheduleDto scheduleDto = scheduleDto();
		scheduleDto.setExisted(true);
		scheduleDto.setTitle("Trùng với ABC");
		
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, Arrays.asList(scheduleDto), true);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateEventScheduleCaseListScheduleEmpty() {
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, new ArrayList<ScheduleDto>(), true);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateEventScheduleCaseException() {
		when(eventRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = eventScheduleService.updateEventSchedule(1, Arrays.asList(scheduleDto()), true);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
