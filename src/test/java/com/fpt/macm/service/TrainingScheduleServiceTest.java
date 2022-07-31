package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class TrainingScheduleServiceTest {

	@InjectMocks
	TrainingScheduleService trainingScheduleService = new TrainingScheduleServiceImpl();
	
	@Mock
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Mock
	CommonScheduleRepository commonScheduleRepository;
	
	@Mock
	SemesterRepository semesterRepository;
	
	@Mock
	CommonScheduleService commonScheduleService;
	
	@Mock
	SemesterService semesterService;
	
	@Mock
	NotificationService notificationService;
	
	private TrainingSchedule trainingSchedule() {
		TrainingSchedule trainingSchedule = new TrainingSchedule();
		trainingSchedule.setId(1);
		trainingSchedule.setDate(LocalDate.now());
		trainingSchedule.setStartTime(LocalTime.of(18, 0));
		trainingSchedule.setFinishTime(LocalTime.of(20, 0));
		return trainingSchedule;
	}
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}
	
	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setId(1);
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setDate(LocalDate.of(2022, 8, 29));
		commonSchedule.setStartTime(LocalTime.of(8, 0));
		commonSchedule.setFinishTime(LocalTime.of(9, 0));
		commonSchedule.setType(1);
		return commonSchedule;
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseStartDateAfterEndDate() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.now().getDayOfWeek().toString());
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("30/08/2022", "29/08/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseStartTimeAfterFinishTime() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.now().getDayOfWeek().toString());
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/08/2022", "30/08/2022", dayOfWeeks, "16:00", "08:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseDateInPast() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.now().getDayOfWeek().toString());
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/01/2022", "30/01/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseDateNotInSemester() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.now().getDayOfWeek().toString());
		
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/12/2022", "30/12/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseCommonScheduleNull() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.of(2022, 8, 29).getDayOfWeek().toString());
		dayOfWeeks.add(LocalDate.of(2022, 8, 30).getDayOfWeek().toString());
		
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(null);
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/08/2022", "30/08/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 2);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseCommonScheduleNotNull() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add(LocalDate.of(2022, 8, 29).getDayOfWeek().toString());
		dayOfWeeks.add(LocalDate.of(2022, 8, 30).getDayOfWeek().toString());
		
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/08/2022", "30/08/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 2);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseListPreviewEmpty() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add("29");
		dayOfWeeks.add("30");
		
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/08/2022", "30/08/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTrainingScheduleCaseException() {
		List<String> dayOfWeeks = new ArrayList<String>();
		dayOfWeeks.add("29");
		dayOfWeeks.add("30");
		
		when(semesterService.getCurrentSemester()).thenReturn(null);
		
		ResponseMessage responseMessage = trainingScheduleService.createPreviewTrainingSchedule("29/08/2022", "30/08/2022", dayOfWeeks, "08:00", "16:00");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTrainingSessionCaseStartTimeAfterFinishTime() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setStartTime(LocalTime.of(16, 0));
		trainingSchedule.setFinishTime(LocalTime.of(4, 0));
		
		ResponseMessage responseMessage = trainingScheduleService.createTrainingSession(trainingSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTrainingSessionCaseScheduleInPast() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().minusMonths(1));
		
		ResponseMessage responseMessage = trainingScheduleService.createTrainingSession(trainingSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTrainingSessionCaseCommonScheduleNull() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().plusDays(1));
		
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(null);
		
		ResponseMessage responseMessage = trainingScheduleService.createTrainingSession(trainingSchedule);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void createTrainingSessionCaseCommonScheduleNotNull() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().plusDays(1));
		
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = trainingScheduleService.createTrainingSession(trainingSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTrainingSessionCaseException() {
		ResponseMessage responseMessage = trainingScheduleService.createTrainingSession(null);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListTrainingScheduleCaseSuccess() {
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage responseMessage = trainingScheduleService.getListTrainingSchedule();
		assertEquals(responseMessage.getData().size(), 1);
	}

}
