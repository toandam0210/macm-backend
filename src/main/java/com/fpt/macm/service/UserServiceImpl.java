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
import com.fpt.macm.model.AdminSemester;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ERole;
import com.fpt.macm.model.MemberSemester;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.AdminSemesterRepository;
import com.fpt.macm.repository.MemberSemesterRepository;
import com.fpt.macm.repository.RoleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	MemberSemesterRepository memberSemesterRepository;

	@Autowired
	AdminSemesterRepository adminSemesterRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	SemesterService semesterService;

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
			List<UserDto> usersDto = new ArrayList<UserDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			for (User admin : admins) {
				UserDto userDto = convertUserToUserDto(admin);
				usersDto.add(userDto);
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(usersDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setTotalResult(usersDto.size());
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
			List<UserDto> usersDto = new ArrayList<UserDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				admins = pageResponse.getContent();
			}
			for (User admin : admins) {
				UserDto userDto = convertUserToUserDto(admin);
				usersDto.add(userDto);
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(usersDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setTotalResult(usersDto.size());
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
					}
					if (currentUser.getEmail().equals(userDto.getEmail())) {
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
					user.setGeneration(userDto.getGeneration());
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
			List<UserDto> usersDto = new ArrayList<UserDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				users = pageResponse.getContent();
			}
			for (User user : users) {
				UserDto userDto = convertUserToUserDto(user);
				usersDto.add(userDto);
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(usersDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setTotalResult(usersDto.size());
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
				}
				if (user.getEmail().equals(userDto.getEmail())) {
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
				user.setGeneration(userDto.getGeneration());
				Optional<Role> roleOptional = roleRepository.findById(userDto.getRoleId());
				if (roleOptional.isPresent()) {
					user.setRole(roleOptional.get());
				}
				user.setActive(true);
				user.setCreatedBy("toandv");
				user.setCreatedOn(LocalDate.now());
				userRepository.save(user);
				if (userDto.getRoleId() > 9 && userDto.getRoleId() < 13) {
					MemberSemester memberSemester = new MemberSemester();
					memberSemester.setUser(user);
					memberSemester.setStatus(true);
					Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
					memberSemester.setSemester(semester.getName());
					memberSemesterRepository.save(memberSemester);
				}
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
	public ResponseMessage deleteAdmin(String studentId, String semester) {
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

			AdminSemester adminSemester = adminSemesterRepository.findByUserId(user.getId(), semester).get();
			adminSemesterRepository.delete(adminSemester);
			userRepository.save(user);
			MemberSemester memberSemester = new MemberSemester();
			memberSemester.setUser(user);
			memberSemester.setStatus(true);
			Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			memberSemester.setSemester(currentSemester.getName());
			memberSemesterRepository.save(memberSemester);
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_004);

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateStatusForUser(String studentId, String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = new User();
			Optional<User> userOptional = userRepository.findByStudentId(studentId);
			if (userOptional.isPresent()) {
				user = userOptional.get();
				user.setActive(!user.isActive());
				Optional<MemberSemester> memberSemesterOp = memberSemesterRepository
						.findByUserIdAndSemester(user.getId(), semester);
				if (memberSemesterOp.isPresent()) {
					MemberSemester memberSemester = memberSemesterOp.get();
					memberSemester.setStatus(!memberSemester.isStatus());
					memberSemesterRepository.save(memberSemester);
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
			List<UserDto> usersDto = new ArrayList<UserDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				users = pageResponse.getContent();
			}
			for (User user : users) {
				UserDto userDto = convertUserToUserDto(user);
				usersDto.add(userDto);
			}
			responseMessage.setMessage(Constant.MSG_001);
			responseMessage.setData(usersDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setTotalResult(usersDto.size());
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
					}
					if (userFromExcel.getEmail().equals(user.getEmail())) {
						checkDuplicateEmail = true;
					}
				}
				if (!checkDuplicateEmail && !checkDuplicateStudentId) {
					userFromExcel.setCreatedOn(LocalDate.now());
					userFromExcel.setCreatedBy("toandv");
					userRepository.saveAll(usersFromExcel);
					MemberSemester memberSemester = new MemberSemester();
					memberSemester.setUser(userFromExcel);
					memberSemester.setStatus(true);
					Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
					memberSemester.setSemester(currentSemester.getName());
					memberSemesterRepository.save(memberSemester);
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

	@Override
	public ResponseMessage getAllUser() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> users = (List<User>) userRepository.findAll();
			if (users.size() > 0) {
				responseMessage.setData(users);
				responseMessage.setTotalResult(users.size());
				responseMessage.setMessage(Constant.MSG_001);
			} else {
				responseMessage.setMessage("Không có người dùng");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getMembersBySemester(String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<MemberSemester> statusSemesters = memberSemesterRepository.findBySemester(semester);
			Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			List<User> collaborators = userRepository.findCollaborator();
			List<UserDto> usersDto = new ArrayList<UserDto>();
			int countDeactive = 0;
			if (statusSemesters.size() > 0) {
				if (collaborators.size() > 0) {
					for (User collaborator : collaborators) {
						if (semester.equals(currentSemester.getName())) {
							UserDto collaboratorDto = convertUserToUserDto(collaborator);
							usersDto.add(collaboratorDto);
							if (!collaborator.isActive()) {
								countDeactive++;
							}
						}
					}
				}
				for (MemberSemester statusSemester : statusSemesters) {
					Optional<User> userOp = userRepository.findByStudentId(statusSemester.getUser().getStudentId());
					User user = userOp.get();
					user.setActive(statusSemester.isStatus());
					if (!user.isActive()) {
						countDeactive++;
					}
					UserDto userDto = convertUserToUserDto(user);
					usersDto.add(userDto);
				}
				responseMessage.setData(usersDto);
				responseMessage.setMessage(Constant.MSG_001);
				responseMessage.setTotalResult(usersDto.size());
				responseMessage.setTotalDeactive(countDeactive);
				responseMessage.setTotalActive(usersDto.size() - countDeactive);
			} else {
				responseMessage.setMessage("Không có dữ liệu");
			}

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAdminBySemester(String semester) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<AdminSemester> statusSemesters = adminSemesterRepository.findBySemester(semester);
			List<UserDto> usersDto = new ArrayList<UserDto>();
			if (statusSemesters.size() > 0) {
				for (AdminSemester statusSemester : statusSemesters) {
					Optional<User> userOp = userRepository.findByStudentId(statusSemester.getUser().getStudentId());
					User user = userOp.get();
					user.setRole(statusSemester.getRole());
					UserDto userDto = convertUserToUserDto(user);
					usersDto.add(userDto);
				}
				responseMessage.setData(usersDto);
				responseMessage.setMessage(Constant.MSG_001);
				responseMessage.setTotalResult(usersDto.size());
			} else {
				responseMessage.setMessage("Không có dữ liệu");
			}

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private UserDto convertUserToUserDto(User user) {
		UserDto userDto = new UserDto();
		userDto.setId(user.getId());
		userDto.setStudentId(user.getStudentId());
		userDto.setEmail(user.getEmail());
		userDto.setGender(user.isGender());
		userDto.setGeneration(user.getGeneration());
		userDto.setImage(user.getImage());
		userDto.setName(user.getName());
		userDto.setPhone(user.getPhone());
		userDto.setActive(user.isActive());
		userDto.setCurrentAddress(user.getCurrentAddress());
		userDto.setDateOfBirth(user.getDateOfBirth());
		userDto.setRoleId(user.getRole().getId());
		userDto.setRoleName(Utils.convertRoleFromDbToExcel(user.getRole()));
		return userDto;
	}
}
