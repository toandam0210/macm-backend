package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface ExhibitionResultService {
	ResponseMessage spawnTimeAndArea(int tournamentId);
	ResponseMessage getListExhibitionResult(int exhibitionTypeId, String date);
	ResponseMessage updateExhibitionResult(int exhibitionTeamId, double score);
	ResponseMessage getExhibitionResultByType(int exhibitionTypeId);
}
