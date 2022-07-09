package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface CompetitiveMatchService {
	ResponseMessage spawnMatchs (int competitiveTypeId, int round);
}
