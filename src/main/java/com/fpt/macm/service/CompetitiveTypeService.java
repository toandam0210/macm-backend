package com.fpt.macm.service;

import java.util.List;
import java.util.Set;

import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ResponseMessage;

public interface CompetitiveTypeService {
	ResponseMessage getAllType(int tournamentId);
	Set<CompetitiveType> getAllTypeByTournament(int tournamentId);
}
