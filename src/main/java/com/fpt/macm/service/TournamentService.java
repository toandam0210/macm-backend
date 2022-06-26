package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.dto.TournamentDto;
import com.fpt.macm.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;

public interface TournamentService {

	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto);

	ResponseMessage createTournament(Tournament tournament);

	ResponseMessage updateTournament(int id, TournamentDto tournamentDto);

	ResponseMessage deleteTournamentById(int id);

	ResponseMessage getTournamentById(int id);

	ResponseMessage getAllTournamentBySemester(String semester);

	ResponseMessage getAllCompetitivePlayer(int tournamentId, double weightMin, double weightMax);

	ResponseMessage getAllExhibitionTeam(int tournamentId, int exhibitionTypeId);
	
	ResponseMessage getAllOrganizingCommitteeRole();
}
