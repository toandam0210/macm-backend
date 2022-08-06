package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.TournamentRepository;

@Service
public class ExhibitionTypeServiceImpl implements ExhibitionTypeService{

	@Autowired
	TournamentRepository tournamentRepository;
	
	@Override
	public ResponseMessage getAllExhibitionType(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament getTournament = tournamentRepository.findById(tournamentId).get();
			Set<ExhibitionType> listType = getTournament.getExhibitionTypes();
			List<ExhibitionType> listTypeResult = new ArrayList<ExhibitionType>();
			for (ExhibitionType exhibitionType : listType) {
				listTypeResult.add(exhibitionType);
			}
			responseMessage.setData(listTypeResult);
			responseMessage.setMessage("Danh sách thể thức thi đấu biểu diễn");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
