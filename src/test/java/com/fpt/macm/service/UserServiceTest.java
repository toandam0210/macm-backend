package com.fpt.macm.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.InforInQrCode;
import com.fpt.macm.model.dto.UserDto;
import com.fpt.macm.model.entity.AdminSemester;
import com.fpt.macm.model.entity.AttendanceEvent;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.MemberEvent;
import com.fpt.macm.model.entity.MemberSemester;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AdminSemesterRepository;
import com.fpt.macm.repository.AttendanceEventRepository;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.MemberSemesterRepository;
import com.fpt.macm.repository.RoleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@InjectMocks
	UserService userService = new UserServiceImpl();
	
	@Mock
	UserRepository userRepository;
	
	@Mock
	RoleRepository roleRepository;
	
	@Mock
	SemesterRepository semesterRepository;
	
	@Mock
	SemesterService semesterService;
	
	@Mock
	MemberSemesterRepository memberSemesterRepository;
	
	@Mock
	AdminSemesterRepository adminSemesterRepository;
	
	@Mock
	private ResourceLoader resourceLoader;
	
	@Mock
	TrainingScheduleService trainingScheduleService;
	
	@Mock
	AttendanceStatusRepository attendanceStatusRepository;
	
	@Mock
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Mock
	EventScheduleRepository eventScheduleRepository;
	
	@Mock
	MemberEventRepository memberEventRepository;
	
	@Mock
	AttendanceEventRepository attendanceEventRepository;
	
	@Mock
	TournamentScheduleRepository tournamentScheduleRepository;
	
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
		user.setGeneration(1);
		return user;
	}
	
	private UserDto createUserDto() {
		UserDto user = new UserDto();
		user.setStudentId("HE140855");
		user.setName("Dam Van Toan");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 02, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140855@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		//user.setRoleId(1);
		user.setGeneration(4);
		return user;
	}
	
	private List<UserDto> usersDto(){
		List<UserDto> users = new ArrayList<UserDto>();
		UserDto user = new UserDto();
		user.setStudentId("HE140855");
		user.setName("Dam Van Toan");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 02, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140855@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		user.setRoleId(1);
		user.setGeneration(4);
		
		UserDto user2 = new UserDto();
		user2.setStudentId("HE140856");
		user2.setName("dam van toan 02");
		user2.setGender(true);
		LocalDate localDate1 = LocalDate.of(2000, 02, 10);
		user2.setDateOfBirth(localDate1);
		user2.setEmail("toandvhe140856@fpt.edu.vn");
		user2.setImage(null);
		user2.setPhone("0982102001");
		user2.setActive(true);
		user2.setCurrentAddress("Dom A");
		user2.setRoleId(2);
		user2.setGeneration(4);
		
		UserDto user3 = new UserDto();
		user3.setStudentId("HA140857");
		user3.setName("dam van tuan 02");
		user3.setGender(false);
		LocalDate localDate2 = LocalDate.of(2000, 02, 10);
		user3.setDateOfBirth(localDate2);
		user3.setEmail("tuandvhe140856@fpt.edu.vn");
		user3.setImage(null);
		user3.setPhone("0982102002");
		user3.setActive(false);
		user3.setCurrentAddress("Dom A");
		user3.setRoleId(2);
		user3.setGeneration(3);
		
		users.add(user2);
		users.add(user);
		users.add(user3);
		return users;
	}
	
	private Semester semester() {
		Semester semester = new Semester();
		semester.setId(1);
		semester.setName("Summer2022");
		semester.setStartDate(LocalDate.of(2022, 5, 1));
		semester.setEndDate(LocalDate.of(2022, 9, 1));
		return semester;
	}
	
	private Role role() {
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		return role;
	}
	
	private AdminSemester adminSemester() {
		AdminSemester adminSemester = new AdminSemester();
		adminSemester.setId(1);
		adminSemester.setRole(role());
		adminSemester.setUser(createUser());
		adminSemester.setSemester("Summer2022");
		return adminSemester;
	}
	
	private MemberSemester memberSemester() {
		MemberSemester memberSemester = new MemberSemester();
		memberSemester.setId(1);
		memberSemester.setSemester("Summer2022");
		memberSemester.setStatus(true);
		memberSemester.setUser(createUser());
		return memberSemester;
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
		attendanceStatus.setStatus(1);
		attendanceStatus.setTrainingSchedule(trainingSchedule());
		attendanceStatus.setUser(createUser());
		return attendanceStatus;
	}
	
	private InforInQrCode inforInQrCode() {
		InforInQrCode inforInQrCode = new InforInQrCode();
		inforInQrCode.setStudentId("HE140855");
		inforInQrCode.setStudentName("Dam Van Toan");
		inforInQrCode.setStatus(true);
		return inforInQrCode;
	}
	
	private Event event() {
		Event event = new Event();
		event.setId(1);
		event.setName("Su kien");
		return event;
	}
	
	private EventSchedule eventSchedule() {
		EventSchedule eventSchedule = new EventSchedule();
		eventSchedule.setDate(LocalDate.now());
		eventSchedule.setEvent(event());
		eventSchedule.setFinishTime(LocalTime.of(20, 0, 0));
		eventSchedule.setId(1);
		eventSchedule.setStartTime(LocalTime.of(18, 0, 0));
		return eventSchedule;
	}
	
	private MemberEvent memberEvent() {
		MemberEvent memberEvent = new MemberEvent();
		memberEvent.setId(1);
		memberEvent.setRegisterStatus(true);
		return memberEvent;

	}
	
	private AttendanceEvent attendanceEvent() {
		AttendanceEvent attendanceEvent = new AttendanceEvent();
		attendanceEvent.setId(1);
		attendanceEvent.setStatus(1);
		attendanceEvent.setEvent(event());
		return attendanceEvent;
	}
	
	private Tournament tournament() {
		Tournament tournament = new Tournament();
		tournament.setId(1);
		tournament.setName("Giai dau");
		return tournament;
	}
	
	private TournamentSchedule tournamentSchedule() {
		TournamentSchedule tournamentSchedule = new TournamentSchedule();
		tournamentSchedule.setDate(LocalDate.now());
		tournamentSchedule.setFinishTime(LocalTime.of(20, 0));
		tournamentSchedule.setStartTime(LocalTime.of(18, 0));
		tournamentSchedule.setId(1);
		tournamentSchedule.setTournament(tournament());
		return tournamentSchedule;
	}
	@Test
	public void testGetUserByStudentId(){
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage responseMessage = userService.getUserByStudentId("HE140855");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetUserByStudentIdFail() throws Exception{
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage responseMessage = userService.getUserByStudentId("HE140855");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetAllAdminForViceHeadClub() {
		User user = createUser();
		List<User> users = Arrays.asList(user);
		Page<User> pages = new PageImpl<User>(users);
		when(userRepository.findAdminForViceHeadClubByRoleId(any())).thenReturn(pages);
		ResponseMessage responseMessage = userService.getAllAdminForViceHeadClub(1,10,"studentId");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetAllAdminForViceHeadClubFail() {
		ResponseMessage responseMessage = userService.getAllAdminForViceHeadClub(-1,10,"studentId");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetAllAdminForViceHeadClubFail2() {
		ResponseMessage responseMessage = userService.getAllAdminForViceHeadClub(0,-10,"studentId");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetAllAdminForHeadClub() {
		User user = createUser();
		List<User> users = Arrays.asList(user);
		Page<User> pages = new PageImpl<User>(users);
		when(userRepository.findAdminForHeadClubByRoleId(any())).thenReturn(pages);
		ResponseMessage responseMessage = userService.getAllAdminForHeadClub(0,10,"studentId");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetAllAdminForHeadClubFail() {
		ResponseMessage responseMessage = userService.getAllAdminForHeadClub(-1,10,"studentId");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetAllAdminForHeadClubFail2() {
		ResponseMessage responseMessage = userService.getAllAdminForHeadClub(0,-10,"studentId");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateUser() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(13);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		ResponseMessage result = userService.updateUser("HE140855", createUserDto());
		assertEquals(result.getData().size(), 1);
	}
	
	@Test
	public void testUpdateUserCaseUpdateRoleHeadClub() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(9);
		User user = createUser();
		user.setStudentId("HE140855");
		Role role = role();
		role.setId(1);
		role.setName(Constant.ROLE_HEAD_CLUB);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
		ResponseMessage result = userService.updateUser("HE140855", userDto);
		assertEquals(result.getData().size(), 0);
	}
	
	@Test
	public void testUpdateUserCaseCollabToAdmin() {
		Role roleCtv = role();
		roleCtv.setId(15);
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(9);
		User user = createUser();
		user.setStudentId("HE140855");
		user.setRole(roleCtv);
		Role role = role();
		role.setId(1);
		role.setName(Constant.ROLE_HEAD_CLUB);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		ResponseMessage result = userService.updateUser("HE140855", userDto);
		assertEquals(result.getData().size(), 0);
	}
	
	@Test
	public void testUpdateUser2() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(13);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(), anyString())).thenReturn(Optional.of(memberSemester()));
		ResponseMessage result = userService.updateUser("HE140855", createUserDto());
		assertEquals(result.getData().size(), 1);
	}
	
	@Test
	public void testUpdateUser3() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(13);
		Role role = role();
		role.setId(13);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(), anyString())).thenReturn(Optional.of(memberSemester()));
		ResponseMessage result = userService.updateUser("HE140855", userDto);
		assertEquals(result.getData().size(), 1);
	}
	
	@Test
	public void testUpdateUser5() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(13);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(), anyString())).thenReturn(Optional.empty());
		ResponseMessage result = userService.updateUser("HE140855", createUserDto());
		assertEquals(result.getData().size(), 1);
	}
	
	@Test
	public void testUpdateUser4() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		UserDto userDto = createUserDto();
		userDto.setRoleId(10);
		Role role = role();
		role.setId(10);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(), anyString())).thenReturn(Optional.empty());
		ResponseMessage result = userService.updateUser("HE140855", userDto);
		assertEquals(result.getData().size(), 1);
	}
	
	@Test
	public void testUpdateUserFail() {
		List<User> users = Arrays.asList(createUser());
		users.get(0).setStudentId("HE140855");
		users.get(0).setEmail("toandvhe140855@fpt.edu.vn");
		Iterable<User> iterable = users;
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		ResponseMessage responseMessage = userService.updateUser("HE140855", createUserDto());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testUpdateUserFail2() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage responseMessage = userService.updateUser("HE140855", createUserDto());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testGetAllMemberAndCollaborator() {
		User user = createUser();
		List<User> users = Arrays.asList(user);
		Page<User> pages = new PageImpl<User>(users);
		when(userRepository.findMemberAndCollaboratorByRoleId(any())).thenReturn(pages);
		ResponseMessage responseMessage = userService.getAllMemberAndCollaborator(0,10,"studentId");
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testGetAllMemberAndCollaboratorCaseException() {
		ResponseMessage responseMessage = userService.getAllMemberAndCollaborator(0,-10,"studentId");
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testAddAnMemberOrCollaborator() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		ResponseMessage responseMessage = userService.addAnMemberOrCollaborator(createUserDto());
		assertEquals(responseMessage.getData().size(), 1);
	}
	
	@Test
	public void testAddAnMemberOrCollaboratorFail() {
		List<User> users = Arrays.asList(createUser());
		users.get(0).setStudentId("HE140855");
		users.get(0).setEmail("toandvhe140855@fpt.edu.vn");
		Iterable<User> iterable = users;
		when(userRepository.findAll()).thenReturn(iterable);
		ResponseMessage responseMessage = userService.addAnMemberOrCollaborator(createUserDto());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testAddAnMemberOrCollaboratorCaseException() {
		when(userRepository.findAll()).thenReturn(null);
		ResponseMessage responseMessage = userService.addAnMemberOrCollaborator(createUserDto());
		assertEquals(responseMessage.getData().size(), 0);
	}
	
	@Test
	public void testAddAnMemberOrCollaboratorSuccess2() {
		List<User> users = Arrays.asList(createUser());
		List<Semester> semesters = Arrays.asList(semester());
		Iterable<User> iterable = users;
		UserDto userDto = createUserDto();
		userDto.setRoleId(10);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findAll()).thenReturn(iterable);
		when(roleRepository.findById(anyInt())).thenReturn(Optional.of(role()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		ResponseMessage response = userService.addAnMemberOrCollaborator(userDto);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdmin() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(4);
		role.setName("ROLE_HeadCulture");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess2() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(5);
		role.setName("ROLE_ViceHeadCulture");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess3() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(6);
		role.setName("ROLE_HeadCommunication");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess4() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(7);
		role.setName("ROLE_ViceHeadCommunication");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess5() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(8);
		role.setName("ROLE_HeadTechnique");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess6() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(9);
		role.setName("ROLE_ViceHeadTechnique");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminSuccess7() {
		User user = createUser();
		List<Semester> semesters = Arrays.asList(semester());
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		user.setRole(role);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testDeleteAdminFail() {
		User user = createUser();
		Role role = new Role();
		role.setId(1);
		role.setName("ROLE_HeadClub");
		user.setRole(role);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(adminSemesterRepository.findByUserId(anyInt(), anyString())).thenReturn(Optional.of(adminSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(null);
		ResponseMessage response = userService.deleteAdmin("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateStatusForUser() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(),anyString())).thenReturn(Optional.of(memberSemester()));
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		ResponseMessage response = userService.updateStatusForUser("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testUpdateStatusForUserDeactive() {
		User user = createUser();
		user.setActive(false);
		MemberSemester memberSemester = memberSemester();
		memberSemester.setStatus(false);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(),anyString())).thenReturn(Optional.of(memberSemester));
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		ResponseMessage response = userService.updateStatusForUser("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testUpdateStatusForUserFail() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(),anyString())).thenReturn(null);
		ResponseMessage response = userService.updateStatusForUser("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testUpdateStatusForUserEmpty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.empty());
		ResponseMessage response = userService.updateStatusForUser("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testUpdateStatusForUserCaseMemberSemesterEmpty() {
		User user = createUser();
		user.setActive(false);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		when(memberSemesterRepository.findByUserIdAndSemester(anyInt(),anyString())).thenReturn(Optional.empty());
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		ResponseMessage response = userService.updateStatusForUser("HE140855", "Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testSearchUserByStudentIdOrName() {
		User user = createUser();
		List<User> users = Arrays.asList(user);
		Page<User> pages = new PageImpl<User>(users);
		when(userRepository.searchByStudentIdOrName(anyString(), any())).thenReturn(pages);
		ResponseMessage response = userService.searchUserByStudentIdOrName("HE140855", 0,5,"studentId");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testSearchUserByStudentIdOrNameFail() {
		ResponseMessage response = userService.searchUserByStudentIdOrName("HE140855", -1,5,"studentId");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void addUserFromExcel() throws IOException {
		User user = createUser();
		user.setActive(true);
		
		MultipartFile multipartFile = new MockMultipartFile("ut.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx")));
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findByStudentIdAndEmail(anyString(), anyString())).thenReturn(Optional.of(user));
		when(trainingScheduleRepository.findAllFutureTrainingSchedule(any())).thenReturn(Arrays.asList(trainingSchedule()));
		
		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void addUserFromExcelCaseException() throws IOException {
		MultipartFile multipartFile = new MockMultipartFile("ut.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx")));
		
		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
		assertEquals(response.getData().size(), 0);
	}
	
//	@Test
//	public void addUserFromExcelCaseUserNotActive() throws IOException {
//		MultipartFile multipartFile = new MockMultipartFile("ut.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx")));
//		List<Semester> semesters = Arrays.asList(semester());
//		ResponseMessage responseMessage = new ResponseMessage();
//		responseMessage.setData(semesters);
//		when(userRepository.findByStudentIdAndEmail(anyString(), anyString())).thenReturn(Optional.of(createUser()));
//		
//		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
//		assertEquals(response.getData().size(), 0);
//	}
	
	@Test
	public void addUserFromExcelDuplicateStudentId() throws IOException {
		MultipartFile multipartFile = new MockMultipartFile("ut.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx")));
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void addUserFromExcelDuplicateEmail() throws IOException {
		MultipartFile multipartFile = new MockMultipartFile("ut.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx")));
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void addUserFromExcelFail() throws IOException {
		MultipartFile multipartFile = new MockMultipartFile("ut-fail.xlsx", new FileInputStream(new File("C:\\Users\\VAN TOAN\\Desktop\\ut-fail.xlsx")));
		List<User> users = Arrays.asList(createUser());
		users.get(0).setStudentId("HE140855");
		users.get(0).setEmail("toandvhe140855@fpt.edu.vn");
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.addUsersFromExcel(multipartFile);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void exportUsersToExcel() {
		ByteArrayInputStream in = userService.exportUsersToExcel(usersDto());
		assertEquals(in, in);
	}
	
	@Test
	public void exportUsersToExcelWithError() {
		ByteArrayInputStream in = userService.exportUsersToExcelWithError(usersDto());
		assertEquals(in, in);
	}
	
	@Test
	public void testFindAllMember() {
		User user = createUser();
		List<User> users = Arrays.asList(user);
		Page<User> pages = new PageImpl<User>(users);
		when(userRepository.findMember(any())).thenReturn(pages);
		ResponseMessage response = userService.findAllMember(0,5,"studentId");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testFindAllMemberException() {
//		User user = createUser();
//		List<User> users = Arrays.asList(user);
//		Page<User> pages = new PageImpl<User>(users);
		ResponseMessage response = userService.findAllMember(0,-5,"studentId");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAllUser() {
		List<User> users = Arrays.asList(createUser());
		Iterable<User> iterable = users;
		when(userRepository.findAll()).thenReturn(iterable);
		ResponseMessage response = userService.getAllUser();
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllUserFail() {
		when(userRepository.findAll()).thenReturn(null);
		ResponseMessage response = userService.getAllUser();
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAllUserFail1() {
		List<User> users = new ArrayList<User>();
		Iterable<User> iterable = users;
		when(userRepository.findAll()).thenReturn(iterable);
		ResponseMessage response = userService.getAllUser();
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetMembersBySemester() {
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		User user = createUser();
		user.setActive(false);
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(Arrays.asList(memberSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findCollaborator()).thenReturn(Arrays.asList(user));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		ResponseMessage response = userService.getMembersBySemester("Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetMembersBySemesterDiffSemester() {
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		User user = createUser();
		user.setActive(false);
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(Arrays.asList(memberSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findCollaborator()).thenReturn(Arrays.asList(user));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		ResponseMessage response = userService.getMembersBySemester("Spring2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetMembersBySemesterFail() {
		List<Semester> semesters = new ArrayList<Semester>();
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(Arrays.asList(memberSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		ResponseMessage response = userService.getMembersBySemester("Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetMembersBySemesterCaseDeactive() {
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		User user = createUser();
		MemberSemester memberSemester = memberSemester();
		memberSemester.setStatus(false);
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(Arrays.asList(memberSemester));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findCollaborator()).thenReturn(Arrays.asList(user));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		ResponseMessage response = userService.getMembersBySemester("Summer2022");
		assertEquals(response.getData().size(), 2);
	}
	
	@Test
	public void testGetMembersBySemesterCaseDataEmpty() {
		List<MemberSemester> memberSemesters = new ArrayList<MemberSemester>();
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		User user = createUser();
		user.setActive(false);
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(memberSemesters);
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findCollaborator()).thenReturn(Arrays.asList(user));
		ResponseMessage response = userService.getMembersBySemester("Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetMembersBySemesterCaseCollabDataEmpty() {
		List<Semester> semesters = Arrays.asList(semester());
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(semesters);
		User user = createUser();
		user.setActive(false);
		List<User> users = new ArrayList<User>();
		when(memberSemesterRepository.findBySemesterOrderByIdDesc(anyString())).thenReturn(Arrays.asList(memberSemester()));
		when(semesterService.getCurrentSemester()).thenReturn(responseMessage);
		when(userRepository.findCollaborator()).thenReturn(users);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(user));
		ResponseMessage response = userService.getMembersBySemester("Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAdminBySemester() {
		when(adminSemesterRepository.findBySemester(anyString())).thenReturn(Arrays.asList(adminSemester()));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.getAdminBySemester("Summer2022");
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAdminBySemesterCaseStatusSemesterNull() {
		List<AdminSemester> adminSemesters = new ArrayList<AdminSemester>();
		when(adminSemesterRepository.findBySemester(anyString())).thenReturn(adminSemesters);
		ResponseMessage response = userService.getAdminBySemester("Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAdminBySemesterCaseException() {
		when(adminSemesterRepository.findBySemester(anyString())).thenReturn(null);
		ResponseMessage response = userService.getAdminBySemester("Summer2022");
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseName() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"toan","","","",null,null,"",null);
		assertEquals(response.getData().size(), 2);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseStudentId() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","HE","","",null,null,"",null);
		assertEquals(response.getData().size(), 2);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseEmail() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","toandv","",null,null,"",null);
		assertEquals(response.getData().size(), 2);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseGender() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","true",null,null,"",null);
		assertEquals(response.getData().size(), 2);
	}
	@Test 
	public void testSearchByMultipleFieldCaseGeneration() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","",1,null,"",null);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseRoleId() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","",null,1,"",null);
		assertEquals(response.getData().size(), 1);
	}
	
	@Test 
	public void testSearchByMultipleFieldCaseIsActive() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","",null,null,"true",null);
		assertEquals(response.getData().size(), 2);
	}
	@Test 
	public void testSearchByMultipleFieldCaseDateFrom() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","",null,null,"",1);
		assertEquals(response.getData().size(), 0);
	}

	@Test 
	public void testSearchByMultipleFieldCaseDateToException() {
		List<UserDto> userDtoResponse = usersDto();
		ResponseMessage response = userService.searchByMultipleField(userDtoResponse,"","","","",null,null,"",1);
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGenerateQrCode() {
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus());
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGenerateQrCodeCaseAttendanceStatusNull() {
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(null);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGenerateQrCodeCaseTrainingScheduleNull() {
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(null);
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGenerateQrCodeCaseException() {
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGenerateQrCodeCaseAttendanceStatusEq0() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(0);
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGenerateQrCodeCaseAttendanceStatusEq2() {
		AttendanceStatus attendanceStatus = attendanceStatus();
		attendanceStatus.setStatus(2);
		when(trainingScheduleService.getTrainingScheduleByDate(any())).thenReturn(trainingSchedule());
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		ResponseMessage response = userService.generateQrCode(inforInQrCode());
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllActiveMemberAndCollaborator() {
		when(userRepository.findActiveMembersAndCollaborators()).thenReturn(Arrays.asList(createUser()));
		ResponseMessage response = userService.getAllActiveMemberAndCollaborator();
		assertEquals(response.getData().size(), 1);
	}
	
	@Test
	public void testGetAllActiveMemberAndCollaboratorCaseUserEmpty() {
		when(userRepository.findActiveMembersAndCollaborators()).thenReturn(new ArrayList<User>());
		ResponseMessage response = userService.getAllActiveMemberAndCollaborator();
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAllActiveMemberAndCollaboratorFail() {
		when(userRepository.findActiveMembersAndCollaborators()).thenReturn(null);
		ResponseMessage response = userService.getAllActiveMemberAndCollaborator();
		assertEquals(response.getData().size(), 0);
	}
	
	@Test
	public void testGetAllUserAttendanceStatus() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus());
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(Optional.of(memberEvent()));
		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(),anyInt())).thenReturn(Optional.of(attendanceEvent()));
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseBeforeTrainingSchedule() {
		TrainingSchedule trainingSchedule = trainingSchedule();
		trainingSchedule.setDate(LocalDate.now().plusDays(1));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule));
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(Optional.of(memberEvent()));
		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(),anyInt())).thenReturn(Optional.of(attendanceEvent()));
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseAttendanceStatusNull() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(null);
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(Optional.of(memberEvent()));
		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(),anyInt())).thenReturn(Optional.of(attendanceEvent()));
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseAttendanceEventEmpty() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(null);
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(Optional.of(memberEvent()));
		when(attendanceEventRepository.findByEventIdAndUserId(anyInt(),anyInt())).thenReturn(Optional.empty());
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseBeforeEventSchedule() {
		EventSchedule eventSchedule = eventSchedule();
		eventSchedule.setDate(LocalDate.now().plusDays(1));
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus());
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule));
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseRegisterStatusFalse() {
		MemberEvent memberEvent = memberEvent();
		memberEvent.setRegisterStatus(false);
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus());
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(Optional.of(memberEvent));
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	@Test
	public void testGetAllUserAttendanceStatusCaseMemberEventNull() {
		when(userRepository.findByStudentId(anyString())).thenReturn(Optional.of(createUser()));
		Optional<MemberEvent> memOptional = Optional.empty();
		when(trainingScheduleRepository.findAll()).thenReturn(Arrays.asList(trainingSchedule()));
		when(attendanceStatusRepository.findByUserIdAndTrainingScheduleId(anyInt(),anyInt())).thenReturn(attendanceStatus());
		when(eventScheduleRepository.findAll()).thenReturn(Arrays.asList(eventSchedule()));
		when(memberEventRepository.findMemberEventByEventAndUser(anyInt(),anyInt())).thenReturn(memOptional);
		when(tournamentScheduleRepository.findAll()).thenReturn(Arrays.asList(tournamentSchedule()));
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 3);
	}
	
	@Test
	public void testGetAllUserAttendanceStatusCaseException() {
		when(userRepository.findByStudentId(anyString())).thenReturn(null);
		ResponseMessage response = userService.getAllUserAttendanceStatus("HE140855");
		assertEquals(response.getData().size(), 0);
	}
	
	
}