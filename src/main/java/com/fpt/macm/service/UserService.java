package com.fpt.macm.service;

import java.io.ByteArrayInputStream;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;

public interface UserService {
	ResponseMessage getUserByStudentId(String studentId);
	ResponseMessage getAllAdminForViceHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllAdminForHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUser(String studentId, UserDto userDto);
	ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy);
	ResponseMessage addAnMemberOrCollaborator(UserDto userDto);
	ResponseMessage deleteAdmin(String studentId);
	ResponseMessage updateStatusForUser(String studentId);
	ResponseMessage searchUserByStudentIdOrName(String inputSearch,int pageNo, int pageSize, String sortBy);
//	ResponseMessage userLogin();
	ResponseMessage addUsersFromExcel(MultipartFile file);
	ByteArrayInputStream exportUsersToExcel();
	ResponseMessage findAllMember(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllUser();
	ResponseMessage getMembersBySemester(String semester);
	ResponseMessage getAdminBySemester(String semester);
}
