package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateResultMatch(int matchId, int areaId, String time, int firstPoint, int secondPoint);
}
