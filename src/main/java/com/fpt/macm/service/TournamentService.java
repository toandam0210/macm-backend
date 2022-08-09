package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.TournamentCreateDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.response.ResponseMessage;

public interface TournamentService {

	ResponseMessage createTournament(TournamentCreateDto tournamentCreateDto, boolean isOverwritten);
	
	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto);

	ResponseMessage updateTournament(int tournamentId, TournamentDto tournamentDto);

	ResponseMessage deleteTournamentById(int tournamentId);

	ResponseMessage getTournamentById(int tournamentId);

	ResponseMessage getAllTournamentBySemester(String semester, int status);

	ResponseMessage getAllCompetitivePlayer(int tournamentId, double weightMin, double weightMax);

	ResponseMessage getAllExhibitionTeam(int tournamentId, int exhibitionTypeId);
	
	ResponseMessage getAllOrganizingCommitteeRoleByTournamentId(int tournamentId);
	
	ResponseMessage getAllExhibitionType(int tournamentId);
	
	ResponseMessage getAllTournamentPlayerPaymentStatus(int tournamentId);
	
	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatus(int tournamentId);
	
	ResponseMessage updateTournamentOrganizingCommitteePaymentStatus(int tournamentOrganizingCommitteeId);
	
	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatusReport(int tournamentId);
	
	ResponseMessage updateTournamentPlayerPaymentStatus(int tournamentPlayerId);
	
	ResponseMessage getAllTournamentPlayerPaymentStatusReport(int tournamentId);
	
	ResponseMessage getAllCompetitivePlayerByType(int tournamentId, int competitiveTypeId);
	
	ResponseMessage registerToJoinTournamentOrganizingComittee(int tournamentId, String studentId, int roleId);
	
	ResponseMessage registerToJoinTournamentCompetitiveType(int tournamentId, String studentId, double weight, int competitiveTypeId);
	
	ResponseMessage registerToJoinTournamentExhibitionType(int tournamentId, String studentId, int exhibitionTypeId, String teamName, List<ActiveUserDto> activeUsersDto);

	ResponseMessage getAllUserCompetitivePlayer(int tournamentId, String studentId);
	
	ResponseMessage getAllUserExhibitionPlayer(int tournamentId, String studentId);
	
	ResponseMessage getAllUserOrganizingCommittee(int tournamentId, String studentId);
	
	LocalDate getStartDate(int tournamentId);
	
	ResponseMessage getAllTournamentByStudentId(String studentId, String semester, int status);
	
	ResponseMessage addListTournamentOrganizingCommittee(String studentId, List<UserTournamentOrganizingCommitteeDto> users, int tournamentId);
	
	ResponseMessage getAllUserNotJoinTournament(int tournamentId);
	
	ResponseMessage deleteTournamentOrganizingCommittee(int tournamentOrganizingCommitteeId);
}
