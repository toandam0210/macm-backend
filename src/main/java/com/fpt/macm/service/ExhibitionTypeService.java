package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface ExhibitionTypeService {
	ResponseMessage getAllExhibitionType(int tournamentId);
}
