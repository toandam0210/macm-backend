package com.fpt.macm.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.TournamentRepository;

@Service
public class CompetitiveTypeServiceImpl implements CompetitiveTypeService{

	@Autowired
	TournamentRepository tournamentRepository;
	
	@Override
	public ResponseMessage getAllType(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament getTournament = tournamentRepository.findById(tournamentId).get();
			responseMessage.setData(Arrays.asList(getTournament.getCompetitiveTypes()));
			responseMessage.setMessage("Danh sách các thể thức thi đấu");
		}
		catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
