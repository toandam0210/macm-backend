package com.fpt.macm.service;

import com.fpt.macm.model.entity.RoleTournament;
import com.fpt.macm.model.response.ResponseMessage;

public interface RoleTournamentService {

	ResponseMessage getAllRoleTournament();

	ResponseMessage addNewRoleTournament(RoleTournament roleTournament);

	ResponseMessage updateRoleTournamentName(int roleTournamentId, RoleTournament roleTournament);

	ResponseMessage deleteRoleTournament(int roleTournamentId);
}
