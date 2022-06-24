package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ExhibitionType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.repository.TournamentRepository;

@Service
public class TournamentServiceImpl implements TournamentService{
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	SemesterService semesterService;

	@Override
	public ResponseMessage createTournament(Tournament tournament) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			tournament.setSemester(semester.getName());
			tournament.setCreatedBy("toandv");
			tournament.setCreatedOn(LocalDateTime.now());
			Set<CompetitiveType> competitiveTypes = tournament.getCompetitiveTypes();
			Set<ExhibitionType> exhibitionTypes = tournament.getExhibitionTypes();
			for (CompetitiveType competitiveType : competitiveTypes) {
				competitiveType.setCreatedBy("toandv");
				competitiveType.setCreatedOn(LocalDateTime.now());
			}
			for (ExhibitionType exhibitionType : exhibitionTypes) {
				exhibitionType.setCreatedBy("toandv");
				exhibitionType.setCreatedOn(LocalDateTime.now());
			}
			tournamentRepository.save(tournament);
			responseMessage.setData(Arrays.asList(tournament));
			responseMessage.setCode(200);
			responseMessage.setMessage(Constant.MSG_097);
			
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
