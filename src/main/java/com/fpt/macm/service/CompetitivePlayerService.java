package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitivePlayerService {
	ResponseMessage addNewCompetitivePlayer (int userId, int tournamentId, double weight);
	ResponseMessage updateWeightForCompetitivePlayer (int competitivePlayerId, double weight);
	ResponseMessage deleteCompetitivePlayer (int competitivePlayerId);
}
