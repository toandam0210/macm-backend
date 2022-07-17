package com.fpt.macm.service;

import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateTimeAndArea(int matchId, CompetitiveResult newResult);
	ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint);
	ResponseMessage spawnTimeAndArea(int tournamentId);
}
