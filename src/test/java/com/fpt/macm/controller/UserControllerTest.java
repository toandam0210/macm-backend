package com.fpt.macm.controller;

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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.service.UserService;

@SpringBootTest
public class UserControllerTest {
	@Mock
	private UserService userService;

	@Mock
	private UserRepository userRepository;

	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;

	@InjectMocks
	UserController userController;

	@BeforeEach
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

//	private User createUser() {
//		User user = new User();
//		user.setStudentId("HE140855");
//		user.setId(1);
//		user.setName("Dam Van Toan");
//		user.setGender(true);
//		LocalDate localDate = LocalDate.of(2000, 02, 10);
//		user.setDateOfBirth(localDate);
//		user.setEmail("toandvhe140855@fpt.edu.vn");
//		user.setImage(null);
//		user.setPhone("0982102000");
//		user.setActive(true);
//		user.setCurrentAddress("Dom A");
//		Role role = new Role();
//		role.setId(1);
//		user.setRole(role);
//		user.setCreatedOn(LocalDate.now());
//		user.setCreatedBy("toandv");
//		return user;
//	}
	
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
	@Test
	public void getUserByStudentIdTestSuccess() throws Exception {

		this.mockMvc.perform(get("/api/admin/hr/getbystudentid/{studentId}", "HE140855")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data[0].studentId").value("HE140855"))
				.andExpect(jsonPath("$.data[0].name").value("Dam Van Toan"))
				.andExpect(jsonPath("$.data[0].email").value("toandvhe140855@fpt.edu.vn"));

	}

	@Test
	public void getUserByStudentIdTestFail() throws Exception {

		this.mockMvc.perform(get("/api/admin/hr/getbystudentid/{studentId}", "HA140855")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.data").isEmpty());

	}

	@Test
	public void getAllAdminForViceHeadClubSuccess() throws Exception {

		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getalladmin")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(7));
	}

	
	@Test
	public void getAllAdminForHeadClubSuccess() throws Exception {

		this.mockMvc.perform(get("/api/admin/hr/headclub/getalladmin")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(8));
	}
	
	@Test
	public void updateUserTestSuccess() throws Exception {
		UserDto userDto = createUserDto();
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140855")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateUserTestFailStudentId() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setStudentId("HE140856");
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140855")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void updateUserTestFailEmail() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setEmail("tuanlahe141277@fpt.edu.vn");
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140855")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void updateUserTestFailRole() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setRoleId(1);
		userDto.setEmail("tuanlahe141277@fpt.edu.vn");
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140855")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void updateUserTestFail() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setEmail("tuanlahe141277@fpt.edu.vn");
		this.mockMvc.perform(put("/api/admin/hr/updateuser/{studentId}", "HE140032")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void getAllMemberAndCollaboratorSuccess() throws Exception {
		this.mockMvc.perform(get("/api/admin/hr/getallmemberandcollaborator")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(10));
	}
	
	@Test
	public void addAnMemberOrCollaboratorFail() throws Exception {
		UserDto userDto = createUserDto();
		this.mockMvc.perform(post("/api/admin/hr/adduser")
		.content(asJsonString(userDto))
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void addAnMemberOrCollaboratorSuccess() throws Exception {
		UserDto userDto = createUserDto();
		userDto.setStudentId("HE1400001");
		userDto.setEmail("toandam10@fpt.edu.vn");
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
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140855").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void deleteAdminTestSuccess2() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140858").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	@Test
	public void deleteAdminTestSuccess3() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140859").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	@Test
	public void deleteAdminTestSuccess4() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140862").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	@Test
	public void deleteAdminTestSuccess5() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/deleteadmin/{studentId}", "HE140863").param("semester", "Summer2022")
		.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void updateStatusForUserSuccess() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/updatestatus")
		.param("studentId", "HE140863")
		.param("semester", "Summer2022"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	@Test
	public void updateStatusForUserSuccess2() throws Exception {
		this.mockMvc.perform(put("/api/admin/hr/updatestatus")
		.param("studentId", "HE141280")
		.param("semester", "Summer2022"))
		.andExpect(status().isOk())
		.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(1));
	}
	
	@Test
	public void searchUserByStudentIdOrNameSuccess() throws Exception {

		this.mockMvc.perform(get("/api/admin/hr/users/search")
				.param("inputSearch", "HE")
				).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(10));
	}
	
	
	@Test
	public void addUsersFromExcelSuccess() throws Exception {
		final byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\VAN TOAN\\Desktop\\ut.xlsx"));
			
		this.mockMvc.perform(multipart("/api/admin/hr/users/import").file("file", bytes)
				).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	@Test
	public void addUsersFromExcelFail() throws Exception {
		final byte[] bytes = Files.readAllBytes(Paths.get("C:\\Users\\VAN TOAN\\Desktop\\testexcel.xlsx"));
			
		this.mockMvc.perform(multipart("/api/admin/hr/users/import").file("file", bytes)
				).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.data.size()").value(0));
	}
	
	@Test
	public void getAllMembersTestSuccess() throws Exception {
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getallmembers")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(10));
	}
	
	@Test
	public void testGetAllUserSuccess() throws Exception{
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getallusers")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(19));
	}
	
	@Test
	public void testGetMembersBySemesterSuccess() throws Exception{
		this.mockMvc.perform(get("/api/admin/hr/viceheadclub/getmembers/semester").param("semester", "Summer2022")).andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.data.size()").value(3));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	      return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	      throw new RuntimeException(e);
	    }
	  }

}
