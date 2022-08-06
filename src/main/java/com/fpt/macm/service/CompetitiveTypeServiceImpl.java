package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.CompetitiveType;
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
			List<CompetitiveType> listType = new ArrayList<CompetitiveType>();
			Set<CompetitiveType> getAllTypeByTournament = getAllTypeByTournament(tournamentId);
			for (CompetitiveType competitiveType : getAllTypeByTournament) {
				listType.add(competitiveType);
			}
			Collections.sort(listType, new Comparator<CompetitiveType>() {
				@Override
				public int compare(CompetitiveType o1, CompetitiveType o2) {
					// TODO Auto-generated method stub
					if(o1.isGender() == o2.isGender()) {
						return o1.getWeightMin() - o2.getWeightMin() > 0 ? 1 : -1;
					}
					return o1.isGender()? -1 : 1;
				}
			});
			responseMessage.setData(Arrays.asList(listType));
			responseMessage.setMessage("Danh sách các thể thức thi đấu");
		}
		catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public Set<CompetitiveType> getAllTypeByTournament(int tournamentId) {
		// TODO Auto-generated method stub
		try {
			Tournament getTournament = tournamentRepository.findById(tournamentId).get();
			return getTournament.getCompetitiveTypes();
		}
		catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
