package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.response.ResponseMessage;

public interface CompetitiveMatchService {
	ResponseMessage spawnMatchs (int competitiveTypeId);
	ResponseMessage listMatchs(int competitiveTypeId);
	ResponseMessage previewMatchsPlayer(int competitiveTypeId);
	ResponseMessage updateListMatchsPlayer(List<CompetitiveMatchDto> listUpdated);
}
