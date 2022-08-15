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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.CompetitiveResultByTypeDto;
import com.fpt.macm.model.dto.CompetitiveTypeDto;
import com.fpt.macm.model.dto.ExhibitionResultByTypeDto;
import com.fpt.macm.model.dto.ExhibitionTypeDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.dto.TournamentCreateDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CommonSchedule;
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
import com.fpt.macm.model.entity.TournamentRole;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TournamentRoleRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
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
	NotificationRepository notificationRepository;

	@Mock
	NotificationService notificationService;

	@Mock
	TournamentRoleRepository tournamentRoleRepository;

	@Mock
	CommonScheduleService commonScheduleService;

	@Mock
	TrainingScheduleService trainingScheduleService;

	@Mock
	AttendanceStatusRepository attendanceStatusRepository;

	@Mock
	TrainingScheduleRepository trainingScheduleRepository;

	@Mock
	CommonScheduleRepository commonScheduleRepository;

	@Mock
	CompetitiveMatchService competitiveMatchService;

	@Mock
	CompetitiveResultService competitiveResultService;

	@Mock
	ExhibitionResultService exhibitionResultService;

	@Mock
	ClubFundService clubFundService;

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
		user.setStudentId("HE140856");
		user.setName("Dam Van Toan");
		user.setGender(true);
		user.setActive(true);
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		user.setRole(role);
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
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusDays(10));
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().plusDays(10));
		tournament.setSemester("Summer2022");
		tournament.setStatus(true);
		tournament.setTournamentPlayers(tournamentPlayers());
		return tournament;
	}

	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.now().minusMonths(1));
		semester.setEndDate(LocalDate.now().plusMonths(1));
		return semester;
	}

	private TournamentOrganizingCommittee tournamentOrganizingCommittee() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setId(1);
		tournamentOrganizingCommittee.setPaymentStatus(true);
		tournamentOrganizingCommittee.setRoleEvent(roleEvent());
		tournamentOrganizingCommittee.setTournament(tournament());
		tournamentOrganizingCommittee.setUser(user());
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
		tournamentOrganizingCommitteeDto.setRoleTournamentDto(roleEventDto());
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
		tournamentDto.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().minusDays(1));
		tournamentDto.setRegistrationPlayerDeadline(LocalDateTime.now().minusDays(1));
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
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		CompetitivePlayer competitivePlayer = new CompetitivePlayer();
		competitivePlayer.setId(1);
		competitivePlayer.setTournamentPlayer(tournamentPlayer());
		competitivePlayer.setWeight(50);
		competitivePlayer.setCompetitiveType(competitiveTypes.get(0));
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
		tournamentOrganizingCommitteePaymentStatusReport.setUser(user());
		return tournamentOrganizingCommitteePaymentStatusReport;
	}

	private TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport() {
		TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
		tournamentPlayerPaymentStatusReport.setFundBalance(1000000);
		tournamentPlayerPaymentStatusReport.setFundChange(500000);
		tournamentPlayerPaymentStatusReport.setId(1);
		tournamentPlayerPaymentStatusReport.setPaymentStatus(true);
		tournamentPlayerPaymentStatusReport.setTournament(tournament());
		tournamentPlayerPaymentStatusReport.setUser(user());
		return tournamentPlayerPaymentStatusReport;
	}

	private ExhibitionPlayer exhibitionPlayer() {
		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
		exhibitionPlayer.setId(1);
		exhibitionPlayer.setRoleInTeam(false);
		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
		return exhibitionPlayer;
	}

	private ActiveUserDto activeUserDto() {
		ActiveUserDto activeUserDto = new ActiveUserDto();
		activeUserDto.setGender(true);
		activeUserDto.setStudentId(user().getStudentId());
		activeUserDto.setStudentName(user().getName());
		return activeUserDto;

	}

