package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitivePlayerBracketService {
	ResponseMessage getListPlayerBracket(int competitiveTypeId);
}
