package com.fpt.macm.service;

import java.util.Set;

import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.response.ResponseMessage;


public interface CompetitiveTypeService {
	ResponseMessage getAllType(int tournamentId);
	Set<CompetitiveType> getAllTypeByTournament(int tournamentId);
}
