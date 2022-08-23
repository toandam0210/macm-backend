package com.fpt.macm.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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

import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
public class CommonScheduleServiceTest {
	@InjectMocks
	CommonScheduleService commonScheduleService = new CommonScheduleServiceImpl();
	
	@Mock
	CommonScheduleRepository commonScheduleRepository;
	
	@Mock
	SemesterRepository semesterRepository;
	
	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setDate(LocalDate.now());
		commonSchedule.setFinishTime(LocalTime.of(20, 0));
		commonSchedule.setId(1);
		commonSchedule.setStartTime(LocalTime.of(18, 0));
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setType(0);
		return commonSchedule;
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
	public void testGetCommonSchedule() {
		when(commonScheduleRepository.listAll()).thenReturn(Arrays.asList(commonSchedule()));
		ResponseMessage responseMessage = commonScheduleService.getCommonSchedule();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetCommonSessionByDate() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.of(commonSchedule()));
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleByDate("28/07/2022");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetCommonSessionByDateCaseNull() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(null);
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleByDate("28/07/2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetCommonSessionByDateCaseEmpty() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.empty());
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleByDate("28/07/2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetCommonSessionByDateCaseException() {
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleByDate("abc");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetCommonScheduleBySemester() {
		when(semesterRepository.findById(anyInt())).thenReturn(Optional.of(semester()));
		when(commonScheduleRepository.listCommonScheduleByTime(any(), any())).thenReturn(Arrays.asList(commonSchedule()));
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleBySemester(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetCommonScheduleBySemesterCaseException() {
		when(semesterRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage responseMessage = commonScheduleService.getCommonScheduleBySemester(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetCommonSessionByDate1() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.of(commonSchedule()));
		CommonSchedule response = commonScheduleService.getCommonSessionByDate(LocalDate.now());
		assertEquals(response.getDate(), LocalDate.now());
	}
	
	@Test
	public void testGetCommonSessionByDate1CaseEmpty() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.empty());
		CommonSchedule response = commonScheduleService.getCommonSessionByDate(LocalDate.now());
		assertNull(response);
	}
	
	@Test
	public void testGetCommonSessionByDate1CaseNull() {
		when(commonScheduleRepository.findByDate(any())).thenReturn(null);
		CommonSchedule response = commonScheduleService.getCommonSessionByDate(LocalDate.now());
		assertNull(response);
	}
}
