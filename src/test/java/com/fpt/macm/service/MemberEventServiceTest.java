package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.fpt.macm.model.dto.RoleEventDto;
import com.fpt.macm.model.dto.UserEventDto;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.EventRepository;
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
	EventRepository eventRepository;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleEventRepository roleEventRepository;
	

	public Optional<Event> createEvent() {
		LocalDateTime dateTimeRegistrationDeadline = LocalDateTime.of(2022, 7, 29, 0, 0);
		Event event = new Event();
		event.setId(8);
		event.setAmountFromClub(0);
		event.setAmountPerRegisterActual(0);
		event.setAmountPerRegisterEstimated(0);
		event.setCreatedBy("LinhLHN");
		event.setCreatedOn(LocalDateTime.now());
		event.setDescription("Gẹt gô");
		event.setMaxQuantityComitee(12);
		event.setName("Đi Đà Lạt");
		event.setSemester("Summer2022");
		event.setTotalAmountActual(0);
		event.setTotalAmountEstimated(0);
		event.setUpdatedBy("LinhLHN");
		event.setUpdatedOn(LocalDateTime.now());
		event.setRegistrationMemberDeadline(dateTimeRegistrationDeadline);
		event.setRegistrationOrganizingCommitteeDeadline(dateTimeRegistrationDeadline);
		Optional<Event> eventOp = Optional.of(event);
		return eventOp;
	}

	public UserEventDto createUserEventDto() {
		UserEventDto userEventDto = new UserEventDto();
		userEventDto.setEventId(8);
		userEventDto.setEventName("Đi Đà Lạt");
		userEventDto.setUserName("dam van toan 06");
		userEventDto.setUserStudentId("HE140860");
		RoleEventDto roleEventDto = new RoleEventDto();
		roleEventDto.setId(1);
		roleEventDto.setName("ROLE_Member");
		userEventDto.setRoleEventDto(roleEventDto);
		return userEventDto;
	}
	
	private Optional<User> createUser() {
		User user = new User();
		user.setStudentId("HE140860");
		user.setId(15);
		user.setName("dam van toan 06");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 07, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140860@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102005");
		user.setActive(true);
		user.setCurrentAddress("Dom C");
		Role role = new Role();
		role.setId(8);
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		Optional<User> userOp = Optional.of(user);
		return userOp;
	}
	
	private List<MemberEvent> createListMemberEvent(){
		List<MemberEvent> membersEvent = new ArrayList<>();
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setCreatedBy("toandv");
		memberEvent.setCreatedOn(LocalDateTime.now());
		Event event = new Event();
		event.setId(8);
		memberEvent.setEvent(event);
		memberEvent.setPaidBeforeClosing(false);
		memberEvent.setPaymentValue(0);
		memberEvent.setRegisterStatus(false);
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		memberEvent.setRoleEvent(roleEvent);
		memberEvent.setUpdatedBy("toandv");
		memberEvent.setUpdatedOn(LocalDateTime.now());
		User user = new User();
		user.setId(1);
		memberEvent.setUser(user);
		membersEvent.add(memberEvent);
		return membersEvent;
	}
	
	private Optional<RoleEvent> createRoleEvent(){
		RoleEvent roleEvent = new RoleEvent();
		roleEvent.setId(1);
		roleEvent.setName("ROLE_Member");
		Optional<RoleEvent> roleEventOp = Optional.of(roleEvent);
		return roleEventOp;
	}

	@Test
	void registerToJoinEventSuccess() throws Exception {
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Đăng ký tham gia sự kiện thành công");
		responseMessage.setData(Arrays.asList(createUserEventDto()));
		
		when(eventRepository.findById(anyInt())).thenReturn(createEvent());
		when(userRepository.findByStudentId(anyString())).thenReturn(createUser());
		when(memberEventRepository.findByEventIdOrderByIdAsc(anyInt())).thenReturn(createListMemberEvent());
		when(roleEventRepository.findMemberRole()).thenReturn(createRoleEvent());
		
		ResponseMessage returnResponseMessage = memberEventService.registerToJoinEvent(8, "HE140860");
		assertEquals(returnResponseMessage.getData().size(), 1);
	}

}
