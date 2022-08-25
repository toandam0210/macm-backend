package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.SemesterRepository;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {
	@InjectMocks
	SemesterService semesterService = new SemesterServiceImpl();
	
	@Mock
	SemesterRepository semesterRepository;
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}	
	@Test
	public void testGetCurrentSemester() {
		when(semesterRepository.findAll()).thenReturn(Arrays.asList(semester()));
		ResponseMessage responseMessage = semesterService.getCurrentSemester();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetCurrentSemesterCaseException() {
		when(semesterRepository.findAll()).thenReturn(null);
		ResponseMessage responseMessage = semesterService.getCurrentSemester();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetCurrentSemesterCaseSizeEq0() {
		List<Semester> semesters = new ArrayList<Semester>();
		when(semesterRepository.findAll()).thenReturn(semesters);
		ResponseMessage responseMessage = semesterService.getCurrentSemester();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetTop3Semesters() {
		when(semesterRepository.findTop4Semester()).thenReturn(Arrays.asList(semester()));
		ResponseMessage responseMessage = semesterService.getTop4Semesters();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetTop3SemestersCaseException() {
		when(semesterRepository.findTop4Semester()).thenReturn(null);
		ResponseMessage responseMessage = semesterService.getTop4Semesters();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	
	@Test
	public void testGetListMonthsBySemesterCaseSummer() {
		ResponseMessage responseMessage = semesterService.getListMonthsBySemester("Summer");
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void testGetListMonthsBySemesterCaseSpring() {
		ResponseMessage responseMessage = semesterService.getListMonthsBySemester("Spring");
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void testGetListMonthsBySemesterCaseFall() {
		ResponseMessage responseMessage = semesterService.getListMonthsBySemester("Fall");
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void testGetListMonthsBySemesterCaseAll() {
		when(semesterRepository.findTop4Semester()).thenReturn(Arrays.asList(semester()));
		ResponseMessage responseMessage = semesterService.getListMonthsBySemester("");
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void testGetListMonthsBySemesterCaseException() {
		when(semesterRepository.findTop4Semester()).thenReturn(null);
		ResponseMessage responseMessage = semesterService.getListMonthsBySemester("");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
