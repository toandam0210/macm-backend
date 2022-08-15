package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface ExhibitionService {
	ResponseMessage getAllExhibitionType(int tournamentId);
	
	ResponseMessage getListNotJoinExhibition(int exhibitionTypeId);
	
	ResponseMessage registerTeam(int exhibition_type_id, String name, List<String> listStudentID);
	
	ResponseMessage getTeamByType (int exhibitionTypeId);
	
	ResponseMessage getTop3TeamByType (int exhibitionTypeId);
	
	ResponseMessage updateTeam(int exhibitionTeamId, List<User> teamUsers);
	
	ResponseMessage getListExhibitionResult(int exhibitionTypeId, String date);
	
	ResponseMessage updateExhibitionResult(int exhibitionTeamId, double score);
	
	ResponseMessage getExhibitionResultByType(int exhibitionTypeId);
}
