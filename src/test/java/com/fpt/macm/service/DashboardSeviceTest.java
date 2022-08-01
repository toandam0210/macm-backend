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
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.ClubFundReport;
import com.fpt.macm.model.entity.CollaboratorReport;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventPaymentStatusReport;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.MembershipPaymentStatusReport;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommitteePaymentStatusReport;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.entity.UserStatusReport;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.ClubFundReportRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CollaboratorReportRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MembershipPaymentStatusReportRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteePaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserStatusReportRepository;

@ExtendWith(MockitoExtension.class)
public class DashboardSeviceTest {

	@InjectMocks
	DashboardService dashboardService = new DashboardServiceImpl();
	
	@Mock
	CollaboratorReportRepository collaboratorReportRepository;

	@Mock
	SemesterRepository semesterRepository;

	@Mock
	AttendanceStatusRepository attendanceStatusRepository;

	@Mock
	TrainingScheduleRepository trainingScheduleRepository;

	@Mock
	EventRepository eventRepository;

	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	EventService eventService;

	@Mock
	UserStatusReportRepository userStatusReportRepository;

	@Mock
	ClubFundReportRepository clubFundReportRepository;

	@Mock
	MembershipPaymentStatusReportRepository membershipPaymentStatusReportRepository;

	@Mock
	EventPaymentStatusReportRepository eventPaymentStatusReportRepository;

	@Mock
	TournamentOrganizingCommitteePaymentStatusReportRepository tournamentOrganizingCommitteePaymentStatusReportRepository;

	@Mock
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;
	
	@Mock
	ClubFundRepository clubFundRepository;
	
