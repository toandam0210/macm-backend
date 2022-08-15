package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveResultService {
	ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint);
	ResponseMessage getResultByType(int competitiveTypeId);
}