//	private Notification notification() {
//		Notification notification = new Notification();
//		notification.setId(1);
//		notification.setMessage("Test");
//		notification.setNotificationType(0);
//		notification.setNotificationTypeId(1);
//		notification.setCreatedOn(LocalDateTime.now());
//		return notification;
//	}
//
//	private NotificationToUser notificationToUser() {
//		NotificationToUser notificationToUser = new NotificationToUser();
//		notificationToUser.setId(1);
//		notificationToUser.setNotification(notification());
//		notificationToUser.setRead(false);
//		notificationToUser.setUser(user());
//		notificationToUser.setCreatedOn(LocalDateTime.now());
//		return notificationToUser;
//	}

	private UserTournamentOrganizingCommitteeDto userTournamentOrganizingCommitteeDto() {
		UserTournamentOrganizingCommitteeDto committeeDto = new UserTournamentOrganizingCommitteeDto();
		committeeDto.setRoleId(1);
		committeeDto.setUser(user());
		return committeeDto;
	}

	private ScheduleDto scheduleDto() {
		ScheduleDto scheduleDto = new ScheduleDto();
		scheduleDto.setDate(LocalDate.now().plusMonths(1));
		scheduleDto.setStartTime(LocalTime.now().minusHours(1));
		scheduleDto.setFinishTime(LocalTime.now().plusHours(1));
		scheduleDto.setExisted(false);
		scheduleDto.setTitle(tournament().getName());
		return scheduleDto;
	}

	private RoleEventDto roleEventDto() {
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(roleEvent().getId());
		roleEventDto.setName(roleEvent().getName());
		roleEventDto.setMaxQuantity(tournamentRole().getQuantity());
		roleEventDto.setAvailableQuantity(10);
		return roleEventDto;
	}

	private TournamentRole tournamentRole() {
		TournamentRole tournamentRole = new TournamentRole();
		tournamentRole.setId(1);
		tournamentRole.setRoleEvent(roleEvent());
		tournamentRole.setQuantity(10);
		tournamentRole.setTournament(tournament());
		return tournamentRole;
	}

	private TournamentCreateDto tournamentCreateDto() {
		TournamentCreateDto tournamentCreateDto = new TournamentCreateDto();
		tournamentCreateDto.setTournament(tournament());
		tournamentCreateDto.setListPreview(Arrays.asList(scheduleDto()));
		tournamentCreateDto.setRolesEventDto(Arrays.asList(roleEventDto()));
		return tournamentCreateDto;
	}

	private CommonSchedule commonSchedule() {
		CommonSchedule commonSchedule = new CommonSchedule();
		commonSchedule.setId(1);
		commonSchedule.setDate(LocalDate.now());
		commonSchedule.setTitle("Lịch tập");
		commonSchedule.setStartTime(LocalTime.now().minusHours(1));
		commonSchedule.setFinishTime(LocalTime.now().plusHours(1));
		commonSchedule.setType(1);
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
	public void testCreateTournamentCaseSuccess() {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(tournamentRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(tournament()));
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.of(roleEvent()));
		when(commonScheduleService.getCommonSessionByDate(any())).thenReturn(commonSchedule());
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByTrainingScheduleIdOrderByIdAsc(anyInt()))
				.thenReturn(Arrays.asList(attendanceStatus()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto(),
				false);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testCreateTournamentCaseRoleEventEmptyAndAllListEmpty() {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(semester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(tournamentRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(tournament()));
		when(roleEventRepository.findByName(anyString())).thenReturn(Optional.empty());
		when(roleEventRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(roleEvent()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto(),
				false);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testCreateTournamentCaseTournamentNull() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.setTournament(null);

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseListPreviewNull() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.setListPreview(null);

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseListPreviewEmpty() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.setListPreview(new ArrayList<ScheduleDto>());

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseRolesEventDtoNull() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.setRolesEventDto(null);

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseRolesEventDtoEmpty() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.setRolesEventDto(new ArrayList<RoleEventDto>());

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseScheduleExistedAndNotOverwrite() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.getListPreview().get(0).setExisted(true);
		tournamentCreateDto.getListPreview().get(0).setTitle("Trùng với Lịch tập");

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseScheduleExistedAndOverwrite() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.getListPreview().get(0).setExisted(true);
		tournamentCreateDto.getListPreview().get(0).setTitle("Trùng với Lịch tập");

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto, true);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testCreateTournamentCaseScheduleExistedAndDuplicateWithEvent() {
		TournamentCreateDto tournamentCreateDto = tournamentCreateDto();
		tournamentCreateDto.getListPreview().get(0).setExisted(true);
		tournamentCreateDto.getListPreview().get(0).setTitle("Trùng với Sự kiện");

		ResponseMessage response = tournamentService.createTournament(user().getStudentId(), tournamentCreateDto,
				false);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentId() {
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentIdCaseEmpty() {
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(new ArrayList<TournamentOrganizingCommittee>());

		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteeByTournamentIdCaseException() {
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteeByTournamentId(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRole() {
		TournamentOrganizingCommitteeDto tournamentOrganizingCommitteeDto = tournamentOrganizingCommitteeDto();
		tournamentOrganizingCommitteeDto.getRoleTournamentDto().setId(10);

		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto));
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRoleCaseRoleNotChange() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto()));

		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteeRoleCaseException() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteeRole(Arrays.asList(tournamentOrganizingCommitteeDto()));
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
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().plusMonths(1));

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		when(commonScheduleRepository.findByDate(any())).thenReturn(Optional.of(commonSchedule()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService.deleteTournamentById(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testDeleteTournamentByIdCaseOverDeadline() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));

		ResponseMessage response = tournamentService.deleteTournamentById(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeleteTournamentByIdCaseStartDateNull() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));

		ResponseMessage response = tournamentService.deleteTournamentById(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testDeleteTournamentByIdCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.deleteTournamentById(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testDeleteTournamentByIdCaseEmpty() {
		Optional<Tournament> tOptional = Optional.empty();
		when(tournamentRepository.findById(anyInt())).thenReturn(tOptional);
		ResponseMessage response = tournamentService.deleteTournamentById(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetTournamentById() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getTournamentById(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetTournamentByIdCaseStatusFalse() {
		Tournament tournament = tournament();
		tournament.setStatus(false);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.getTournamentById(1);
		assertEquals(response.getData().size(), 0);
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
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 2);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseFail1() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(1));

		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 2);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseFail2() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now());

		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq0() {
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq1() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(1));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseStatusEq3() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().plusDays(1));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		ResponseMessage response = tournamentService.getAllTournamentBySemester("Summer2022", 3);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentBySemesterCaseDateNull() {
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
	public void getStartDateCaseException() {
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(null);

		LocalDate localDate = tournamentService.getStartDate(tournament().getId());
		assertEquals(localDate, null);
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
	public void testGetAllCompetitivePlayerCaseCompetitivePlayerEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 49, 53);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseWeightMinEq0() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 0, 53);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseWeightMaxEq0() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 49, 0);
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
	public void testGetAllCompetitivePlayerCaseWeightGreaterThanMax() {
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(100);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 49, 53);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllCompetitivePlayerCaseWeightSmallerThanMin() {
		CompetitivePlayer competitivePlayer = competitivePlayer();
		competitivePlayer.setWeight(1);
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.of(competitivePlayer));
		ResponseMessage response = tournamentService.getAllCompetitivePlayer(1, 49, 53);
		assertEquals(response.getData().size(), 0);
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
	public void testGetAllExhibitionTeamCaseTypeEq1() {
		Tournament tournament = tournament();

		Set<ExhibitionType> exhibitionTypes = exhibitionTypes();
		for (ExhibitionType exhibitionType : exhibitionTypes) {
			exhibitionType.setId(2);
		}

		tournament.setExhibitionTypes(exhibitionTypes);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.getAllExhibitionTeam(1, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllExhibitionTeamCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllExhibitionTeam(1, 0);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseSuccess() {
		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentRole()));

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseAvailableQuantityEq0() {
		TournamentRole tournamentRole = tournamentRole();
		tournamentRole.setQuantity(0);

		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentRole));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndRoleInTournament(anyInt(), anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseAvailableQuantityGreaterThan0() {
		TournamentRole tournamentRole = tournamentRole();
		tournamentRole.setQuantity(10);

		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentRole));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndRoleInTournament(anyInt(), anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseRoleIdEq1() {
		TournamentRole tournamentRole = tournamentRole();
		tournamentRole.getRoleEvent().setId(1);

		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentRole));

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseEmpty() {
		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(new ArrayList<TournamentRole>());

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllOrganizingCommitteeRoleByTournamentIdCaseException() {
		when(tournamentRoleRepository.findByTournamentId(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.getAllOrganizingCommitteeRoleByTournamentId(tournament().getId());
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
	public void testGetAllTournamentPlayerPaymentStatus() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusCasePlayerEmpty() {
		Tournament tournament = tournament();
		tournament.setTournamentPlayers(new HashSet<TournamentPlayer>());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));

		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusCaseNoFee() {
		Tournament tournament = tournament();
		tournament.setFeePlayerPay(0);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));

		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusTournamentEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentPlayerPaymentStatusCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentPlayerPaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatus() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseOrganizingEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(new ArrayList<TournamentOrganizingCommittee>());
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseNoFee() {
		Tournament tournament = tournament();
		tournament.setFeeOrganizingCommiteePay(0);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseTournamentEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.empty());
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllTournamentOrganizingCommitteePaymentStatusCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllTournamentOrganizingCommitteePaymentStatus(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteePaymentStatus() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteePaymentStatus(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteePaymentStatusCasePaymentStatusFalse() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setPaymentStatus(false);

		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteePaymentStatus(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentOrganizingCommitteePaymentStatusCaseException() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));

		ResponseMessage response = tournamentService
				.updateTournamentOrganizingCommitteePaymentStatus(user().getStudentId(), 1);
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
		ResponseMessage response = tournamentService.updateTournamentPlayerPaymentStatus(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentPlayerPaymentStatusCasePaymentStatusFalse() {
		TournamentPlayer tournamentPlayer = tournamentPlayer();
		tournamentPlayer.setPaymentStatus(false);

		when(tournamentPlayerRepository.findById(anyInt())).thenReturn(Optional.of(tournamentPlayer));
		when(clubFundRepository.findAll()).thenReturn(Arrays.asList(clubFund()));
		when(tournamentRepository.findByTournamentPlayers(any())).thenReturn(Optional.of(tournament()));
		ResponseMessage response = tournamentService.updateTournamentPlayerPaymentStatus(user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testUpdateTournamentPlayerPaymentStatusCaseException() {
		when(tournamentPlayerRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.updateTournamentPlayerPaymentStatus(user().getStudentId(), 1);
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
	public void registerToJoinTournamentOrganizingComitteeCaseSuccess() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentRoleRepository.findByRoleEventIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentRole()));
		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseAlreadyJoinOrganizing() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentRoleRepository.findByRoleEventIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentRole()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCasePlayer() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentRoleRepository.findByRoleEventIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentRole()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseOverDeadline() {
		Tournament tournament = tournament();
		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().minusHours(1));

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseRoleMember() {
		RoleEvent roleEvent = roleEvent();
		roleEvent.setId(1);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent));
		when(tournamentRoleRepository.findByRoleEventIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentRole()));

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseFullSlot() {
		TournamentRole tournamentRole = tournamentRole();
		tournamentRole.setQuantity(0);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		when(tournamentRoleRepository.findByRoleEventIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentRole));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndRoleInTournament(anyInt(), anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentOrganizingComitteeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.registerToJoinTournamentOrganizingComittee(1,
				user().getStudentId(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseSuccess() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 58, 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseAlreadyRegister() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 58, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseTournamentPlayerEmpty() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 58, 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseOutOfWeightMax() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 100, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseOutOfWeightMin() {
		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseOutOfGender() {
		User user = user();
		user.setGender(false);

		List<CompetitiveType> competitiveTypes = new ArrayList<>(competitiveTypes());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.of(competitiveTypes.get(0)));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseCompetitiveTypeEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(competitiveTypeRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseAlreadyJoinOrganizing() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseOutOfDeadline() {
		Tournament tournament = tournament();
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().minusDays(1));

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentCompetitiveTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.registerToJoinTournamentCompetitiveType(tournament().getId(),
				user().getStudentId(), 0, 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserCompetitivePlayerCaseSuccess() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt()))
				.thenReturn(Optional.of(competitivePlayer()));
		when(competitivePlayerRepository.findByCompetitiveTypeId(anyInt()))
				.thenReturn(Arrays.asList(competitivePlayer()));

		ResponseMessage response = tournamentService.getAllUserCompetitivePlayer(tournament().getId(),
				user().getStudentId());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllUserCompetitivePlayerCaseCompetitivePlayerEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(competitivePlayerRepository.findByTournamentPlayerId(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllUserCompetitivePlayer(tournament().getId(),
				user().getStudentId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserCompetitivePlayerCaseTournamentPlayerEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllUserCompetitivePlayer(tournament().getId(),
				user().getStudentId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserCompetitivePlayerCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.getAllUserCompetitivePlayer(tournament().getId(),
				user().getStudentId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserExhibitionPlayer() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(Arrays.asList(exhibitionPlayer()));
		ResponseMessage response = tournamentService.getAllUserExhibitionPlayer(1, "HE140855");
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllUserExhibitionPlayerCaseSortFail() {

		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<ExhibitionPlayer>();
		exhibitionPlayers.add(exhibitionPlayer());
		ExhibitionPlayer exhibitionPlayer = exhibitionPlayer();
		exhibitionPlayer.setId(2);
		exhibitionPlayers.add(exhibitionPlayer);

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(exhibitionPlayers);
		ResponseMessage response = tournamentService.getAllUserExhibitionPlayer(1, "HE140855");
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllUserExhibitionPlayerCaseSortSuccess() {

		List<ExhibitionPlayer> exhibitionPlayers = new ArrayList<ExhibitionPlayer>();
		exhibitionPlayers.add(exhibitionPlayer());
		exhibitionPlayers.add(exhibitionPlayer());

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(exhibitionPlayerRepository.findAllByPlayerId(anyInt())).thenReturn(exhibitionPlayers);
		ResponseMessage response = tournamentService.getAllUserExhibitionPlayer(1, "HE140855");
		assertEquals(response.getData().size(), 2);
	}

	@Test
	public void testGetAllUserExhibitionPlayerCaseTournamentPlayerEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		ResponseMessage response = tournamentService.getAllUserExhibitionPlayer(1, "HE140855");
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserExhibitionPlayerCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllUserExhibitionPlayer(1, "HE140855");
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserOrganizingCommittee() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentId(anyInt()))
				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.getAllUserOrganizingCommittee(1, "HE140855");
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllUserOrganizingCommitteeCaseStatusPending() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));
		ResponseMessage response = tournamentService.getAllUserOrganizingCommittee(1, "HE140855");
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserOrganizingCommitteeEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		ResponseMessage response = tournamentService.getAllUserOrganizingCommittee(1, "HE140855");
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserOrganizingCommitteeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllUserOrganizingCommittee(1, "HE140855");
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseSuccess() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseJoinPlayer() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseNotJoinPlayer() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseTournamentEnd() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(1));

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseTournamentNotHappened() {
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().plusDays(1));

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStartDateNull() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseTournamentDeleted() {
		Tournament tournament = tournament();
		tournament.setStatus(false);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 0);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseSemesterNull() {
		Tournament tournament = tournament();
		tournament.setStatus(false);
		when(semesterRepository.findTop3Semester()).thenReturn(Arrays.asList(semester()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(), "", 0);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus1() {
		List<TournamentSchedule> tournamentSchedules = new ArrayList<TournamentSchedule>();

		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(2));

		tournamentSchedules.add(tournamentSchedule);

		tournamentSchedule.setDate(LocalDate.now().minusDays(3));

		tournamentSchedules.add(tournamentSchedule);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(tournamentSchedules);
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus1Empty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus2() {
		List<TournamentSchedule> tournamentSchedules = new ArrayList<TournamentSchedule>();
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now());
		tournamentSchedules.add(tournamentSchedule);
		tournamentSchedule.setDate(LocalDate.now());
		tournamentSchedules.add(tournamentSchedule);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(tournamentSchedules);
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 2);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus2Empty() {
		List<TournamentSchedule> tournamentSchedules = new ArrayList<TournamentSchedule>();
		TournamentSchedule tournamentSchedule = tournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now().minusDays(2));
		tournamentSchedules.add(tournamentSchedule);
		tournamentSchedule.setDate(LocalDate.now().plusDays(2));
		tournamentSchedules.add(tournamentSchedule);

		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(tournamentSchedules);
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 2);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus3() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(null);
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 3);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void getAllTournamentByStudentIdCaseStatus3Empty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findBySemester(anyString())).thenReturn(Arrays.asList(tournament()));
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(Arrays.asList(tournamentSchedule()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 3);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getAllTournamentByStudentIdCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);

		ResponseMessage response = tournamentService.getAllTournamentByStudentId(user().getStudentId(),
				semester().getName(), 3);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAddListTournamentOrganizingCommittee() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		ResponseMessage response = tournamentService.addListTournamentOrganizingCommittee("HE140855",
				Arrays.asList(userTournamentOrganizingCommitteeDto()), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAddListTournamentOrganizingCommittee2() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(roleEventRepository.findById(anyInt())).thenReturn(Optional.of(roleEvent()));
		ResponseMessage response = tournamentService.addListTournamentOrganizingCommittee("HE140855",
				Arrays.asList(userTournamentOrganizingCommitteeDto()), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testAddListTournamentOrganizingCommitteeNotJoin() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(tournamentOrganizingCommitteeRepository.findByTournamentIdAndUserId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));
		ResponseMessage response = tournamentService.addListTournamentOrganizingCommittee("HE140855",
				Arrays.asList(userTournamentOrganizingCommitteeDto()), 1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testAddListTournamentOrganizingCommitteeCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage response = tournamentService.addListTournamentOrganizingCommittee("HE140855",
				Arrays.asList(userTournamentOrganizingCommitteeDto()), 1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserNotJoinTournament() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		ResponseMessage response = tournamentService.getAllUserNotJoinTournament(1);
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void testGetAllUserNotJoinTournamentCaseJoin() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findAllActiveUser()).thenReturn(Arrays.asList(user()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.getAllUserNotJoinTournament(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void testGetAllUserNotJoinTournamentCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);
		ResponseMessage response = tournamentService.getAllUserNotJoinTournament(1);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void deleteTournamentOrganizingCommitteeCaseSuccess() {
		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
		tournamentOrganizingCommittee.setPaymentStatus(false);

		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee));

		ResponseMessage response = tournamentService
				.deleteTournamentOrganizingCommittee(tournamentOrganizingCommittee().getId());
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void deleteTournamentOrganizingCommitteeCaseHavePaid() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt()))
				.thenReturn(Optional.of(tournamentOrganizingCommittee()));

		ResponseMessage response = tournamentService
				.deleteTournamentOrganizingCommittee(tournamentOrganizingCommittee().getId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void deleteTournamentOrganizingCommitteeCaseEmpty() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage response = tournamentService
				.deleteTournamentOrganizingCommittee(tournamentOrganizingCommittee().getId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void deleteTournamentOrganizingCommitteeCaseException() {
		when(tournamentOrganizingCommitteeRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService
				.deleteTournamentOrganizingCommittee(tournamentOrganizingCommittee().getId());
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseSuccess() {
		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseTournamentPlayerNotNull() {
		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseExhibitionPlayerNotNull() {
		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(exhibitionPlayerRepository.findByTournamentPlayerAndType(anyInt(), anyInt()))
				.thenReturn(Optional.of(exhibitionPlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseFemale() {
		User user = user();
		user.setGender(false);

		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseOutOfSlot() {
		List<ActiveUserDto> activeUserDtos = new ArrayList<ActiveUserDto>();
		activeUserDtos.add(activeUserDto());
		activeUserDtos.add(activeUserDto());

		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", activeUserDtos);
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseRoleFalse() {
		List<ExhibitionType> exhibitionTypes = new ArrayList<>(exhibitionTypes());
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(exhibitionTypeRepository.findById(anyInt())).thenReturn(Optional.of(exhibitionTypes.get(0)));
		when(tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.of(tournamentPlayer()));
		when(tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(anyInt(), anyInt()))
				.thenReturn(Optional.empty());

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				"HE141122", 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 1);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseOutOfDeadline() {
		Tournament tournament = tournament();
		tournament.setRegistrationPlayerDeadline(LocalDateTime.now().minusHours(1));

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament));

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void registerToJoinTournamentExhibitionTypeCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage response = tournamentService.registerToJoinTournamentExhibitionType(tournament().getId(),
				user().getStudentId(), 1, "Team 1", Arrays.asList(activeUserDto()));
		assertEquals(response.getData().size(), 0);
	}

	@Test
	public void getEndDateNull() {
		when(tournamentScheduleRepository.findByTournamentId(anyInt())).thenReturn(new ArrayList<TournamentSchedule>());

		LocalDate date = tournamentService.getEndDate(tournament().getId());
		assertEquals(date, null);
	}

	@Test
	public void getResultOfTournamentCaseSuccess() {
		ResponseMessage competitiveResultResponse = new ResponseMessage();
		competitiveResultResponse.setData(Arrays.asList(new CompetitiveResultByTypeDto()));

		ResponseMessage exhibitionResultResponse = new ResponseMessage();
		exhibitionResultResponse.setData(Arrays.asList(new ExhibitionResultByTypeDto()));

		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.of(tournament()));
		when(competitiveResultService.getResultByType(anyInt())).thenReturn(competitiveResultResponse);
		when(exhibitionResultService.getExhibitionResultByType(anyInt())).thenReturn(exhibitionResultResponse);

		ResponseMessage responseMessage = tournamentService.getResultOfTournament(tournament().getId());
		assertEquals(responseMessage.getData().size(), 1);
	}

	@Test
	public void getResultOfTournamentCaseEmpty() {
		when(tournamentRepository.findById(anyInt())).thenReturn(Optional.empty());

		ResponseMessage responseMessage = tournamentService.getResultOfTournament(tournament().getId());
		assertEquals(responseMessage.getData().size(), 0);
	}

	@Test
	public void getResultOfTournamentCaseException() {
		when(tournamentRepository.findById(anyInt())).thenReturn(null);

		ResponseMessage responseMessage = tournamentService.getResultOfTournament(tournament().getId());
		assertEquals(responseMessage.getData().size(), 0);
	}

}
