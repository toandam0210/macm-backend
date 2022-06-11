package com.fpt.macm.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;

public interface UserService {
	ResponseMessage getUserByStudentId(String studentId);
	ResponseMessage getAllAdminForViceHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllAdminForHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUser(String studentId, UserDto userDto);
	ResponseMessage addListMemberAndCollaboratorFromFileCsv(MultipartFile file) throws Exception;
	ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy);
	ResponseMessage addAnMemberOrCollaborator(UserDto userDto);
	ResponseMessage deleteAdmin(String studentId);
	ResponseMessage updateStatusForUser(String studentId);
	void export(HttpServletResponse response)throws IOException;
	ResponseMessage searchUserByStudentIdOrName(String inputSearch,int pageNo, int pageSize, String sortBy);
//	ResponseMessage userLogin();
}
