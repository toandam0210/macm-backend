package com.fpt.macm.service;

import java.time.LocalDateTime;
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

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ERole;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService {

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
	public ResponseMessage updateMemberToAdminByStudentId(String studentId, Role role) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			if (user.getRole().getName().equals(ERole.ROLE_Member.name())) {
				user.setRole(role);
				user.setUpdatedBy("toandv");
				user.setUpdatedOn(LocalDateTime.now());
				userRepository.save(user);
				responseMessage.setData(Arrays.asList(user));
				responseMessage.setMessage(Constant.MSG_002);
			} else {
				responseMessage.setMessage(Constant.MSG_003);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllAdmin(int pageNo, int pageSize, String sortBy) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<User> pageResponse = userRepository.findByRoleId(paging);
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
	public ResponseMessage updateAdminToMemberByStudentId(String studentId, Role role) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<User> userOp = userRepository.findByStudentId(studentId);
			User user = userOp.get();
			user.setRole(role);
			user.setUpdatedBy("toandv");
			user.setUpdatedOn(LocalDateTime.now());
			userRepository.save(user);
			responseMessage.setData(Arrays.asList(user));
			responseMessage.setMessage(Constant.MSG_004);

		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateAdmin(String studentId, UserDto userDto) {
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

}
