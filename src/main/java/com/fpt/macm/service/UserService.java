package com.fpt.macm.service;

import org.springframework.web.multipart.MultipartFile;

import com.fpt.macm.model.ResponseMessage;

public interface UserService {
	ResponseMessage addListMemberAndCollaboratorFromFileCsv(MultipartFile file) throws Exception;
	ResponseMessage getAllMemberAndCollaborator(int pageNo, int pageSize, String sortBy);
}
