package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;

public interface CompetitivePlayerBracketService {
	ResponseMessage getListPlayerBracket(int competitiveTypeId, int round);
}