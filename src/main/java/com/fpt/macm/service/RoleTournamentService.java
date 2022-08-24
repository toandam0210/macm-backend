package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface RoleTournamentService {

	ResponseMessage getAllRoleTournament();

	ResponseMessage addNewRoleTournament(String newName);

	ResponseMessage updateRoleTournamentName(int roleTournamentId, String newName);

	ResponseMessage updateStatusRoleTournament(int roleTournamentId);
}