	private CollaboratorReport collaboratorReport() {
		CollaboratorReport collaboratorReport = new CollaboratorReport();
		collaboratorReport.setId(1);
		collaboratorReport.setNumberFemale(10);
		collaboratorReport.setNumberJoin(10);
		collaboratorReport.setNumberMale(10);
		collaboratorReport.setNumberNotPassed(10);
		collaboratorReport.setNumberPassed(10);
		collaboratorReport.setSemester(semester().getName());
		return collaboratorReport;
	}
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 05, 01));
		semester.setEndDate(LocalDate.of(2022, 9, 01));
		return semester;
	}
	
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
	
	public Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Đi Đà Lạt");
		event.setDescription("Gẹt gô");
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setMaxQuantityComitee(12);
		event.setSemester(semester().getName());
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		event.setRegistrationMemberDeadline(LocalDateTime.now().plusMonths(1));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusMonths(1));
		return event;
	}
	
	private MemberEvent memberEvent() {
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		memberEvent.setEvent(event());
		memberEvent.setRoleEvent(roleEvent());
		memberEvent.setUser(user());
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		return memberEvent;
	}
	
	private RoleEvent roleEvent() {
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		return roleEvent;
	}
	
	private UserStatusReport userStatusReport() {
		UserStatusReport userStatusReport = new UserStatusReport();
		userStatusReport.setId(1);
		userStatusReport.setSemester(semester().getName());
		userStatusReport.setNumberActiveInSemester(10);
		userStatusReport.setNumberDeactiveInSemester(10);
		userStatusReport.setTotalNumberUserInSemester(10);
		return userStatusReport;
	}
	
	private ClubFundReport clubFundReport() {
		ClubFundReport clubFundReport = new ClubFundReport();
		clubFundReport.setId(1);
		clubFundReport.setNote("Rút tiền");
		clubFundReport.setFundChange(-50000);
		clubFundReport.setFundBalance(1000000);
		clubFundReport.setCreatedOn(LocalDateTime.now());
		return clubFundReport;
	}
	
	private MembershipPaymentStatusReport membershipPaymentStatusReport()  {
		MembershipPaymentStatusReport membershipPaymentStatusReport = new MembershipPaymentStatusReport();
		membershipPaymentStatusReport.setId(1);
		membershipPaymentStatusReport.setUser(user());
		membershipPaymentStatusReport.setPaymentStatus(false);
		membershipPaymentStatusReport.setFundChange(-50000);
		membershipPaymentStatusReport.setFundBalance(1000000);
		membershipPaymentStatusReport.setMembershipInfo(membershipInfo());
		membershipPaymentStatusReport.setCreatedOn(LocalDateTime.now());
		return membershipPaymentStatusReport;
	}
	
	private MembershipInfo membershipInfo() {
		MembershipInfo membershipInfo = new MembershipInfo();
		membershipInfo.setId(1);
		membershipInfo.setSemester(semester().getName());
		membershipInfo.setAmount(50000);
		return membershipInfo;
	}
	
	private EventPaymentStatusReport eventPaymentStatusReport() {
		EventPaymentStatusReport eventPaymentStatusReport = new EventPaymentStatusReport();
		eventPaymentStatusReport.setId(1);
		eventPaymentStatusReport.setUser(user());
		eventPaymentStatusReport.setEvent(event());
		eventPaymentStatusReport.setFundChange(50000);
		eventPaymentStatusReport.setFundBalance(1000000);
		eventPaymentStatusReport.setCreatedOn(LocalDateTime.now());
		return eventPaymentStatusReport;
	}
	
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
	
	private TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport() {
		TournamentOrganizingCommitteePaymentStatusReport tournamentOrganizingCommitteePaymentStatusReport = new TournamentOrganizingCommitteePaymentStatusReport();
		tournamentOrganizingCommitteePaymentStatusReport.setId(1);
		tournamentOrganizingCommitteePaymentStatusReport.setUser(user());
		tournamentOrganizingCommitteePaymentStatusReport.setTournament(tournament());
		tournamentOrganizingCommitteePaymentStatusReport.setFundChange(50000);
		tournamentOrganizingCommitteePaymentStatusReport.setFundBalance(1000000);
		tournamentOrganizingCommitteePaymentStatusReport.setCreatedOn(LocalDateTime.now());
		return tournamentOrganizingCommitteePaymentStatusReport;
	}
	
	private TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport() {
		TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
		tournamentPlayerPaymentStatusReport.setId(1);
		tournamentPlayerPaymentStatusReport.setUser(user());
		tournamentPlayerPaymentStatusReport.setTournament(tournament());
		tournamentPlayerPaymentStatusReport.setPaymentStatus(false);
		tournamentPlayerPaymentStatusReport.setFundChange(-50000);
		tournamentPlayerPaymentStatusReport.setFundBalance(1000000);
		tournamentPlayerPaymentStatusReport.setCreatedOn(LocalDateTime.now());
		return tournamentPlayerPaymentStatusReport;
	}
	
	private ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(100000);
		return clubFund;
	}
	
	@Test
	public void getCollaboratorReportCaseSuccess() {
		when(collaboratorReportRepository.findAll()).thenReturn(Arrays.asList(collaboratorReport()));
		
		ResponseMessage responseMessage = dashboardService.getCollaboratorReport();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getCollaboratorReportCaseException() {
		when(collaboratorReportRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = dashboardService.getCollaboratorReport();
		assertEquals(responseMessage.getData(), null);
	}
	
	@Test
	public void attendanceReportCaseAttendanceStatusEqual0() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void attendanceReportCaseAttendanceStatusEqual1() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(1);
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void attendanceReportCaseTrainingScheduleIdDifferentFromAttendanceStatusTrainingScheduleId() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.getTrainingSchedule().setId(2);
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void attendanceReportCaseSemesterEmpty() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void attendanceReportCaseAttendanceStatusDateAfterLocalDateNow() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().plusDays(1));
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(attendanceStatusRepository.findAll()).thenReturn(Arrays.asList(attendanceStatus()));
		when(trainingScheduleRepository.listTrainingScheduleByTime(any(), any())).thenReturn(Arrays.asList(trainingSchedule));
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void attendanceReportCaseException() {
		when(semesterRepository.findByName(anyString())).thenReturn(null);
		
		ResponseMessage responseMessage = dashboardService.attendanceReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void eventReportCaseStartDateBeforeLocalDateNow() {
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventService.getStartDate(anyInt())).thenReturn(LocalDate.now().minusDays(1));
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		
		ResponseMessage responseMessage = dashboardService.eventReport();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void eventReportCaseStartDateAfterLocalDateNow() {
		when(eventRepository.findAll()).thenReturn(Arrays.asList(event()));
		when(eventService.getStartDate(anyInt())).thenReturn(LocalDate.now().plusDays(1));
		
		ResponseMessage responseMessage = dashboardService.eventReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void eventReportCaseException() {
		when(eventRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = dashboardService.eventReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void statusMemberReportCaseSuccess() {
		when(userStatusReportRepository.findAll()).thenReturn(Arrays.asList(userStatusReport()));
		
		ResponseMessage responseMessage = dashboardService.statusMemberReport();
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void statusMemberReportCaseEmpty() {
		when(userStatusReportRepository.findAll()).thenReturn(new ArrayList<UserStatusReport>());
		
		ResponseMessage responseMessage = dashboardService.statusMemberReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void statusMemberReportCaseException() {
		when(userStatusReportRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = dashboardService.statusMemberReport();
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void feeReportCaseSuccess() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(clubFundReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(clubFundReport()));
		when(membershipPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(membershipPaymentStatusReport()));
		when(eventPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(eventPaymentStatusReport()));
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentOrganizingCommitteePaymentStatusReport()));
		when(tournamentPlayerPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentPlayerPaymentStatusReport()));
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void feeReportCaseEndMonthEqualTo1() {
		Semester semester = semester();
		semester.setStartDate(LocalDate.of(2022, 9, 1));
		semester.setEndDate(LocalDate.of(2022, 1, 5));
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester));
		when(clubFundReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(clubFundReport()));
		when(membershipPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(membershipPaymentStatusReport()));
		when(eventPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(eventPaymentStatusReport()));
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentOrganizingCommitteePaymentStatusReport()));
		when(tournamentPlayerPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentPlayerPaymentStatusReport()));
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void feeReportCaseFundChangeOfClubFundReportGreaterThan0() {
		ClubFundReport clubFundReport = clubFundReport();
		clubFundReport.setFundChange(50000);
		
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(clubFundReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(clubFundReport));
		when(membershipPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(membershipPaymentStatusReport()));
		when(eventPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(eventPaymentStatusReport()));
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentOrganizingCommitteePaymentStatusReport()));
		when(tournamentPlayerPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(Arrays.asList(tournamentPlayerPaymentStatusReport()));
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void feeReportCaseAllListEmpty() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.of(semester()));
		when(clubFundReportRepository.findAllFundChange(any(), any())).thenReturn(new ArrayList<ClubFundReport>());
		when(membershipPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(new ArrayList<MembershipPaymentStatusReport>());
		when(eventPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(new ArrayList<EventPaymentStatusReport>());
		when(tournamentOrganizingCommitteePaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(new ArrayList<TournamentOrganizingCommitteePaymentStatusReport>());
		when(tournamentPlayerPaymentStatusReportRepository.findAllFundChange(any(), any())).thenReturn(new ArrayList<TournamentPlayerPaymentStatusReport>());
		when(clubFundRepository.findById(anyInt())).thenReturn(Optional.of(clubFund()));
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 4);
	}
	
	@Test
	public void feeReportCaseSemesterEmpty() {
		when(semesterRepository.findByName(anyString())).thenReturn(Optional.empty());
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void feeReportCaseException() {
		when(semesterRepository.findByName(anyString())).thenReturn(null);
		
		ResponseMessage responseMessage = dashboardService.feeReport(semester().getName());
		assertEquals(responseMessage.getData().size(), 0);
	}
}
