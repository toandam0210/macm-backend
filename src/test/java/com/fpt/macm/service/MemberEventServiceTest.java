package com.fpt.macm.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fpt.macm.model.dto.MemberEventDto;
import com.fpt.macm.model.dto.MemberNotJoinEventDto;
import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventRole;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.EventPaymentStatusReportRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventRoleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.RoleEventRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class MemberEventServiceTest {

	@InjectMocks
	MemberEventService memberEventService = new MemberEventServiceImpl();

	@Mock
	MemberEventRepository memberEventRepository;

	@Mock
	ClubFundRepository clubFundRepository;

	@Mock
	EventPaymentStatusReportRepository eventPaymentStatusReportRepository;

	@Mock
	EventRepository eventRepository;

	@Mock
	RoleEventRepository roleEventRepository;

	@Mock
	UserRepository userRepository;
	
	@Mock
	AttendanceEventRepository attendanceEventRepository;
	
	@Mock
	EventRoleRepository eventRoleRepository;
	
	private User user() {
		User user = new User();
		user.setId(1);
		user.setStudentId("HE140000");
		user.setName("Nguyen Van A");
		user.setActive(true);
		Role role = new Role();
		role.setId(8);
		user.setRole(role);
		return user;
	}

	public Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Sự kiện 1");
		event.setDescription("ABC");
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(50000);
		event.setSemester("Summer2022");
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(100000);
		event.setRegistrationMemberDeadline(LocalDateTime.now().plusMonths(1));
		event.setRegistrationOrganizingCommitteeDeadline(LocalDateTime.now().plusMonths(1));
		event.setStatus(true);
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
		roleEvent.setName("Thành viên tham gia");
		return roleEvent;
	}
	
	private MemberEventDto memberEventDto(){
		MemberEventDto memberEventDto = new MemberEventDto();
		memberEventDto.setId(memberEvent().getId());
		memberEventDto.setRoleEventDto(roleEventDto());
		return memberEventDto;
	}

	private RoleEventDto roleEventDto() {
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(roleEvent().getId());
		roleEventDto.setName(roleEvent().getName());
		roleEventDto.setMaxQuantity(eventRole().getQuantity());
		roleEventDto.setAvailableQuantity(10);
		return roleEventDto;
	}
	
	private EventRole eventRole() {
		EventRole eventRole = new EventRole();
		eventRole.setId(1);
		eventRole.setEvent(event());
		eventRole.setQuantity(10);
		eventRole.setRoleEvent(roleEvent());
		return eventRole;
	}
	
	private ClubFund clubFund() {
		ClubFund clubFund = new ClubFund();
		clubFund.setId(1);
		clubFund.setFundAmount(100000000);
		return clubFund;
	}
	
	private MemberNotJoinEventDto memberNotJoinEventDto() {
		MemberNotJoinEventDto memberNotJoinEventDto = new MemberNotJoinEventDto();
		memberNotJoinEventDto.setRegisteredStatus(true);
		memberNotJoinEventDto.setRoleEventDto(roleEventDto());
		memberNotJoinEventDto.setUserId(user().getId());
		memberNotJoinEventDto.setUserMail(user().getEmail());
		memberNotJoinEventDto.setUserName(user().getName());
		memberNotJoinEventDto.setUserStudentId(user().getStudentId());
		memberNotJoinEventDto.setRoleInClub(user().getRole().getName());
		return memberNotJoinEventDto;
	}
	
	@Test
	private void updateListMemberEventRoleCaseSuccess() {
		when(memberEventRepository.findById(anyInt())).thenReturn(Optional.of(memberEvent()));
		
		ResponseMessage responseMessage = memberEventService.updateListMemberEventRole(Arrays.asList(memberEventDto()));
		assertEquals(responseMessage.getData().size(), 1);
	}

}
