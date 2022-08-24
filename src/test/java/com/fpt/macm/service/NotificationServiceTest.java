//package com.fpt.macm.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyBoolean;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Optional;
//import java.util.Set;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Sort;
//
//import com.fpt.macm.constant.Constant;
//import com.fpt.macm.model.entity.CompetitiveType;
//import com.fpt.macm.model.entity.Event;
//import com.fpt.macm.model.entity.ExhibitionPlayer;
//import com.fpt.macm.model.entity.ExhibitionTeam;
//import com.fpt.macm.model.entity.ExhibitionType;
//import com.fpt.macm.model.entity.MemberEvent;
//import com.fpt.macm.model.entity.MembershipInfo;
//import com.fpt.macm.model.entity.MembershipStatus;
//import com.fpt.macm.model.entity.Notification;
//import com.fpt.macm.model.entity.NotificationToUser;
//import com.fpt.macm.model.entity.Role;
//import com.fpt.macm.model.entity.RoleEvent;
//import com.fpt.macm.model.entity.Semester;
//import com.fpt.macm.model.entity.Tournament;
//import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
//import com.fpt.macm.model.entity.TournamentPlayer;
//import com.fpt.macm.model.entity.TournamentRole;
//import com.fpt.macm.model.entity.User;
//import com.fpt.macm.model.response.ResponseMessage;
//import com.fpt.macm.repository.MemberEventRepository;
//import com.fpt.macm.repository.MembershipShipInforRepository;
//import com.fpt.macm.repository.MembershipStatusRepository;
//import com.fpt.macm.repository.NotificationRepository;
//import com.fpt.macm.repository.NotificationToUserRepository;
//import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
//import com.fpt.macm.repository.TournamentRepository;
//import com.fpt.macm.repository.UserRepository;
//
//@ExtendWith(MockitoExtension.class)
//public class NotificationServiceTest {
//
//	@InjectMocks
//	NotificationService notificationService = new NotificationServiceImpl();
//
//	@Mock
//	NotificationRepository notificationRepository;
//
//	@Mock
//	UserRepository userRepository;
//
//	@Mock
//	NotificationToUserRepository notificationToUserRepository;
//
//	@Mock
//	TournamentRepository tournamentRepository;
//
//	@Mock
//	MembershipStatusRepository membershipStatusRepository;
//
//	@Mock
//	MembershipShipInforRepository membershipShipInforRepository;
//
//	@Mock
//	SemesterService semesterService;
//
//	@Mock
//	MemberEventRepository memberEventRepository;
//
//	@Mock
//	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;
//
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
//	private User user() {
//		User user = new User();
//		user.setStudentId("HE140855");
//		user.setId(1);
//		user.setName("dam van toan 01");
//		user.setActive(true);
//		Role role = new Role();
//		role.setId(8);
//		user.setRole(role);
//		user.setCreatedOn(LocalDate.now());
//		user.setCreatedBy("toandv");
//		return user;
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
//
//	public Semester semester() {
//		Semester semester = new Semester();
//		semester.setId(1);
//		semester.setName("Summer2022");
//		semester.setStartDate(LocalDate.of(2022, 5, 1));
//		semester.setEndDate(LocalDate.of(2022, 8, 31));
//		return semester;
//	}
//
//	public MembershipInfo membershipInfo() {
//		MembershipInfo membershipInfo = new MembershipInfo();
//		membershipInfo.setId(1);
//		membershipInfo.setSemester(semester().getName());
//		membershipInfo.setAmount(100000);
//		return membershipInfo;
//	}
//
//	public MembershipStatus membershipStatus() {
//		MembershipStatus membershipStatus = new MembershipStatus();
//		membershipStatus.setId(1);
//		membershipStatus.setMembershipInfo(membershipInfo());
//		membershipStatus.setStatus(false);
//		membershipStatus.setUser(user());
//		return membershipStatus;
//	}
//
//	private MemberEvent memberEvent() {
//		MemberEvent memberEvent = new MemberEvent();
//		memberEvent.setId(1);
//		memberEvent.setRegisterStatus(Constant.REQUEST_STATUS_APPROVED);
//		memberEvent.setEvent(event());
//		memberEvent.setRoleEvent(roleEvent());
//		memberEvent.setUser(user());
//		memberEvent.setPaidBeforeClosing(false);
//		memberEvent.setPaymentValue(0);
//		return memberEvent;
//	}
//
//	private RoleEvent roleEvent() {
//		RoleEvent roleEvent = new RoleEvent();
//		roleEvent.setId(1);
//		roleEvent.setName("ROLE_Member");
//		return roleEvent;
//	}
//
//	public Event event() {
//		Event event = new Event();
//		event.setId(1);
//		event.setName("Đi Đà Lạt");
//		event.setDescription("Gẹt gô");
//		event.setAmountFromClub(0);
//		event.setAmountPerRegisterActual(0);
//		event.setAmountPerRegisterEstimated(50000);
//		event.setSemester(semester().getName());
//		event.setTotalAmountActual(0);
//		event.setTotalAmountEstimated(100000);
//		event.setRegistrationMemberDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
//		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
//		event.setStatus(true);
//		return event;
//	}
//
//	private Tournament tournament() {
//		Tournament tournament = new Tournament();
//		tournament.setCompetitiveTypes(competitiveTypes());
//		tournament.setDescription("abc");
//		tournament.setExhibitionTypes(exhibitionTypes());
//		tournament.setFeeOrganizingCommiteePay(100000);
//		tournament.setFeePlayerPay(100000);
//		tournament.setId(1);
//		tournament.setName("FNC");
//		tournament.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
//		tournament.setRegistrationPlayerDeadline(LocalDateTime.of(2022, 8, 1, 18, 0));
//		tournament.setSemester("Summer2022");
//		tournament.setStatus(true);
//		tournament.setTournamentPlayers(tournamentPlayers());
//		return tournament;
//	}
//
//	private Set<CompetitiveType> competitiveTypes() {
//		Set<CompetitiveType> competitiveTypes = new HashSet<CompetitiveType>();
//		CompetitiveType competitiveType = new CompetitiveType();
//		competitiveType.setId(1);
//		competitiveType.setGender(true);
//		competitiveType.setWeightMax(60);
//		competitiveType.setWeightMin(57);
//		competitiveTypes.add(competitiveType);
//		return competitiveTypes;
//	}
//
//	private Set<ExhibitionType> exhibitionTypes() {
//		Set<ExhibitionType> exhibitionTypes = new HashSet<ExhibitionType>();
//		ExhibitionType exhibitionType = new ExhibitionType();
//		exhibitionType.setExhibitionTeams(exhibitionTeams());
//		exhibitionType.setId(1);
//		exhibitionType.setName("Long ho quyen");
//		exhibitionType.setNumberFemale(0);
//		exhibitionType.setNumberMale(1);
//		exhibitionTypes.add(exhibitionType);
//		return exhibitionTypes;
//	}
//
//	private Set<ExhibitionTeam> exhibitionTeams() {
//		Set<ExhibitionTeam> exhibitionTeams = new HashSet<ExhibitionTeam>();
//		ExhibitionTeam exhibitionTeam = new ExhibitionTeam();
//		exhibitionTeam.setExhibitionPlayers(exhibitionPlayers());
//		exhibitionTeam.setId(1);
//		exhibitionTeam.setTeamName("Team 1");
//		exhibitionTeams.add(exhibitionTeam);
//		return exhibitionTeams;
//	}
//
//	private Set<ExhibitionPlayer> exhibitionPlayers() {
//		Set<ExhibitionPlayer> exhibitionPlayers = new HashSet<ExhibitionPlayer>();
//		ExhibitionPlayer exhibitionPlayer = new ExhibitionPlayer();
//		exhibitionPlayer.setId(1);
//		exhibitionPlayer.setRoleInTeam(true);
//		exhibitionPlayer.setTournamentPlayer(tournamentPlayer());
//		exhibitionPlayers.add(exhibitionPlayer);
//		return exhibitionPlayers;
//	}
//
//	private TournamentPlayer tournamentPlayer() {
//		TournamentPlayer tournamentPlayer = new TournamentPlayer();
//		tournamentPlayer.setId(1);
//		tournamentPlayer.setPaymentStatus(true);
//		tournamentPlayer.setUser(user());
//		return tournamentPlayer;
//	}
//
//	private Set<TournamentPlayer> tournamentPlayers() {
//		Set<TournamentPlayer> tournamentPlayers = new HashSet<TournamentPlayer>();
//		TournamentPlayer tournamentPlayer = new TournamentPlayer();
//		tournamentPlayer.setId(1);
//		tournamentPlayer.setPaymentStatus(true);
//		tournamentPlayer.setUser(user());
//		tournamentPlayers.add(tournamentPlayer);
//		return tournamentPlayers;
//	}
//
//	private TournamentOrganizingCommittee tournamentOrganizingCommittee() {
//		TournamentOrganizingCommittee tournamentOrganizingCommittee = new TournamentOrganizingCommittee();
//		tournamentOrganizingCommittee.setId(1);
//		tournamentOrganizingCommittee.setPaymentStatus(false);
//		TournamentRole tournamentRole = new TournamentRole();
//		tournamentRole.setId(1);
//		tournamentRole.setName("Ban truyen thong");
//		tournamentOrganizingCommittee.setTournamentRole(tournamentRole);
//		tournamentOrganizingCommittee.setTournament(tournament());
//		tournamentOrganizingCommittee.setUser(user());
//		tournamentOrganizingCommittee.setCreatedBy("toandv");
//		tournamentOrganizingCommittee.setCreatedOn(LocalDateTime.now());
//		tournamentOrganizingCommittee.setUpdatedBy("toandv");
//		tournamentOrganizingCommittee.setUpdatedOn(LocalDateTime.now());
//		return tournamentOrganizingCommittee;
//	}
//
//	@Test
//	public void getAllNotificationByStudentIdCaseSuccess() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserId(anyInt(), any()))
//				.thenReturn(new PageImpl<>(Arrays.asList(notificationToUser())));
//		when(notificationToUserRepository.findAllUnreadNotificationByUser(anyInt()))
//				.thenReturn(Arrays.asList(notificationToUser()));
//
//		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void getAllNotificationByStudentIdCaseEmpty() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserId(anyInt(), any())).thenReturn(null);
//		when(notificationToUserRepository.findAllUnreadNotificationByUser(anyInt()))
//				.thenReturn(Arrays.asList(notificationToUser()));
//
//		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void getAllNotificationByStudentIdCaseException() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void sendNotificationToAllUserCaseSuccess() {
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.sendNotificationToAllUser(notification());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void sendNotificationToAllUserCaseException() {
//		when(userRepository.findAll()).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.sendNotificationToAllUser(notification());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void sendNotificationToAnUserCaseSuccess() {
//		ResponseMessage responseMessage = notificationService.sendNotificationToAnUser(user(), notification());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseMemberShipHasNotPaid() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.of(membershipInfo()));
//		when(membershipStatusRepository.findByMemberShipInfoIdAndUserId(anyInt(), anyInt()))
//				.thenReturn(Optional.of(membershipStatus()));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseMemberShipHasPaid() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MembershipStatus membershipStatus = membershipStatus();
//		membershipStatus.setStatus(true);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.of(membershipInfo()));
//		when(membershipStatusRepository.findByMemberShipInfoIdAndUserId(anyInt(), anyInt()))
//				.thenReturn(Optional.of(membershipStatus));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseMemberShipInfoEmpty() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MembershipStatus membershipStatus = membershipStatus();
//		membershipStatus.setStatus(true);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.empty());
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessHaveNotPaidBeforeEventClose() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessHavePaidBeforeEventClose() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.setPaymentValue(50000);
//		;
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseNoFeeEvent() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterEstimated(0);
//		memberEvent.getEvent().setTotalAmountEstimated(0);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseHaveNotPaidEventClosed() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(100000);
//		memberEvent.getEvent().setTotalAmountActual(200000);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseHaveNotPaidEnoughEventClosed() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(100000);
//		memberEvent.getEvent().setTotalAmountActual(200000);
//		memberEvent.setPaidBeforeClosing(true);
//		memberEvent.setPaymentValue(50000);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseHaveNoPaidMoreEventClosed() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(50000);
//		memberEvent.getEvent().setTotalAmountActual(100000);
//		memberEvent.setPaidBeforeClosing(true);
//		memberEvent.setPaymentValue(50000);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseException1() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		MemberEvent memberEvent = memberEvent();
//		memberEvent.getEvent().setAmountPerRegisterActual(100000);
//		memberEvent.getEvent().setTotalAmountActual(200000);
//		memberEvent.setPaidBeforeClosing(true);
//		memberEvent.setPaymentValue(45000);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseHaveNotPaidOrganizingCommitteeTournament() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(tournamentOrganizingCommitteeRepository.findByUserId(anyInt()))
//				.thenReturn(Arrays.asList(tournamentOrganizingCommittee()));
//		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(tournament()));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseHavePaidOrganizingCommitteeTournament() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//		TournamentOrganizingCommittee tournamentOrganizingCommittee = tournamentOrganizingCommittee();
//		tournamentOrganizingCommittee.setPaymentStatus(true);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(tournamentOrganizingCommitteeRepository.findByUserId(anyInt()))
//				.thenReturn(Arrays.asList(tournamentOrganizingCommittee));
//		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(tournament()));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseHavePaidTournamentPlayer() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(tournament()));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseHaveNotPaidTournamentPlayer() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		Tournament tournament = tournament();
//		for (TournamentPlayer tournamentPlayer : tournament.getTournamentPlayers()) {
//			tournamentPlayer.setPaymentStatus(false);
//		}
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(tournament));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseNotJoinTournamentPlayer() {
//		ResponseMessage currentSemeseter = new ResponseMessage();
//		currentSemeseter.setData(Arrays.asList(semester()));
//
//		Tournament tournament = tournament();
//		for (TournamentPlayer tournamentPlayer : tournament.getTournamentPlayers()) {
//			tournamentPlayer.getUser().setStudentId("HE140000");
//		}
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
//		when(tournamentRepository.findAll()).thenReturn(Arrays.asList(tournament));
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void checkPaymentStatusCaseSuccessCaseException2() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.checkPaymentStatus("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void markNotificationAsReadCaseSuccess() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserIdAndNotificationId(anyInt(), anyInt()))
//				.thenReturn(Optional.of(notificationToUser()));
//
//		ResponseMessage responseMessage = notificationService.markNotificationAsRead(1, "HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void markNotificationAsReadCaseNotificationEmpty() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserIdAndNotificationId(anyInt(), anyInt()))
//				.thenReturn(Optional.empty());
//
//		ResponseMessage responseMessage = notificationService.markNotificationAsRead(1, "HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void markNotificationAsReadCaseException() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.markNotificationAsRead(1, "HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void markAllNotificationAsReadCaseSuccess() {
//		NotificationToUser notificationToUser = notificationToUser();
//		notificationToUser.setRead(false);
//
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findAllUnreadNotificationByUser(anyInt()))
//				.thenReturn(Arrays.asList(notificationToUser));
//
//		ResponseMessage responseMessage = notificationService.markAllNotificationAsRead("HE140855");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void markAllNotificationAsReadCaseException() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.markAllNotificationAsRead("HE140855");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createTournamentNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createTournamentCreateNotification(1, "FNC");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createTournamentNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createTournamentCreateNotification(1, "FNC");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createEventNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createEventCreateNotification(1, "Test");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createEventNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createEventCreateNotification(1, "Test");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void createEventDeleteNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createEventDeleteNotification(1, "Test");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createEventDeleteNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createEventDeleteNotification(1, "Test");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createTrainingSessionCreateNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionCreateNotification(LocalDate.now());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createTrainingSessionCreateNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionCreateNotification(LocalDate.now());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createTrainingSessionUpdateNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionUpdateNotification(LocalDate.now(),
//				LocalTime.now(), LocalTime.now().plusHours(1));
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createTrainingSessionUpdateNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionUpdateNotification(LocalDate.now(),
//				LocalTime.now(), LocalTime.now().plusHours(1));
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createTrainingSessionDeleteNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionDeleteNotification(LocalDate.now());
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//
//	@Test
//	public void createTrainingSessionDeleteNotificationCaseException() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createTrainingSessionDeleteNotification(LocalDate.now());
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//
//	@Test
//	public void createTournamentDeleteNotificationCaseSuccess() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(Arrays.asList(notification()));
//		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
//
//		ResponseMessage responseMessage = notificationService.createTournamentDeleteNotification(1, "");
//		assertEquals(responseMessage.getData().size(), 1);
//
//	}
//	
//	@Test
//	public void createTournamentDeleteNotificationCaseExceptiop() {
//		when(notificationRepository.findAll(any(Sort.class))).thenReturn(null);
//
//		ResponseMessage responseMessage = notificationService.createTournamentDeleteNotification(1, "");
//		assertEquals(responseMessage.getData().size(), 0);
//
//	}
//
//	@Test
//	public void getAllUnreadNotificationByStudentIdCaseSuccess() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserIdAndIsRead(anyInt(), anyBoolean(), any())).thenReturn(new PageImpl<>(Arrays.asList(notificationToUser())));
//		
//		ResponseMessage responseMessage = notificationService.getAllUnreadNotificationByStudentId(user().getStudentId(), 0, 10, "id");
//		assertEquals(responseMessage.getData().size(), 1);
//	}
//	
//	@Test
//	public void getAllUnreadNotificationByStudentIdCaseEmpty() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserIdAndIsRead(anyInt(), anyBoolean(), any())).thenReturn(new PageImpl<>(new ArrayList<NotificationToUser>()));
//		
//		ResponseMessage responseMessage = notificationService.getAllUnreadNotificationByStudentId(user().getStudentId(), 0, 10, "id");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void getAllUnreadNotificationByStudentIdCaseNull() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
//		when(notificationToUserRepository.findByUserIdAndIsRead(anyInt(), anyBoolean(), any())).thenReturn(null);
//		
//		ResponseMessage responseMessage = notificationService.getAllUnreadNotificationByStudentId(user().getStudentId(), 0, 10, "id");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//	@Test
//	public void getAllUnreadNotificationByStudentIdCaseException() {
//		when(userRepository.findByStudentId(anyString())).thenReturn(null);
//		
//		ResponseMessage responseMessage = notificationService.getAllUnreadNotificationByStudentId(user().getStudentId(), 0, 10, "id");
//		assertEquals(responseMessage.getData().size(), 0);
//	}
//	
//}
