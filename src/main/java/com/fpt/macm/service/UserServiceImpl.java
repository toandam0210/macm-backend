package com.fpt.macm.service;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
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
		}
		userRepository.saveAll(users);
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
}
