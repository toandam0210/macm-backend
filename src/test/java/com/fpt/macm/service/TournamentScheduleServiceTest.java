package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class TournamentScheduleServiceTest {

	@InjectMocks
	TournamentScheduleService tournamentScheduleService = new TournamentScheduleServiceImpl();
	
	@Mock
	TournamentScheduleRepository tournamentScheduleRepository;

	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	SemesterService semesterService;

	@Mock
	CommonScheduleService commonScheduleService;
	
	@Mock
	CommonScheduleRepository commonScheduleRepository;

	@Mock
	NotificationService notificationService;
	
	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setId(1);
		tournament.setName("Giải đấu FNC Summer2022");
		tournament.setDescription("Giải đấu cho thành viên FNC Summer2022");
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setSemester("Summer2022");
		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setGender(true);
		competitiveType.setWeightMax(80);
		competitiveType.setWeightMin(77);
		competitiveTypes.add(competitiveType);
		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setName("Khai nguyên");
		exhibitionType.setNumberMale(3);
		exhibitionType.setNumberFemale(3);
		exhibitionTypes.add(exhibitionType);
		tournament.setCompetitiveTypes(competitiveTypes);
		tournament.setExhibitionTypes(exhibitionTypes);
		tournament.setTotalAmount(500000);
		tournament.setFeePlayerPay(20000);
		tournament.setStatus(true);
		return tournament;
	}
	
	private TournamentSchedule tournamentSchedule() {
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		tournamentSchedule.setId(1);
		tournamentSchedule.setDate(LocalDate.now().plusMonths(1));
		tournamentSchedule.setStartTime(LocalTime.of(18, 0));
		tournamentSchedule.setFinishTime(LocalTime.of(20, 0));
		tournamentSchedule.setTournament(tournament());
		return tournamentSchedule;
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
	
//	private ScheduleDto scheduleDto() {
//		ScheduleDto scheduleDto = new ScheduleDto();
//		scheduleDto.setDate(LocalDate.now().plusMonths(1));
//		scheduleDto.setStartTime(LocalTime.of(18, 0));
//		scheduleDto.setFinishTime(LocalTime.of(20, 0));
//		scheduleDto.setExisted(false);
//		scheduleDto.setTitle("Giải đấu FNC Summer2022 update");
//		return scheduleDto;
//	}
	
	@Test
	public void createPreviewTournamentScheduleCaseStartDateAfterEndDate() {
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "30/10/2022", "29/10/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseStartTimeAfterFinishTime() {
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/10/2022", "30/10/2022", "16:00", "08:00");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseStartDateInPast() {
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/01/2022", "30/01/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseNotInSemester() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/10/2022", "30/10/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseNotExisted() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(null);
		
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/08/2022", "30/08/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 2);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseExisted() {
		ResponseMessage semesterResponse = new ResponseMessage();
		semesterResponse.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(semesterResponse);
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/08/2022", "30/08/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 2);
	}
	
	@Test
	public void createPreviewTournamentScheduleCaseException() {
		when(semesterService.getCurrentSemester()).thenReturn(null);
		
		ResponseMessage response = tournamentScheduleService.createPreviewTournamentSchedule("FNC", "29/08/2022", "30/08/2022", "08:00", "16:00");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void getListTournamentScheduleByTournamentCaseSuccess() {
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = tournamentScheduleService.getListTournamentScheduleByTournament(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListTournamentScheduleByTournamentCaseException() {
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = tournamentScheduleService.getListTournamentScheduleByTournament(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
//	@Test
//	public void createTournamentScheduleCaseNotExisted() {
//		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
//		
//		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSchedule(1, Arrays.asList(scheduleDto()), false);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void createTournamentScheduleCaseExistedAndNotOverwrite() {
//		ScheduleDto scheduleDto = scheduleDto();
//		scheduleDto.setExisted(true);
//		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
//		
//		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSchedule(1, Arrays.asList(scheduleDto), false);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void createTournamentScheduleCaseExistedAndOverwriteAndNotTrainingSchedule() {
//		ScheduleDto scheduleDto = scheduleDto();
//		scheduleDto.setExisted(true);
//		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
//		
//		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSchedule(1, Arrays.asList(scheduleDto), true);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void createTournamentScheduleCaseExistedAndOverwriteTrainingSchedule() {
//		ScheduleDto scheduleDto = scheduleDto();
//		scheduleDto.setExisted(true);
//		scheduleDto.setTitle("Trùng với Lịch tập");
//		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
//		
//		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSchedule(1, Arrays.asList(scheduleDto), true);
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void createTournamentScheduleCaseException() {
//		when(tournamentRepository.findById(anyInt())).thenReturn(null);
//		
//		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSchedule(1, Arrays.asList(scheduleDto()), true);
//		assertEquals(responseMessage.getData().size(), 0);
//	}
	
	@Test
	public void createTournamentSessionCaseStartTimeAfterFinishTime() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setStartTime(LocalTime.of(16, 0));
		tournamentSchedule.setFinishTime(LocalTime.of(8, 0));
		
		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSession(1, tournamentSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTournamentSessionCaseDateInPast() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusMonths(1));
		
		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSession(1, tournamentSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTournamentSessionCaseCommonScheduleNotNull() {
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSession(1, tournamentSchedule());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void createTournamentSessionCaseCommonScheduleNull() {
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(null);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSession(1, tournamentSchedule());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void createTournamentSessionCaseException() {
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(null);
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = tournamentScheduleService.createTournamentSession(1, tournamentSchedule());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateTournamentSessionCaseScheduleInPast() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusMonths(1));
		
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(Optional.of(tournamentSchedule));
		
		ResponseMessage responseMessage = tournamentScheduleService.updateTournamentSession(1, tournamentSchedule);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateTournamentSessionCaseSuccess() {
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(Optional.of(tournamentSchedule()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = tournamentScheduleService.updateTournamentSession(1, tournamentSchedule());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateTournamentSessionCaseException() {
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = tournamentScheduleService.updateTournamentSession(1, tournamentSchedule());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void deleteTournamentSessionCaseScheduleInPast() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusMonths(1));
		
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(Optional.of(tournamentSchedule));
		
		ResponseMessage responseMessage = tournamentScheduleService.deleteTournamentSession(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void deleteTournamentSessionCaseSuccess() {
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(Optional.of(tournamentSchedule()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		
		ResponseMessage responseMessage = tournamentScheduleService.deleteTournamentSession(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void deleteTournamentSessionCaseException() {
		when(tournamentScheduleRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = tournamentScheduleService.deleteTournamentSession(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getTournamentSessionByDateCaseSuccess() {
		when(tournamentScheduleRepository.findByDate(any())).thenReturn(Optional.of(tournamentSchedule()));
		
		TournamentSchedule tournamentSchedule = tournamentScheduleService.getTournamentSessionByDate(LocalDate.now().plusMonths(1));
		assertEquals(tournamentSchedule.getId(), tournamentSchedule().getId());
	}
	
	@Test
	public void getTournamentSessionByDateCaseTournamentScheduleEmpty() {
		when(tournamentScheduleRepository.findByDate(any())).thenReturn(Optional.empty());
		
		TournamentSchedule tournamentSchedule = tournamentScheduleService.getTournamentSessionByDate(LocalDate.now().plusMonths(1));
		assertEquals(tournamentSchedule, null);
	}
	
	@Test
	public void getTournamentSessionByDateCaseException() {
		when(tournamentScheduleRepository.findByDate(any())).thenReturn(null);
		
		TournamentSchedule tournamentSchedule = tournamentScheduleService.getTournamentSessionByDate(LocalDate.now().plusMonths(1));
		assertEquals(tournamentSchedule, null);
	}
}
