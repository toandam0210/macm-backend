package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitivePlayerService {
	ResponseMessage addNewCompetitivePlayer (List<User> users, int competitiveTypeId);
	ResponseMessage updateWeightForCompetitivePlayer (int competitivePlayerId, double weight);
	ResponseMessage deleteCompetitivePlayer (int competitivePlayerId);
	ResponseMessage listUserNotJoinCompetitive(int tournamentId);
}
