package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time);
	ResponseMessage updateResultMatch(int resultId, int firstPoint, int secondPoint);
}
