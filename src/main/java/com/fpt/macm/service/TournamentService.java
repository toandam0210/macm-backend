package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.ResponseMessage;

public interface TournamentService {

	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto);
}
