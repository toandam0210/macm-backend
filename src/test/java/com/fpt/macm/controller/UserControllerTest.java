package com.fpt.macm.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.InforInQrCode;
import com.fpt.macm.model.dto.UserAttendanceStatusDto;
import com.fpt.macm.model.dto.UserDto;
import com.fpt.macm.model.entity.Role;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.service.UserService;

@SpringBootTest
public class UserControllerTest {
	@MockBean
	private UserService userService;

//	@Mock
//	private UserRepository userRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	UserController userController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	private User createUser() {
		User user = new User();
		user.setStudentId("HE140855");
		user.setId(1);
		user.setName("Dam Van Toan");
		user.setGender(true);
		LocalDate localDate = LocalDate.of(2000, 02, 10);
		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140855@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		Role role = new Role();
		role.setId(1);
		user.setRole(role);
		user.setCreatedOn(LocalDate.now());
		user.setCreatedBy("toandv");
		return user;
	}
	
	private UserDto createUserDto() {
		UserDto user = new UserDto();
		user.setStudentId("HE140855");
		user.setName("Dam Van Toan");
		user.setGender(true);
//		LocalDate localDate = LocalDate.of(2000, 02, 10);
//		user.setDateOfBirth(localDate);
		user.setEmail("toandvhe140855@fpt.edu.vn");
		user.setImage(null);
		user.setPhone("0982102000");
		user.setActive(true);
		user.setCurrentAddress("Dom A");
		user.setRoleId(1);
		user.setGeneration(4);
		return user;
	}
	
	private List<UserDto> usersDto(){
		List<UserDto> users = new ArrayList<UserDto>();
		UserDto user = new UserDto();
		user.setStudentId("HE140855");
		user.setName("Dam Van Toan");
		user.setGender(true);
//		LocalDate localDate = LocalDate.of(2000, 02, 10);
//		user.setDateOfBirth(localDate);
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
//		LocalDate localDate = LocalDate.of(2000, 02, 10);
//		user.setDateOfBirth(localDate);
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
//		LocalDate localDate = LocalDate.of(2000, 02, 10);
//		user.setDateOfBirth(localDate);
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
	@Test
	public void getUserByStudentIdTestSuccess() throws Exception {
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.getUserByStudentId(anyString())).thenReturn(responseMessage);

		this.mockMvc.perform(get("/api/admin/hr/getbystudentid/{studentId}", "HE140855")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data[0].studentId").value("HE140855"))
				.andExpect(jsonPath("$.data[0].name").value("Dam Van Toan"))
				.andExpect(jsonPath("$.data[0].email").value("toandvhe140855@fpt.edu.vn"));

	}
	
	@Test
	public void getAllAdminForViceHeadClubSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.getAllAdminForViceHeadClub(anyInt(),anyInt(),anyString())).thenReturn(responseMessage);
		
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getalladmin")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(3));
	}

	
	@Test
	public void getAllAdminForHeadClubSuccess() throws Exception {
		
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.getAllAdminForHeadClub(anyInt(),anyInt(),anyString())).thenReturn(responseMessage);

		this.mockMvc.perform(get("/api/admin/hr/headclub/getalladmin")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	@Test
	public void updateUserTestSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUserDto()));
		when(userService.updateUser(anyString(),any(UserDto.class))).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140855")
		.content(asJsonString(createUserDto()))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getAllMemberAndCollaboratorSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.getAllMemberAndCollaborator(anyInt(),anyInt(),anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/getallmemberandcollaborator")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(3));
	}

	
	
	@Test
	public void addAnMemberOrCollaboratorSuccess() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setStudentId("HE1400001");
		userDto.setEmail("toandam10@fpt.edu.vn");
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUserDto()));
		when(userService.addAnMemberOrCollaborator(any(UserDto.class))).thenReturn(responseMessage);
		this.mockMvc.perform(post("/api/admin/hr/adduser")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void deleteAdminTestSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.deleteAdmin(anyString(),anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140858").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}	
	@Test
	public void updateStatusForUserSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.updateStatusForUser(anyString(),anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(put("/api/admin/hr/updatestatus")
		.param("studentId", "HE140863")
		.param("semester", "Summer2022"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	@Test
	public void searchUserByStudentIdOrNameSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.searchUserByStudentIdOrName(anyString(),anyInt(),anyInt(),anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/users/search")
				.param("inputSearch", "HE")
				).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	@Test
	public void addUsersFromExcelSuccess() throws Exception {
		final byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx"));
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.addUsersFromExcel(any())).thenReturn(responseMessage);
		this.mockMvc.perform(multipart("/api/admin/hr/users/import").file("file", bytes)
				).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getAllMembersTestSuccess() throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.findAllMember(anyInt(),anyInt(),anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getallmembers")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void testGetAllUserSuccess() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(createUser()));
		when(userService.getAllUser()).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getallusers")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void testGetMembersBySemesterSuccess() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.getMembersBySemester(anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getmembers/semester").param("semester", "Summer2022")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	@Test
	public void testGetAdminsBySemesterSuccess() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.getAdminBySemester(anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getadmins/semester").param("semester", "Summer2022")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	@Test
	public void testSearchSuccess() throws Exception{
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(usersDto());
		when(userService.searchByMultipleField(anyList(),anyString(),anyString(),anyString(),anyString(),anyInt(),anyInt(),anyString(),any())).thenReturn(responseMessage);
		this.mockMvc.perform(post("/api/admin/hr/viceheadclub/member/search").param("name", "toan").content(asJsonString(usersDto()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	public void testGenerateQrCode() throws Exception{
		InforInQrCode inforInQrCode = new InforInQrCode();
		inforInQrCode.setStudentId("HE140855");
		inforInQrCode.setStudentName("dam van toan");
		inforInQrCode.setDate(LocalDate.now().toString());
		inforInQrCode.setStatus(true);
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList("abc"));
		when(userService.generateQrCode(any(InforInQrCode.class))).thenReturn(responseMessage);
		this.mockMvc.perform(post("/api/admin/hr/member/qrcode/create").content(asJsonString(inforInQrCode))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void testGetAllUserAttendanceStatus() throws Exception {
		UserAttendanceStatusDto userAttendanceStatusDto = new UserAttendanceStatusDto();
		userAttendanceStatusDto.setUserName("dam van toan");
		userAttendanceStatusDto.setDate(LocalDate.now());
		userAttendanceStatusDto.setFinishTime(LocalTime.of(20, 0, 0));
		userAttendanceStatusDto.setStartTime(LocalTime.of(18, 0, 0));
		userAttendanceStatusDto.setStatus(0);
		userAttendanceStatusDto.setStudentId("HE140855");
		userAttendanceStatusDto.setTitle("Lịch tập");
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(userAttendanceStatusDto));
		when(userService.getAllUserAttendanceStatus(anyString())).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/getallattendancestatusofuser/{studentId}", "HE140855")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void getAllActiveMemberAndCollaborator() throws Exception {
		ActiveUserDto activeUserDto = new ActiveUserDto();
		activeUserDto.setGender(true);
		activeUserDto.setStudentId("HE140855");
		activeUserDto.setStudentName("dam van toan");
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setData(Arrays.asList(activeUserDto));
		when(userService.getAllActiveMemberAndCollaborator()).thenReturn(responseMessage);
		this.mockMvc.perform(get("/api/admin/hr/getallactivememberandcollaborator")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	
	public static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

}
