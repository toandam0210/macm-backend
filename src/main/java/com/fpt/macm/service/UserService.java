package com.fpt.macm.service;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.model.dto.InforInQrCode;
import com.fpt.macm.model.dto.UserDto;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface UserService {
	ResponseMessage getUserByStudentId(String studentId);
	ResponseMessage getAllAdminForViceHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllAdminForHeadClub(int pageNo, int pageSize, String sortBy);
	ResponseMessage updateUser(String studentId, UserDto userDto);
	ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy);
	ResponseMessage addAnMemberOrCollaborator(UserDto userDto);
	ResponseMessage deleteAdmin(String studentId, String semester);
	ResponseMessage updateStatusForUser(String studentId, String semester);
	ResponseMessage searchUserByStudentIdOrName(String inputSearch,int pageNo, int pageSize, String sortBy);
//	ResponseMessage userLogin();
	ResponseMessage addUsersFromExcel(MultipartFile file);
	ByteArrayInputStream exportUsersToExcel(List<User> users);
	ResponseMessage findAllMember(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllUser();
	ResponseMessage getMembersBySemester(String semester);
	ResponseMessage getAdminBySemester(String semester);
	ResponseMessage searchByMultipleField(List<UserDto> userDtos, String name, String studentId, String email, String gender, Integer generation, Integer roleId, String isActive, String dateFrom, String dateTo);
	ResponseMessage generateQrCode(InforInQrCode inforInQrCode);
	ResponseMessage getAllActiveMemberAndCollaborator();
	ResponseMessage getAllUserAttendanceStatus(String studentId);
}
