package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AttendanceStatusServiceTest {
	@InjectMocks
	AttendanceStatusService attendanceStatusService = new AttendanceStatusServiceImpl();
	
	@Mock
	TrainingScheduleService trainingScheduleService;

	@Mock
	AttendanceStatusRepository attendanceStatusRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	SemesterRepository semesterRepository;

	@Mock
	TrainingScheduleRepository trainingScheduleRepository;

	@Mock
	SemesterService semesterService;

	private TrainingSchedule trainingSchedule() {
		TrainingSchedule trainingSchedule = new TrainingSchedule();
		trainingSchedule.setId(1);
		trainingSchedule.setDate(LocalDate.now());
		trainingSchedule.setStartTime(LocalTime.of(18, 0, 0));
		trainingSchedule.setFinishTime(LocalTime.of(20, 0, 0));
		return trainingSchedule;
	}
	
	private AttendanceStatus attendanceStatus() {
		AttendanceStatus attendanceStatus = new AttendanceStatus();
		attendanceStatus.setId(1);
		attendanceStatus.setStatus(0);
		attendanceStatus.setTrainingSchedule(trainingSchedule());
		attendanceStatus.setUser(user());
		return attendanceStatus;
	}
	
	private User user() {
		User user = new User();
		user.setStudentId("HE140856");
		user.setId(1);
		user.setName("Dam Van Toan");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 02, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140856@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		return user;
	}
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}
	
	@Test
	public void testTakeAttendanceByStudentIdCaseSuccess() {
		when(trainingScheduleRepository.findById(anyInt())).thenReturn(Optional.of(trainingSchedule()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus());
		
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(user().getStudentId(), 1, trainingSchedule().getId());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testTakeAttendanceByStudentIdCaseTrainingScheduleInPast() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().minusDays(1));
		when(trainingScheduleRepository.findById(anyInt())).thenReturn(Optional.of(trainingSchedule));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus());
		
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(user().getStudentId(), 1, trainingSchedule().getId());
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void testTakeAttendanceByStudentIdCaseTrainingScheduleEmpty() {
		when(trainingScheduleRepository.findById(anyInt())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(user().getStudentId(), 1, trainingSchedule().getId());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testTakeAttendanceByStudentIdCaseTrainingScheduleInFuture() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().plusDays(1));
		when(trainingScheduleRepository.findById(anyInt())).thenReturn(Optional.of(trainingSchedule));
		
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(user().getStudentId(), 1, trainingSchedule().getId());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testTakeAttendanceByStudentIdCaseAttendanceStatusNull() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().minusDays(1));
		when(trainingScheduleRepository.findById(anyInt())).thenReturn(Optional.of(trainingSchedule));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(user().getStudentId(), 1, trainingSchedule().getId());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testTakeAttendanceByStudentIdCaseException() {
		ResponseMessage responseMessage = attendanceStatusService.takeAttendanceByStudentId(anyString(), anyInt(), anyInt());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testCheckAttendanceStatusByTrainingSchedule() {
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(attendanceStatus()));
		ResponseMessage responseMessage = attendanceStatusService.checkAttendanceStatusByTrainingSchedule(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testCheckAttendanceStatusByTrainingScheduleCaseAttent() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(1);
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(attendanceStatus));
		ResponseMessage responseMessage = attendanceStatusService.checkAttendanceStatusByTrainingSchedule(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testCheckAttendanceStatusByTrainingScheduleCaseException() {
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt())).thenReturn(null);
		ResponseMessage responseMessage = attendanceStatusService.checkAttendanceStatusByTrainingSchedule(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testAttendanceTrainingReport() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(),any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus()));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user()));
		ResponseMessage responseMessage = attendanceStatusService.attendanceTrainingReport("Summer2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testAttendanceTrainingReportCaseSemesterNull() {
		Optional<Semester> seOptional = Optional.empty();
		when(semesterRepository.findByName(anyString())).thenReturn(seOptional);
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus()));
		ResponseMessage responseMessage = attendanceStatusService.attendanceTrainingReport("Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testAttendanceTrainingReportCaseTrainingScheduleIdNotEq() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setId(2);
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(),any())).thenReturn(Arrays.asList(trainingSchedule));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus()));
		ResponseMessage responseMessage = attendanceStatusService.attendanceTrainingReport("Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}	
	
	@Test
	public void testAttendanceTrainingReportCaseException() {
		when(semesterRepository.findByName(anyString())).thenReturn(null);
		ResponseMessage responseMessage = attendanceStatusService.attendanceTrainingReport("Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}	

	@Test
	public void getAllAttendanceStatusByStudentIdAndSemesterCaseAttendanceStatusNotNull() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus());
	
		ResponseMessage responseMessage = attendanceStatusService.getAllAttendanceStatusByStudentIdAndSemester("HE140856", "Summer2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllAttendanceStatusByStudentIdAndSemesterCaseAttendanceStatusNull() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(null);
	
		ResponseMessage responseMessage = attendanceStatusService.getAllAttendanceStatusByStudentIdAndSemester("HE140856", "Summer2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllAttendanceStatusByStudentIdAndSemesterCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
	
		ResponseMessage responseMessage = attendanceStatusService.getAllAttendanceStatusByStudentIdAndSemester("HE140856", "Summer2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListOldTrainingScheduleToTakeAttendanceBySemesterCaseSuccess() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().minusDays(1));
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule));
		
		ResponseMessage responseMessage = attendanceStatusService.getListOldTrainingScheduleToTakeAttendanceBySemester(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListOldTrainingScheduleToTakeAttendanceBySemesterCaseSemesterNull() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().minusDays(1));
		
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule));
		
		ResponseMessage responseMessage = attendanceStatusService.getListOldTrainingScheduleToTakeAttendanceBySemester("");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListOldTrainingScheduleToTakeAttendanceBySemesterCaseTrainingScheduleNow() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage responseMessage = attendanceStatusService.getListOldTrainingScheduleToTakeAttendanceBySemester(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListOldTrainingScheduleToTakeAttendanceBySemesterCaseException() {
		ResponseMessage responseMessage = attendanceStatusService.getListOldTrainingScheduleToTakeAttendanceBySemester(anyString());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getAttendanceTrainingStatisticCaseSuccess() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus());
		
		ResponseMessage responseMessage = attendanceStatusService.getAttendanceTrainingStatistic(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAttendanceTrainingStatisticCaseStatusEq1() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(1);
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus);
		
		ResponseMessage responseMessage = attendanceStatusService.getAttendanceTrainingStatistic(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAttendanceTrainingStatisticCaseAttendanceStatusNull() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = attendanceStatusService.getAttendanceTrainingStatistic(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAttendanceTrainingStatisticCaseSemesterEmpty() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(2);
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(), anyInt())).thenReturn(attendanceStatus);
		
		ResponseMessage response = attendanceStatusService.getAttendanceTrainingStatistic("");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void getAttendanceTrainingStatisticCaseException() {
		when(semesterRepository.findByName(anyString())).thenReturn(null);
		
		ResponseMessage response = attendanceStatusService.getAttendanceTrainingStatistic("");
		assertEquals(response.getData().size(), 0);
	}
	
}
