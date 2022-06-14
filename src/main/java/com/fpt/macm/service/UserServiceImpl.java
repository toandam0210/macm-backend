package com.fpt.macm.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.helper.ExcelHelper;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ERole;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.RoleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Override
	public ResponseMessage getUserByStudentId(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			Utils.convertNameOfRole(user.getRole());
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
			Pageable paging = PageRequest.of(pageNo, pageSize, Utils.sortUser(sortBy));
			Page<User> pageResponse = userRepository.findAdminForViceHeadClubByRoleId(paging);
			List<User> admins = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			for (User admin : admins) {
				Utils.convertNameOfRole(admin.getRole());
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

			Pageable paging = PageRequest.of(pageNo, pageSize, Utils.sortUser(sortBy));
			Page<User> pageResponse = userRepository.findAdminForHeadClubByRoleId(paging);
			List<User> admins = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			for (User admin : admins) {
				Utils.convertNameOfRole(admin.getRole());
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
			List<User> users = (List<User>) userRepository.findAll();
			users.remove(user);
			boolean checkDuplicateEmail = false;
			boolean checkDuplicateStudentId = false;
			Role currentUserRole = user.getRole();
			Optional<Role> roleOptional = roleRepository.findById(userDto.getRoleId());
			if (currentUserRole.getName().equals(ERole.ROLE_HeadClub.name())
					&& !roleOptional.get().getName().equals(currentUserRole.getName())) {
				responseMessage.setMessage(Constant.MSG_035);
			} else {
				for (User currentUser : users) {
					if (currentUser.getStudentId().equals(userDto.getStudentId())) {
						checkDuplicateStudentId = true;
					} else if (currentUser.getEmail().equals(userDto.getEmail())) {
						checkDuplicateEmail = true;
					}
				}
				if (!checkDuplicateEmail && !checkDuplicateStudentId) {
					user.setRole(roleOptional.get());
					user.setName(userDto.getName());
					user.setEmail(userDto.getEmail());
					user.setPhone(userDto.getPhone());
					user.setCurrentAddress(userDto.getCurrentAddress());
					user.setDateOfBirth(userDto.getDateOfBirth());
					user.setGender(userDto.isGender());
					user.setStudentId(userDto.getStudentId());
					user.setUpdatedBy("toandv");
					user.setUpdatedOn(LocalDateTime.now());
					userRepository.save(user);
					responseMessage.setData(Arrays.asList(user));
					responseMessage.setMessage(Constant.MSG_005);
				} else {
					String messageError = "";
					if (checkDuplicateStudentId) {
						messageError += Constant.MSG_048 + userDto.getStudentId() + Constant.MSG_050;
					}
					if (checkDuplicateEmail) {
						messageError += Constant.MSG_049 + userDto.getEmail() + Constant.MSG_050;
					}
					responseMessage.setMessage(messageError);
					responseMessage.setCode(400);
				}
			}

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Utils.sortUser(sortBy));
			Page<User> pageResponse = userRepository.findMemberAndCollaboratorByRoleId(paging);
			List<User> users = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				users = pageResponse.getContent();
			}
			for (User user : users) {
				Utils.convertNameOfRole(user.getRole());
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
	public ResponseMessage addAnMemberOrCollaborator(UserDto userDto) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> users = (List<User>) userRepository.findAll();
			boolean checkDuplicateEmail = false;
			boolean checkDuplicateStudentId = false;
			for (User user : users) {
				if (user.getStudentId().equals(userDto.getStudentId())) {
					checkDuplicateStudentId = true;
				} else if (user.getEmail().equals(userDto.getEmail())) {
					checkDuplicateEmail = true;
				}
			}
			if (!checkDuplicateEmail && !checkDuplicateStudentId) {
				User user = new User();
				user.setStudentId(userDto.getStudentId());
				user.setName(userDto.getName());
				user.setGender(userDto.isGender());
				user.setDateOfBirth(userDto.getDateOfBirth());
				user.setEmail(userDto.getEmail());
				user.setImage(userDto.getImage());
				user.setPhone(userDto.getPhone());
				user.setCurrentAddress(userDto.getCurrentAddress());
				Optional<Role> roleOptional = roleRepository.findById(userDto.getRoleId());
				if (roleOptional.isPresent()) {
					user.setRole(roleOptional.get());
				}
				user.setActive(true);
				user.setCreatedBy("toandv");
				user.setCreatedOn(LocalDate.now());
				userRepository.save(user);
				responseMessage.setData(Arrays.asList(user));
				responseMessage.setMessage(Constant.MSG_007);
			} else {
				String messageError = "";
				if (checkDuplicateStudentId) {
					messageError += Constant.MSG_048 + userDto.getStudentId() + Constant.MSG_050;
				}
				if (checkDuplicateEmail) {
					messageError += Constant.MSG_049 + userDto.getEmail() + Constant.MSG_050;
				}
				responseMessage.setMessage(messageError);
				responseMessage.setCode(400);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}

		return responseMessage;
	}

	@Override
	public ResponseMessage deleteAdmin(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			if (user.getRole().getName().equals(ERole.ROLE_HeadCulture.name())
					|| user.getRole().getName().equals(ERole.ROLE_ViceHeadCulture.name())) {
				Role role = new Role();
				role.setId(Constant.ROLE_ID_MEMBER_CULTURE);
				user.setRole(role);
			} else if (user.getRole().getName().equals(ERole.ROLE_HeadCommunication.name())
					|| user.getRole().getName().equals(ERole.ROLE_ViceHeadCommunication.name())) {
				Role role = new Role();
				role.setId(Constant.ROLE_ID_MEMBER_COMMUNICATION);
				user.setRole(role);
			} else {
				Role role = new Role();
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
	public ResponseMessage updateStatusForUser(String studentId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = new User();
			Optional<User> userOptional = userRepository.findByStudentId(studentId);
			if (userOptional.isPresent()) {
				user = userOptional.get();
				if (user.isActive()) {
					user.setActive(false);
				} else {
					user.setActive(true);
				}
				userRepository.save(user);
			}
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_005);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage searchUserByStudentIdOrName(String inputSearch, int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Utils.sortUser(sortBy));
			Page<User> pageResponse = userRepository.searchByStudentIdOrName(inputSearch, paging);
			List<User> users = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				users = pageResponse.getContent();
			}
			for (User user : users) {
				Utils.convertNameOfRole(user.getRole());
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

//	@Override
//	public ResponseMessage userLogin() {
//		ResponseMessage responseMessage = new ResponseMessage();
//		String username = SecurityContextHolder.getContext().getAuthentication().getName();
//		User user = userRepository.findByEmail(username).get();
//		responseMessage.setData(Arrays.asList(user));
//		responseMessage.setMessage("Login successful");
//		return responseMessage;
//	}
	@Override
	public ResponseMessage addUsersFromExcel(MultipartFile file) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> usersFromExcel = ExcelHelper.excelToUsers(file.getInputStream());
			List<User> users = (List<User>) userRepository.findAll();
			boolean checkDuplicateEmail = false;
			boolean checkDuplicateStudentId = false;
			String messageError = "";
			for (User userFromExcel : usersFromExcel) {
				for (User user : users) {
					if (userFromExcel.getStudentId().equals(user.getStudentId())) {
						checkDuplicateStudentId = true;
					} else if (userFromExcel.getEmail().equals(user.getEmail())) {
						checkDuplicateEmail = true;
					}
				}
				if (!checkDuplicateEmail && !checkDuplicateStudentId) {
					userFromExcel.setCreatedOn(LocalDate.now());
					userFromExcel.setCreatedBy("toandv");
					userRepository.saveAll(usersFromExcel);
					responseMessage.setData(usersFromExcel);
					responseMessage.setMessage(Constant.MSG_006);
				} else {
					if (checkDuplicateStudentId) {
						messageError += Constant.MSG_048 + userFromExcel.getStudentId() + Constant.MSG_050;
					}
					if (checkDuplicateEmail) {
						messageError += Constant.MSG_049 + userFromExcel.getEmail() + Constant.MSG_050;
					}
				}
				responseMessage.setMessage(messageError);
				responseMessage.setCode(400);
			}

			return responseMessage;
		} catch (IOException e) {
			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
	}

	public ByteArrayInputStream exportUsersToExcel() {
		List<User> users = (List<User>) userRepository.findAll();
		ByteArrayInputStream in = ExcelHelper.usersToExcel(users);
		return in;
	}

	@Override
	public ResponseMessage findAllMember(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Utils.sortUser(sortBy));
			Page<User> pageResponse = userRepository.findMember(paging);
			List<User> members = new ArrayList<User>();
			if (pageResponse != null && pageResponse.hasContent()) {
				members = pageResponse.getContent();
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(members);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
