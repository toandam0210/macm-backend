package com.fpt.macm.service;

import java.time.LocalDate;

import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;

public interface TournamentScheduleService {
	ResponseMessage createPreviewTournamentSchedule(String tournamentName, String startDate, String finishDate, String startTime, String finishTime);
	ResponseMessage getListTournamentScheduleByTournament(int tournamentId);
//	ResponseMessage createTournamentSchedule(int tournamentId, List<ScheduleDto> listPreview, Boolean isOverwritten);
	ResponseMessage createTournamentSession(int tournamentId, TournamentSchedule tournamentSchedule);
	ResponseMessage updateTournamentSession(int tournamentId, TournamentSchedule tournamentSchedule);
	ResponseMessage deleteTournamentSession(int tournamentId);
	TournamentSchedule getTournamentSessionByDate(LocalDate date);
	ResponseMessage getAllTournamentSchedule();
	
}
