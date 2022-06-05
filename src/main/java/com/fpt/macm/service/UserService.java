package com.fpt.macm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;
import com.fpt.macm.model.User;

public interface UserService {
	ResponseMessage getUserByStudentId(String studentId);
	ResponseMessage getAllAdminForViceHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllAdminForHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUser(String studentId, UserDto userDto);
	ResponseMessage addListMemberAndCollaboratorFromFileCsv(MultipartFile file) throws Exception;
	ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy);
	ResponseMessage addAnMemberOrCollaborator(User user);
	ResponseMessage deleteAdmin(String studentId, Role role);
	ResponseMessage updateListStatusForUser(List<User> users);
}
