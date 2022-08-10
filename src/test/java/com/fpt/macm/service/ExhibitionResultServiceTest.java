package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;

@ExtendWith(MockitoExtension.class)
public class ExhibitionResultServiceTest {

	@InjectMocks
	ExhibitionResultService exhibitionResultService = new ExhibitionResultServiceImpl();
	
	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	TournamentScheduleRepository tournamentScheduleRepository;

	@Mock
	AreaRepository areaRepository;

	@Mock
	ExhibitionResultRepository exhibitionResultRepository;

	@Mock
	ExhibitionTeamRepository exhibitionTeamRepository;

	@Mock
	CompetitiveTypeService competitiveTypeService;

	@Mock
	CompetitiveMatchRepository competitiveMatchRepository;
	
	@Mock
	ExhibitionTypeRepository exhibitionTypeRepository;
	
	private Set<CompetitiveType> competitiveTypes() {
		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setId(1);
		competitiveType.setGender(true);
		competitiveType.setWeightMax(60);
		competitiveType.setWeightMin(57);
		competitiveTypes.add(competitiveType);
		return competitiveTypes;
	}

	private Set<ExhibitionType> exhibitionTypes() {
		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		exhibitionTypes.add(exhibitionType());
		return exhibitionTypes;
	}
	
	private ExhibitionType exhibitionType() {
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setExhibitionTeams(exhibitionTeams());
		exhibitionType.setId(1);
		exhibitionType.setName("Long ho quyen");
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(1);
		return exhibitionType;
	}

	private Set<ExhibitionTeam> exhibitionTeams() {
		Set<ExhibitionTeam> exhibitionTeams = new HashSet<ExhibitionTeam>();
		exhibitionTeams.add(exhibitionTeam());
		return exhibitionTeams;
	}
	
	private ExhibitionTeam exhibitionTeam() {
		ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
		exhibitionTeam.setExhibitionPlayers(exhibitionPlayers());
		exhibitionTeam.setId(1);
		exhibitionTeam.setTeamName("Team 1");
		return exhibitionTeam;
	}

	private Set<ExhibitionPlayer> exhibitionPlayers() {
		Set<ExhibitionPlayer> exhibitionPlayers = new HashSet<ExhibitionPlayer>();
		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
		exhibitionPlayer.setId(1);
		exhibitionPlayer.setRoleInTeam(true);
		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
		exhibitionPlayers.add(exhibitionPlayer);
		return exhibitionPlayers;
	}

