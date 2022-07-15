package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time);
	ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint);
	ResponseMessage spawnTimeAndArea(int tournamentId, List<Area> listArea);
}
