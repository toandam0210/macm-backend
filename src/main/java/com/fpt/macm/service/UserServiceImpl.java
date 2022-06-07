package com.fpt.macm.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.dto.UserToCsvDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ERole;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseMessage getUserByStudentId(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_001);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllAdminForViceHeadClub(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<User> pageResponse = userRepository.findAdminForViceHeadClubByRoleId(paging);
			List<User> admins = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(admins);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}

		return responseMessage;
	}

	@Override
	public ResponseMessage getAllAdminForHeadClub(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<User> pageResponse = userRepository.findAdminForHeadClubByRoleId(paging);
			List<User> admins = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(admins);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}

		return responseMessage;
	}

	@Override
	public ResponseMessage updateUser(String studentId, UserDto userDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			user.setStudentId(userDto.getStudentId());
			user.setRole(userDto.getRole());
			user.setEmail(userDto.getEmail());
			user.setPhone(userDto.getPhoneNumber());
			user.setCurrentAddress(userDto.getCurrentAddress());
			user.setUpdatedBy("toandv");
			user.setUpdatedOn(LocalDateTime.now());
			userRepository.save(user);
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_005);

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addListMemberAndCollaboratorFromFileCsv(MultipartFile file) throws Exception {
		ResponseMessage responseMessage = new ResponseMessage();
		List<User> users = new ArrayList<User>();
		InputStream inputStream = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser = new CsvParser(setting);
		List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
		for (Record record : parseAllRecords) {
			User user = new User();
			user.setStudentId(record.getString("student_id"));
			user.setName(record.getString("name"));
			DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			user.setDateOfBirth(LocalDate.parse(record.getString("date_of_birth"), f));
			user.setPhone(record.getString("phone"));
			user.setEmail(record.getString("email"));
			user.setGender(Boolean.parseBoolean(record.getString("gender")));
			user.setImage(record.getString("image"));
			user.setActive(Boolean.parseBoolean(record.getString("is_active")));
			List<String> roles = Arrays.asList(Constant.ROLES);
			for (int i = 0; i < roles.size(); i++) {
				Role role = new Role();
				if (record.getString("role").equals(roles.get(i))) {
					role.setId(i + 1);
					user.setRole(role);
				}
			}
			user.setCurrentAddress(record.getString("current_address"));
			user.setCreatedBy("toandv");
			user.setCreatedOn(LocalDate.now());
			users.add(user);
			userRepository.save(user);
		}
		responseMessage.setData(users);
		responseMessage.setTotalResult(users.size());
		responseMessage.setMessage(Constant.MSG_006);
		return responseMessage;

	}

	@Override
	public ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<User> pageResponse = userRepository.findMemberAndCollaboratorByRoleId(paging);
			List<User> users = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				users = pageResponse.getContent();
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(users);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}

		return responseMessage;
	}

	@Override
	public ResponseMessage addAnMemberOrCollaborator(User user) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			user.setActive(true);
			user.setCreatedBy("toandv");
			user.setCreatedOn(LocalDate.now());
			userRepository.save(user);
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_007);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}

		return responseMessage;
	}

	@Override
	public ResponseMessage deleteAdmin(String studentId, Role role) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			if (user.getRole().getName().equals(ERole.ROLE_HeadCulture.name())
					|| user.getRole().getName().equals(ERole.ROLE_ViceHeadCulture.name())) {
				role.setId(Constant.ROLE_ID_MEMBER_CULTURE);
				user.setRole(role);
			} else if (user.getRole().getName().equals(ERole.ROLE_HeadCommunication.name())
					|| user.getRole().getName().equals(ERole.ROLE_ViceHeadCommunication.name())) {
				role.setId(Constant.ROLE_ID_MEMBER_COMMUNICATION);
				user.setRole(role);
			} else {
				role.setId(Constant.ROLE_ID_MEMBER_TECHNIQUE);
				user.setRole(role);
			}

			userRepository.save(user);
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_004);

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateListStatusForUser(List<User> users) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			for (User user : users) {
				User tempUser = userRepository.findByStudentId(user.getStudentId()).get();
				tempUser.setActive(user.isActive());
				userRepository.save(tempUser);
			}
			responseMessage.setData(users);
			responseMessage.setMessage(Constant.MSG_005);
			responseMessage.setTotalResult(users.size());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public void export(HttpServletResponse response) throws IOException {
		response.setContentType("text/csv; charset=UTF-8");
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String currentDateTime = dateFormatter.format(new Date());
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
		response.setHeader(headerKey, headerValue);
		response.setCharacterEncoding("UTF-8");
		ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
		String[] csvHeader = { "student_id", "name", "date_of_birth", "phone", "email", "gender", "image", "is_active",
				"role", "current_address" };
		String[] nameMapping = { "studentId", "name", "dateOfBirth", "phone", "email", "gender", "image", "isActive",
				"role", "currentAddress" };
		csvWriter.writeHeader(csvHeader);
		List<User> users = (List<User>) userRepository.findAll();
		List<UserToCsvDto> userToCsvDtos = new ArrayList<UserToCsvDto>();
		for (User user : users) {
			UserToCsvDto userToCsvDto = convertUserToUserCsv(user);
			userToCsvDtos.add(userToCsvDto);
		}
		for (UserToCsvDto userToCsvDto : userToCsvDtos) {
			csvWriter.write(userToCsvDto, nameMapping);
		}
		csvWriter.close();
	}
	
	public void convertUserRoleFromDbToCsv(User user, UserToCsvDto userToCsvDto) {
		switch (user.getRole().getName()) {
		case "ROLE_HeadClub":
			userToCsvDto.setRole("Chủ nhiệm");
			break;
		case "ROLE_ViceHeadClub":
			userToCsvDto.setRole("Phó chủ nhiệm");
			break;
		case "ROLE_Treasurer":
			userToCsvDto.setRole("Thủ quỹ");
			break;
		case "ROLE_HeadCulture":
			userToCsvDto.setRole("Trưởng ban văn hóa");
			break;
		case "ROLE_ViceHeadCulture":
			userToCsvDto.setRole("Phó ban văn hóa");
			break;
		case "ROLE_HeadCommunication":
			userToCsvDto.setRole("Trưởng ban truyền thông");
			break;
		case "ROLE_ViceHeadCommunication":
			userToCsvDto.setRole("Phó ban truyền thông");
			break;
		case "ROLE_HeadTechnique":
			userToCsvDto.setRole("Trưởng ban chuyên môn");
			break;
		case "ROLE_ViceHeadTechnique":
			userToCsvDto.setRole("Phó ban chuyên môn");
			break;
		case "ROLE_Member_Commnication":
			userToCsvDto.setRole("Thành viên ban truyền thông");
			break;
		case "ROLE_Member_Culture":
			userToCsvDto.setRole("Thành viên ban văn hóa");
			break;
		case "ROLE_Member_Technique":
			userToCsvDto.setRole("Thành viên ban chuyên môn");
			break;
		case "ROLE_Collaborator_Commnunication":
			userToCsvDto.setRole("CTV truyền thông");
			break;
		case "ROLE_Collaborator_Culture":
			userToCsvDto.setRole("CTV văn hóa");
			break;
		case "ROLE_Collaborator_Technique":
			userToCsvDto.setRole("CTV chuyên môn");
			break;

		default:
			userToCsvDto.setRole("Thành viên ban chuyên môn");
			break;
		}
	}
	
	public UserToCsvDto convertUserToUserCsv(User user) {
		UserToCsvDto userToCsvDto = new UserToCsvDto();
		userToCsvDto.setStudentId(user.getStudentId());
		userToCsvDto.setName(user.getName());
		userToCsvDto.setDateOfBirth(user.getDateOfBirth());
		userToCsvDto.setPhone(user.getPhone());
		userToCsvDto.setEmail(user.getEmail());
		if (user.isGender()) {
			userToCsvDto.setGender("Nam");
		} else {
			userToCsvDto.setGender("Nữ");
		}
		userToCsvDto.setImage(user.getImage());
		if (user.isActive()) {
			userToCsvDto.setIsActive("Hoạt động");
		} else {
			userToCsvDto.setIsActive("Không hoạt động");
		}
		convertUserRoleFromDbToCsv(user, userToCsvDto);
		userToCsvDto.setCurrentAddress(user.getCurrentAddress());
		return userToCsvDto;
	}
}
