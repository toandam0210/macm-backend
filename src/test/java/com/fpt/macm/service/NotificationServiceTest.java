package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import com.fpt.macm.model.dto.UserNotificationDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.MembershipInfo;
import com.fpt.macm.model.entity.MembershipStatus;
import com.fpt.macm.model.entity.Notification;
import com.fpt.macm.model.entity.NotificationToUser;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MembershipShipInforRepository;
import com.fpt.macm.repository.MembershipStatusRepository;
import com.fpt.macm.repository.NotificationRepository;
import com.fpt.macm.repository.NotificationToUserRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

	@InjectMocks
	NotificationService notificationService = new NotificationServiceImpl();
	
	@Mock
	NotificationRepository notificationRepository;
	
	@Mock
	UserRepository userRepository;

	@Mock
	NotificationToUserRepository notificationToUserRepository;

	@Mock
	TournamentRepository tournamentRepository;

	@Mock
	MembershipStatusRepository membershipStatusRepository;

	@Mock
	MembershipShipInforRepository membershipShipInforRepository;

	@Mock
	SemesterService semesterService;

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;
	
	private Notification notification() {
		Notification notification = new Notification();
		notification.setId(1);
		notification.setMessage("Test");
		notification.setNotificationType(0);
		notification.setNotificationTypeId(1);
		notification.setCreatedOn(LocalDateTime.now());
		return notification;
	}

	private User user() {
		User user = new User();
		user.setStudentId("HE140855");
		user.setId(1);
		user.setName("dam van toan 01");
		user.setActive(true);
		Role role = new Role();
		role.setId(8);
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		return user;
	}

	private NotificationToUser notificationToUser() {
		NotificationToUser notificationToUser = new NotificationToUser();
		notificationToUser.setId(1);
		notificationToUser.setNotification(notification());
		notificationToUser.setRead(false);
		notificationToUser.setUser(user());
		notificationToUser.setCreatedOn(LocalDateTime.now());
		return notificationToUser;
	}

	private UserNotificationDto userNotificationDto() {
		UserNotificationDto userNotificationDto = new UserNotificationDto();
		userNotificationDto.setId(1);
		userNotificationDto.setMessage("Test");
		userNotificationDto.setNotificationType(0);
		userNotificationDto.setNotificationTypeId(1);
		userNotificationDto.setCreatedOn(LocalDateTime.now());
		userNotificationDto.setRead(false);
		userNotificationDto.setUserName("dam van toan 01");
		userNotificationDto.setStudentId("HE140855");
		return userNotificationDto;
	}

	private List<String> messages() {
		List<String> messages = new ArrayList<String>();
		messages.add("Membership kỳ Summer2022: 50000VND");
		messages.add("Membership kỳ Summer2022: 100000VND");
		messages.add("Membership kỳ Summer2022: 200000VND");
		return messages;
	}
	
	public Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 8, 31));
		return semester;
	}
	
	public MembershipInfo membershipInfo() {
		MembershipInfo membershipInfo = new MembershipInfo();
		membershipInfo.setId(1);
		membershipInfo.setSemester(semester().getName());
		membershipInfo.setAmount(100000);
		return membershipInfo;
	}
	
	public MembershipStatus membershipStatus() {
		MembershipStatus membershipStatus = new MembershipStatus();
		membershipStatus.setId(1);
		membershipStatus.setMembershipInfo(membershipInfo());
		membershipStatus.setStatus(false);
		membershipStatus.setUser(user());
		return membershipStatus;
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
		event.setRegistrationMemberDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.of(2022, 10, 30, 0, 0));
		return event;
	}
	
	@Test
	public void getAllNotificationByStudentIdCaseSuccess() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(notificationToUserRepository.findByUserId(anyInt(), any())).thenReturn(new PageImpl<>(Arrays.asList(notificationToUser())));
		when(notificationToUserRepository.findAllUnreadNotificationByUser(anyInt())).thenReturn(Arrays.asList(notificationToUser()));
		
		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void getAllNotificationByStudentIdCaseEmpty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(notificationToUserRepository.findByUserId(anyInt(), any())).thenReturn(null);
		when(notificationToUserRepository.findAllUnreadNotificationByUser(anyInt())).thenReturn(Arrays.asList(notificationToUser()));
		
		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void getAllNotificationByStudentIdCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		
		ResponseMessage responseMessage = notificationService.getAllNotificationByStudentId("HE140855", 0, 1000, "id");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void sendNotificationToAllUserCaseSuccess() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(user()));
		
		ResponseMessage responseMessage = notificationService.sendNotificationToAllUser(notification());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void sendNotificationToAllUserCaseException() {
		when(userRepository.findAll()).thenReturn(null);
		
		ResponseMessage responseMessage = notificationService.sendNotificationToAllUser(notification());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void sendNotificationToAnUserCaseSuccess() {
		ResponseMessage responseMessage = notificationService.sendNotificationToAnUser(user(), notification());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void checkPaymentStatusCaseSuccess() {
		ResponseMessage currentSemeseter = new ResponseMessage();
		currentSemeseter.setData(Arrays.asList(semester()));
		
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user()));
		when(semesterService.getCurrentSemester()).thenReturn(currentSemeseter);
		when(membershipShipInforRepository.findBySemester(anyString())).thenReturn(Optional.of(membershipInfo()));
		when(membershipStatusRepository.findByMemberShipInfoIdAndUserId(anyInt(), anyInt())).thenReturn(Optional.of(membershipStatus()));
		when(memberEventRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(memberEvent()));
		when(tournamentOrganizingCommitteeRepository.findByUserId(anyInt())).thenReturn(Arrays.asList(null));
	}
}
