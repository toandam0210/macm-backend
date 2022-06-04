package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Role;

public interface UserService {
	ResponseMessage getStudentByStudentId(String studentId);
	ResponseMessage updateMemberToAdminByStudentId(String studentId, Role role);
}