	private TournamentPlayer tournamentPlayer() {
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(user());
		return tournamentPlayer;
	}

	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(user());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
	}

	private User user() {
		User user = new User();
		user.setId(1);
		user.setStudentId("HE140855");
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

	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setCompetitiveTypes(competitiveTypes());
		tournament.setDescription("abc");
		tournament.setExhibitionTypes(exhibitionTypes());
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setFeePlayerPay(100000);
		tournament.setId(1);
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(true);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
	}
	
	private ExhibitionResult exhibitionResult() {
		ExhibitionResult exhibitionResult = new ExhibitionResult();
		for (ExhibitionTeam exhibitionTeam : exhibitionTeams()) {
			exhibitionResult.setId(1);
			exhibitionResult.setTeam(exhibitionTeam);
			exhibitionResult.setScore(0.0);
			exhibitionResult.setTime(LocalDateTime.now());
			exhibitionResult.setArea(area());
		}
		return exhibitionResult;
	}
	
	private Area area() {
		Area area = new Area();
		area.setId(1);
		area.setName("Sân 1");
		return area;
	}
	
	private CompetitiveMatch competitiveMatch() {
		CompetitiveMatch competitiveMatch = new CompetitiveMatch();
		competitiveMatch.setId(1);
		competitiveMatch.setRound(1);
		competitiveMatch.setFirstStudentId("HE140855");
		competitiveMatch.setSecondStudentId("HE140855");
		return competitiveMatch;
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
	
	@Test
	public void spawnTimeAndAreaCaseSuccess() {
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void spawnTimeAndAreaCaseRoundDifferentFrom1() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setRound(2);
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void spawnTimeAndAreaCaseFirstStudentIdNull() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setFirstStudentId(null);
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void spawnTimeAndAreaCaseSecondStudentIdNull() {
		CompetitiveMatch competitiveMatch = competitiveMatch();
		competitiveMatch.setSecondStudentId(null);
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void spawnTimeAndAreaCaseExhibitionTeamSizeEqual0() {
		Tournament tournament = tournament();
		Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
		for (ExhibitionType exhibitionType : exhibitionTypes) {
			exhibitionType.setExhibitionTeams(new HashSet<ExhibitionTeam>());
		}
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void spawnTimeAndAreaCaseExhibitionTeamSizeGreaterThanMatchCanHeld() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setStartTime(LocalTime.now());
		tournamentSchedule.setFinishTime(LocalTime.now().plusMinutes(1));
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void spawnTimeAndAreaCase3Type() {
		ExhibitionType exhibitionType1 = exhibitionType();
		exhibitionType1.setId(1);
		ExhibitionType exhibitionType2 = exhibitionType();
		exhibitionType2.setId(2);
		ExhibitionType exhibitionType3 = exhibitionType();
		exhibitionType3.setId(3);
		
		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		exhibitionTypes.add(exhibitionType1);
		exhibitionTypes.add(exhibitionType2);
		exhibitionTypes.add(exhibitionType3);
		
		Tournament tournament = tournament();
		tournament.setExhibitionTypes(exhibitionTypes);
		
		when(competitiveTypeService.getAllTypeByTournament(anyInt())).thenReturn(competitiveTypes());
		when(areaRepository.findAll()).thenReturn(Arrays.asList(area()));
		when(competitiveMatchRepository.listMatchsByType(anyInt())).thenReturn(Arrays.asList(competitiveMatch()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		
		ResponseMessage responseMessage = exhibitionResultService.spawnTimeAndArea(1);
		assertEquals(responseMessage.getData().size(), 3);
	}
	
	@Test
	public void getListExhibitionResultCaseExhibitionTypeIdNotNull() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult()));
		
		ResponseMessage responseMessage = exhibitionResultService.getListExhibitionResult(1, "");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListExhibitionResultCaseDateNotNull() {
		when(exhibitionResultRepository.findAll()).thenReturn(Arrays.asList(exhibitionResult()));
		
		ResponseMessage responseMessage = exhibitionResultService.getListExhibitionResult(0, (LocalDate.now().getDayOfMonth() < 10 ? "0" : "") + LocalDate.now().getDayOfMonth() + "/" + (LocalDate.now().getMonthValue() < 10 ? "0" : "") + LocalDate.now().getMonthValue() + "/" + LocalDate.now().getYear());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getListExhibitionResultCaseDateNotNullAndNotEqualToExhibitionResultDate() {
		when(exhibitionResultRepository.findAll()).thenReturn(Arrays.asList(exhibitionResult()));
		
		ResponseMessage responseMessage = exhibitionResultService.getListExhibitionResult(0, "01/01/2022");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListExhibitionResultCaseDateAndTypeNull() {
		ResponseMessage responseMessage = exhibitionResultService.getListExhibitionResult(0, "");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getListExhibitionResultCaseException() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = exhibitionResultService.getListExhibitionResult(1, "");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void updateExhibitionResultCaseSuccess() {
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult()));
		when(exhibitionTeamRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTeam()));
		
		ResponseMessage responseMessage = exhibitionResultService.updateExhibitionResult(1, 100);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void updateExhibitionResultCaseException() {
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(null);
		
		ResponseMessage responseMessage = exhibitionResultService.updateExhibitionResult(1, 100);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetExhibitionResultByType() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult()));
		ResponseMessage responseMessage = exhibitionResultService.getExhibitionResultByType(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetExhibitionResultByTypeCaseEmpty() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.empty());
		ResponseMessage responseMessage = exhibitionResultService.getExhibitionResultByType(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetExhibitionResultByTypeCaseTypeEmpty() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage responseMessage = exhibitionResultService.getExhibitionResultByType(1);
		assertEquals(responseMessage.getData().size(),0);
	}
	
	@Test
	public void testGetExhibitionResultByTypeCaseResultNull() {
		ExhibitionResult exhibitionResult = exhibitionResult();
		exhibitionResult.setScore(null);
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionType()));
		when(exhibitionResultRepository.findByTeam(anyInt())).thenReturn(Optional.of(exhibitionResult));
		ResponseMessage responseMessage = exhibitionResultService.getExhibitionResultByType(1);
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetExhibitionResultByTypeCaseException() {
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage responseMessage = exhibitionResultService.getExhibitionResultByType(1);
		assertEquals(responseMessage.getData().size(), 0);
	}
	
}
