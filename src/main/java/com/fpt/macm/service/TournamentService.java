package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.response.ResponseMessage;

public interface TournamentService {

	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto);

	ResponseMessage createTournament(Tournament tournament);

	ResponseMessage updateTournament(int id, TournamentDto tournamentDto);

	ResponseMessage deleteTournamentById(int id);

	ResponseMessage getTournamentById(int id);

	ResponseMessage getAllTournamentBySemester(String semester, int status);

	ResponseMessage getAllCompetitivePlayer(int tournamentId, double weightMin, double weightMax);

	ResponseMessage getAllExhibitionTeam(int tournamentId, int exhibitionTypeId);
	
	ResponseMessage getAllOrganizingCommitteeRole();
	
	ResponseMessage getAllExhibitionType(int tournamentId);
	
	ResponseMessage acceptRequestOrganizingCommittee(int tournamentOrganizingCommitteeId);
	
	ResponseMessage declineRequestOrganizingCommittee(int tournamentOrganizingCommitteeId);
	
	ResponseMessage getAllTournamentPlayerPaymentStatus(int tournamentId);
	
	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatus(int tournamentId);
	
	ResponseMessage updateTournamentOrganizingCommitteePaymentStatus(int tournamentOrganizingCommitteeId);
	
	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatusReport(int tournamentId);
	
	ResponseMessage updateTournamentPlayerPaymentStatus(int tournamentPlayerId);
	
	ResponseMessage getAllTournamentPlayerPaymentStatusReport(int tournamentId);
	
	ResponseMessage getAllCompetitivePlayerByType(int tournamentId, int competitiveTypeId);
	
	ResponseMessage registerToJoinTournamentOrganizingComittee(int tournamentId, String studentId, int roleId);
	
	ResponseMessage registerToJoinTournamentCompetitiveType(int tournamentId, String studentId, double weight, int competitiveTypeId);
	
	List<Tournament> listTournamentsByRegistrationPlayerDeadline(LocalDateTime playerDeadline);
	
	ResponseMessage registerToJoinTournamentExhibitionType(int tournamentId, String studentId, int exhibitionTypeId, String teamName, List<ActiveUserDto> activeUsersDto);

	ResponseMessage getAllUserCompetitivePlayer(int tournamentId, String studentId);
	
	ResponseMessage getAllUserExhibitionPlayer(int tournamentId, String studentId);
	
	ResponseMessage getAllUserOrganizingCommittee(int tournamentId, String studentId);
	
	LocalDate getStartDate(int tournamentId);
}
