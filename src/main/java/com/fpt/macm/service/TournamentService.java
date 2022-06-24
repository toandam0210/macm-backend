package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface TournamentService {

	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);
	
}
