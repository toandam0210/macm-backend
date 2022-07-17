package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.response.ResponseMessage;

public interface ExhibitionTeamService {
	ResponseMessage registerTeam(int exhibition_type_id, String name, List<String> listStudentID);
	ResponseMessage getTeamByType (int exhibitionTypeId);
	ResponseMessage getTop3TeamByType (int exhibitionTypeId);
}
