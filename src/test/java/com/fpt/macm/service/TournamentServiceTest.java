package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.CompetitiveTypeDto;
import com.fpt.macm.model.dto.ExhibitionTypeDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class TournamentServiceTest {
	@InjectMocks
	TournamentService tournamentService = new TournamentServiceImpl();

	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	SemesterService semesterService;

	@Mock
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Mock
	CompetitiveTypeRepository competitiveTypeRepository;

	@Mock
	ExhibitionTypeRepository exhibitionTypeRepository;

	@Mock
	SemesterRepository semesterRepository;

	@Mock
	TournamentScheduleRepository tournamentScheduleRepository;

	@Mock
	TournamentPlayerRepository tournamentPlayerRepository;

	@Mock
	CompetitivePlayerRepository competitivePlayerRepository;

	@Mock
	ExhibitionPlayerRepository exhibitionPlayerRepository;

	@Mock
	ExhibitionTeamRepository exhibitionTeamRepository;

	@Mock
	RoleEventRepository roleEventRepository;

	@Mock
	ClubFundRepository clubFundRepository;

	@Mock
	CompetitiveMatchRepository competitiveMatchRepository;

	@Mock
	TournamentOrganizingCommitteePaymentStatusReportRepository tournamentOrganizingCommitteePaymentStatusReportRepository;

	@Mock
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;

	@Mock
	UserRepository userRepository;

	@Mock
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;

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
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setExhibitionTeams(exhibitionTeams());
		exhibitionType.setId(1);
		exhibitionType.setName("Long ho quyen");
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(1);
		exhibitionTypes.add(exhibitionType);
		return exhibitionTypes;
	}

	private Set<ExhibitionTeam> exhibitionTeams() {
		Set<ExhibitionTeam> exhibitionTeams = new HashSet<ExhibitionTeam>();
		ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
		exhibitionTeam.setExhibitionPlayers(exhibitionPlayers());
		exhibitionTeam.setId(1);
		exhibitionTeam.setTeamName("Team 1");
		exhibitionTeams.add(exhibitionTeam);
		return exhibitionTeams;
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
		tournamentPlayer.setUser(createUser());
		return tournamentPlayer;
	}

	private Set<TournamentPlayer> tournamentPlayers() {
		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
		TournamentPlayer tournamentPlayer = new TournamentPlayer();
		tournamentPlayer.setId(1);
		tournamentPlayer.setPaymentStatus(true);
		tournamentPlayer.setUser(createUser());
		tournamentPlayers.add(tournamentPlayer);
		return tournamentPlayers;
	}

	private User createUser() {
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

	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setCompetitiveTypes(competitiveTypes());
		tournament.setDescription("abc");
		tournament.setExhibitionTypes(exhibitionTypes());
		tournament.setFeeOrganizingCommiteePay(100000);
		tournament.setFeePlayerPay(100000);
		tournament.setId(1);
		tournament.setMaxQuantityComitee(10);
		tournament.setName("FNC");
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournament.setSemester("Summer2022");
		tournament.setStatus(1);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
	}

	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}

	private TournamentOrganizingCommittee tournamentOrganizingCommittee() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setId(1);
		tournamentOrganizingCommittee.setPaymentStatus(true);
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("Ban truyen thong");
		tournamentOrganizingCommittee.setRoleEvent(roleEvent);
		tournamentOrganizingCommittee.setTournament(tournament());
		tournamentOrganizingCommittee.setUser(createUser());
		tournamentOrganizingCommittee.setCreatedBy("toandv");
		tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
		tournamentOrganizingCommittee.setUpdatedBy("toandv");
		tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
		return tournamentOrganizingCommittee;
	}

	private RoleEvent roleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(2);
		roleEvent.setName("Ban van hoa");
		return roleEvent;
	}

	private TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto() {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = new TournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.setId(1);
		tournamentOrganizingCommitteeDto.setPaymentStatus(true);
		tournamentOrganizingCommitteeDto.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(2);
		roleEventDto.setName("Ban van hoa");
		tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto);
		tournamentOrganizingCommitteeDto.setUserName("toan");
		tournamentOrganizingCommitteeDto.setUserStudentId("HE140855");
		return tournamentOrganizingCommitteeDto;
	}

	private TournamentDto tournamentDto() {
		TournamentDto tournamentDto = new TournamentDto();
		tournamentDto.setCompetitiveTypesDto(competitiveTypesDto());
		tournamentDto.setDescription("abc");
		tournamentDto.setExhibitionTypesDto(exhibitionTypesDtos());
		tournamentDto.setFeeOrganizingCommiteePay(100000);
		tournamentDto.setFeePlayerPay(100000);
		tournamentDto.setId(1);
		tournamentDto.setMaxQuantityComitee(10);
		tournamentDto.setName("FNC");
		tournamentDto.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournamentDto.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
		tournamentDto.setStatus(1);
		tournamentDto.setTournamentPlayers(tournamentPlayers());
		return tournamentDto;
	}

	private Set<CompetitiveTypeDto> competitiveTypesDto() {
		Set<CompetitiveTypeDto> competitiveTypesDto = new HashSet<CompetitiveTypeDto>();
		CompetitiveTypeDto competitiveTypeDto = new CompetitiveTypeDto();
		competitiveTypeDto.setId(1);
		competitiveTypeDto.setGender(true);
		competitiveTypeDto.setWeightMax(60);
		competitiveTypeDto.setWeightMin(57);
		competitiveTypesDto.add(competitiveTypeDto);
		return competitiveTypesDto;
	}

	private Set<ExhibitionTypeDto> exhibitionTypesDtos() {
		Set<ExhibitionTypeDto> exhibitionTypes = new HashSet<ExhibitionTypeDto>();
		ExhibitionTypeDto exhibitionType = new ExhibitionTypeDto();
		exhibitionType.setId(1);
		exhibitionType.setName("Long ho quyen");
		exhibitionType.setNumberFemale(0);
		exhibitionType.setNumberMale(1);
		exhibitionTypes.add(exhibitionType);
		return exhibitionTypes;
	}

	private TournamentSchedule tournamentSchedule() {
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now());
		tournamentSchedule.setFinishTime(LocalTime.of(20, 0));
		tournamentSchedule.setId(1);
		tournamentSchedule.setStartTime(LocalTime.of(18, 0));
		tournamentSchedule.setTournament(tournament());
		return tournamentSchedule;
	}

	private CompetitivePlayer competitivePlayer() {
		CompetitivePlayer competitivePlayer = new CompetitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayer.setTournamentPlayer(tournamentPlayer());
		competitivePlayer.setWeight(50);
		return competitivePlayer;
	}

	private ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(1000000);
		return clubFund;
	}

	private TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport() {
		TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport = new TournamentOrganizingCommitteePaymentStatusReport();
		tournamentOrganizingCommitteePaymentStatusReport.setFundBalance(1000000);
		tournamentOrganizingCommitteePaymentStatusReport.setFundChange(500000);
		tournamentOrganizingCommitteePaymentStatusReport.setId(1);
		tournamentOrganizingCommitteePaymentStatusReport.setPaymentStatus(true);
		tournamentOrganizingCommitteePaymentStatusReport.setTournament(tournament());
		tournamentOrganizingCommitteePaymentStatusReport.setUser(createUser());
		return tournamentOrganizingCommitteePaymentStatusReport;
	}

	private TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport() {
		TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
		tournamentPlayerPaymentStatusReport.setFundBalance(1000000);
		tournamentPlayerPaymentStatusReport.setFundChange(500000);
		tournamentPlayerPaymentStatusReport.setId(1);
		tournamentPlayerPaymentStatusReport.setPaymentStatus(true);
		tournamentPlayerPaymentStatusReport.setTournament(tournament());
		tournamentPlayerPaymentStatusReport.setUser(createUser());
		return tournamentPlayerPaymentStatusReport;
	}

	@Test
	public void testCreateTournament() {
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = tournamentService.createTournament(tournament());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testCreateTournamentCaseException() {
		when(semesterService.getCurrentSemester()).thenReturn(null);
		ResponseMessage response = tournamentService.createTournament(tournament());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentId() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentIdCaseResgisterStatus() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentIdCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRole() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto()));
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRoleCaseRoleNotChange() {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = tournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.getRoleTournamentDto().setId(1);
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRoleCaseException() {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = tournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.getRoleTournamentDto().setId(1);
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournament() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.updateTournament(1, tournamentDto());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentCaseTournamentNotFound() {
		Optional<Tournament> tOptional = Optional.empty();
		when(tournamentRepository.findById(anyInt())).thenReturn(tOptional);
		ResponseMessage response = tournamentService.updateTournament(1, tournamentDto());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentCaseType() {
		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
		CompetitiveType competitiveType = new CompetitiveType();
		competitiveType.setGender(true);
		competitiveType.setWeightMax(50);
		competitiveType.setWeightMin(47);
		competitiveTypes.add(competitiveType);
		CompetitiveType competitiveType2 = new CompetitiveType();
		competitiveType2.setGender(true);
		competitiveType2.setWeightMax(70);
		competitiveType2.setWeightMin(68);
		competitiveTypes.add(competitiveType2);

		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
		ExhibitionType exhibitionType = new ExhibitionType();
		exhibitionType.setName("Bao sat");
		exhibitionType.setNumberFemale(1);
		exhibitionType.setNumberMale(2);

		Tournament tournament = tournament();
		tournament.setCompetitiveTypes(competitiveTypes);
		tournament.setExhibitionTypes(exhibitionTypes);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.updateTournament(1, tournamentDto());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.updateTournament(1, tournamentDto());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeleteTournamentById() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.deleteTournamentById(1);
		assertEquals(response.getMessage(), Constant.MSG_102);
	}

	@Test
	public void testDeleteTournamentByIdCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.deleteTournamentById(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeleteTournamentByIdCaseEmpty() {
		Optional<Tournament> tOptional = Optional.empty();
		when(tournamentRepository.findById(anyInt())).thenReturn(tOptional);
		ResponseMessage response = tournamentService.deleteTournamentById(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetTournamentById() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getTournamentById(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetTournamentByIdCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getTournamentById(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetTournamentByIdCaseEmpty() {
		Optional<Tournament> tOptional = Optional.empty();
		when(tournamentRepository.findById(anyInt())).thenReturn(tOptional);
		ResponseMessage response = tournamentService.getTournamentById(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentBySemester() {
		// when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 2);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq0() {
		// when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq1() {
		// when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(1));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq3() {
		// when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().plusDays(1));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 3);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseDateNull() {
		// when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(null);
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 3);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseException() {
		when(tournamentRepository.findBySemester(anyString())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 3);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseSemesterNull() {
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("", 3);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllCompetitivePlayer() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 49, 53);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseWeightEq0() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 0, 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseWeightInRange() {
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 0, 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseException() {
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 0, 0);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllExhibitionTeam() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getAllExhibitionTeam(1, 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllExhibitionTeamCaseTypeEq0() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getAllExhibitionTeam(1, 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllExhibitionTeamCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllExhibitionTeam(1, 0);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllOrganizingCommitteeRole() {
		when(roleEventRepository.findAllOrganizingCommitteeRole()).thenReturn(Arrays.asList(roleEvent()));
		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRole();
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllOrganizingCommitteeRoleCaseException() {
		when(roleEventRepository.findAllOrganizingCommitteeRole()).thenReturn(null);
		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRole();
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllExhibitionType() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getAllExhibitionType(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllExhibitionTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllExhibitionType(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAcceptRequestOrganizingCommittee() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.acceptRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAcceptRequestOrganizingCommitteeCasePending() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.acceptRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testAcceptRequestOrganizingCommitteeCaseFull() {
		Tournament tournament = tournament();
		tournament.setMaxQuantityComitee(0);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.acceptRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAcceptRequestOrganizingCommitteeCaseException() {
		ResponseMessage response = tournamentService.acceptRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeclineRequestOrganizingCommittee() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.declineRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testDeclineRequestOrganizingCommitteeCaseException() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.declineRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeclineRequestOrganizingCommitteeCaseNotEqPending() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.declineRequestOrganizingCommittee(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatus() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatus() {
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseStatusNotEqApproved() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_PENDING);
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseException() {
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteePaymentStatus() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		ResponseMessage response = tournamentService.updateTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteePaymentStatusCaseException() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.updateTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusReport() {
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommitteePaymentStatusReport()));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatusReport(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusReportCaseException() {
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findByTournamentId(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatusReport(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentPlayerPaymentStatus() {
		when(tournamentPlayerRepository.findById(anyInt())).thenReturn(Optional.of(tournamentPlayer()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(tournamentRepository.findByTournamentPlayers(any())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.updateTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentPlayerPaymentStatusCaseException() {
		when(tournamentPlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.updateTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusReport() {
		when(tournamentPlayerPaymentStatusReportRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentPlayerPaymentStatusReport()));
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatusReport(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusReportCaseException() {
		when(tournamentPlayerPaymentStatusReportRepository.findByTournamentId(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatusReport(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllCompetitivePlayerByType() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = tournamentService.getAllCompetitivePlayerByType(1, 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllCompetitivePlayerByTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllCompetitivePlayerByType(1, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseAlreadyRegister() {
		User user = createUser();
		user.setStudentId("HE140140");
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseAccessDenied() {
		User user = createUser();
		user.setStudentId("HE140140");
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_DECLINED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseUnregister() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCasePlayerRegister() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseFull() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		Tournament tournament = tournament();
		tournament.setMaxQuantityComitee(0);
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseRoleMember() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		RoleEvent roleEvent = roleEvent();
		roleEvent.setName("ROLE_Member");
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseOverTimeToRegister() {
		Tournament tournament = tournament();
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().minusDays(1));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1, "HE140140", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseAlreadyRegister() {
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140140", 58, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseGenderInvalid() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140140", 58, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseWeightInvalid() {
		User user = createUser();
		user.setStudentId("HE140140");
		user.setId(2);
		user.setGender(false);
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(55);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140140", 55, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseWeightIsEmpty() {
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140140", 58, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseRegisterSuccess() {
		Tournament tournament = tournament();
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().plusDays(1));
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.empty());
		when(competitivePlayerRepository.findCompetitivePlayerByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140855", 58, 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeCaseAlreadyRegistered() {
		Tournament tournament = tournament();
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().plusDays(1));
		Set<CompetitiveType> competitiveTypes = competitiveTypes();
		List<CompetitiveType> listCompetitive = new ArrayList<CompetitiveType>(competitiveTypes);
		CompetitiveType competitiveType = listCompetitive.get(0);
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(58);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveType));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140855", 58, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testRegisterToJoinTournamentCompetitiveTypeOverTimeToRegister() {
		Tournament tournament = tournament();
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().minusDays(1));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(1, "HE140855", 58, 1);
		assertEquals(response.getData().size(), 0);

	}

}
