package com.fpt.macm.service;

import com.fpt.macm.dto.UserDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;

public interface AdminService {
	ResponseMessage getUserByStudentId(String studentId);
	ResponseMessage updateMemberToAdminByStudentId(String studentId, Role role);
	ResponseMessage getAllAdmin(int pageNo, int pageSize, String sortBy);
	ResponseMessage updateAdminToMemberByStudentId(String studentId, Role role);
	ResponseMessage updateAdmin(String studentId, UserDto userDto);
}
