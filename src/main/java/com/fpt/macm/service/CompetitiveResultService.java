package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time);
	ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint);
	ResponseMessage spawnTimeAndArea(int tournamentId);
}
