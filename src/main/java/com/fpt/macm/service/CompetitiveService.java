package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveService {
	
	ResponseMessage getAllCompetitiveType(int tournamentId);

	ResponseMessage getListNotJoinCompetitive(int competitiveTypeId);

	ResponseMessage listMatchs(int competitiveTypeId);

	ResponseMessage updateListMatchsPlayer(List<CompetitiveMatchDto> listUpdated);

	void autoSpawnMatchs(int competitiveTypeId);

	ResponseMessage addNewCompetitivePlayer(List<User> users, int competitiveTypeId);

	ResponseMessage updateWeightForCompetitivePlayer(int competitivePlayerId, double weight);

	ResponseMessage deleteCompetitivePlayer(int competitivePlayerId);

	ResponseMessage getListPlayer(int competitiveTypeId);

	ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint);

	ResponseMessage getResultByType(int competitiveTypeId);

}
