package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.ResponseMessage;

public interface TournamentScheduleService {
	ResponseMessage createPreviewTournamentSchedule(String tournamentName, String startDate, String finishDate, String startTime, String finishTime);
	ResponseMessage getListTournamentScheduleByTournament(int tournamentId);
	ResponseMessage createTournamenttSchedule(int eventId, List<ScheduleDto> listPreview, Boolean isOverwritten);
}
