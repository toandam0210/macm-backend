package com.fpt.macm.service;

import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;

public interface TournamentService {
	ResponseMessage createTournament(Tournament tournament);
}
