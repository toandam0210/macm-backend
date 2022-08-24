package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.model.dto.ActiveUserDto;
import com.fpt.macm.model.dto.TournamentCreateDto;
import com.fpt.macm.model.dto.TournamentDto;
import com.fpt.macm.model.dto.TournamentOrganizingCommitteeDto;
import com.fpt.macm.model.dto.TournamentRoleDto;
import com.fpt.macm.model.dto.UserTournamentOrganizingCommitteeDto;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.response.ResponseMessage;

public interface TournamentService {

	ResponseMessage createTournament(String studentId, TournamentCreateDto tournamentCreateDto, boolean isOverwritten);

	ResponseMessage getAllTournamentOrganizingCommitteeByTournamentId(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteeRole(
			List<TournamentOrganizingCommitteeDto> tournamentOrganizingCommitteesDto);

	ResponseMessage updateTournament(int tournamentId, TournamentDto tournamentDto);

	ResponseMessage deleteTournamentById(String studentId, int tournamentId);

	ResponseMessage getTournamentById(int tournamentId);

	ResponseMessage getAllTournamentBySemester(String semester, int status);

	ResponseMessage getAllCompetitivePlayer(int tournamentId, double weightMin, double weightMax);

	ResponseMessage getAllExhibitionTeam(int tournamentId, int exhibitionTypeId);

	ResponseMessage getAllOrganizingCommitteeRoleByTournamentId(int tournamentId);

	ResponseMessage getAllExhibitionType(int tournamentId);

	ResponseMessage getAllTournamentPlayerPaymentStatus(int tournamentId);

	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatus(int tournamentId);

	ResponseMessage updateTournamentOrganizingCommitteePaymentStatus(String studentId,
			int tournamentOrganizingCommitteeId);

	ResponseMessage getAllTournamentOrganizingCommitteePaymentStatusReport(int tournamentId);

	ResponseMessage updateTournamentPlayerPaymentStatus(String studentId, int tournamentPlayerId);

	ResponseMessage getAllTournamentPlayerPaymentStatusReport(int tournamentId);

	ResponseMessage getAllCompetitivePlayerByType(int tournamentId, int competitiveTypeId);

	ResponseMessage registerToJoinTournamentOrganizingComittee(int tournamentId, String studentId, int roleId);
	
	ResponseMessage acceptRequestToJoinTournamentOrganizingCommittee(int tournamentOrganizingCommitteeId);
	
	ResponseMessage declineRequestToJoinTournamentOrganizingCommittee(int tournamentOrganizingCommitteeId);

	ResponseMessage registerToJoinTournamentCompetitiveType(int tournamentId, String studentId, double weight,
			int competitiveTypeId);

	ResponseMessage registerToJoinTournamentExhibitionType(int tournamentId, String studentId, int exhibitionTypeId,
			String teamName, List<ActiveUserDto> activeUsersDto);

	ResponseMessage getAllUserCompetitivePlayer(int tournamentId, String studentId);

	ResponseMessage getAllUserExhibitionPlayer(int tournamentId, String studentId);

	ResponseMessage getAllUserOrganizingCommittee(int tournamentId, String studentId);

	LocalDate getStartDate(int tournamentId);

	LocalDate getEndDate(int tournamentId);

	ResponseMessage getAllTournamentByStudentId(String studentId, String semester, int status);

	ResponseMessage addListTournamentOrganizingCommittee(String studentId,
			List<UserTournamentOrganizingCommitteeDto> users, int tournamentId);

	ResponseMessage getAllUserNotJoinTournament(int tournamentId);

	ResponseMessage deleteTournamentOrganizingCommittee(int tournamentOrganizingCommitteeId);

	ResponseMessage getResultOfTournament(int tournamentId);

	ResponseMessage spawnTimeAndArea(int tournamentId);

	ResponseMessage updateAfterTournament(String studentId, int tournamentId, double totalAmountActual);

	ResponseMessage updateTimeAndAreaCompetitive(int matchId, CompetitiveResult newResult);

	ResponseMessage updateTimeAndAreaExhibition(int teamId, ExhibitionResult newResult);

	ResponseMessage editRoleTournament(int tournamentId, List<TournamentRoleDto> rolesTournamentDto);
	
}
