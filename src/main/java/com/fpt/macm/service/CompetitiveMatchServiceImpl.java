package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CompetitiveMatch;
import com.fpt.macm.model.CompetitivePlayerBracket;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;

@Service
public class CompetitiveMatchServiceImpl implements CompetitiveMatchService{
	
	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;
	
	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Override
	public ResponseMessage spawnMatchs(int competitiveTypeId, int round) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveType competitiveType = competitiveTypeRepository.findById(competitiveTypeId).get();
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listByTypeAndRound(competitiveTypeId, round);
			int numberPlayer = listPlayers.size();
			int nextPower = nextPower(numberPlayer);
			List<CompetitiveMatch> listMatch = new ArrayList<CompetitiveMatch>();
			int freePlayer = nextPower - numberPlayer;
			if(numberPlayer == 2) {
				List<CompetitivePlayerBracket> listSemiFinal = competitivePlayerBracketRepository.listByTypeAndRound(competitiveType.getId(), round - 1);
				List<CompetitivePlayerBracket> listLoseSemi = new ArrayList<CompetitivePlayerBracket>();
				for (CompetitivePlayerBracket competitivePlayerBracket : listSemiFinal) {
					boolean isWin = false;
					for (CompetitivePlayerBracket winSemi : listPlayers) {
						if(competitivePlayerBracket.getCompetitivePlayer().equals(winSemi.getCompetitivePlayer())) {
							isWin = true;
							break;
						}
					}
					if(!isWin) {
						listLoseSemi.add(competitivePlayerBracket);
					}
				}
				CompetitiveMatch newCompetitiveMatch = new CompetitiveMatch();
				newCompetitiveMatch.setCompetitiveType(competitiveType);
				newCompetitiveMatch.setRound(round);
				newCompetitiveMatch.setFirstPlayer(listLoseSemi.get(0));
				newCompetitiveMatch.setSecondPlayer(listLoseSemi.get(1));
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
				newCompetitiveMatch.setCompetitiveType(competitiveType);
				newCompetitiveMatch.setRound(round);
				newCompetitiveMatch.setFirstPlayer(listPlayers.get(0));
				newCompetitiveMatch.setSecondPlayer(listPlayers.get(1));
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
			}
			for(int i = 0; i < freePlayer; i++) {
				CompetitivePlayerBracket newCompetitivePlayerBracket = new CompetitivePlayerBracket();
				newCompetitivePlayerBracket.setCompetitiveType(competitiveType);
				newCompetitivePlayerBracket.setCompetitivePlayer(listPlayers.get(i).getCompetitivePlayer());
				newCompetitivePlayerBracket.setRound(round + 1);
				newCompetitivePlayerBracket.setNumerical_order_id(listPlayers.get(i).getNumerical_order_id());
				newCompetitivePlayerBracket.setCreatedBy("LinhLHN");
				newCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
				newCompetitivePlayerBracket.setUpdatedBy("LinhLHN");
				newCompetitivePlayerBracket.setUpdatedOn(LocalDateTime.now());
				competitivePlayerBracketRepository.save(newCompetitivePlayerBracket);
			}
			for(int i = freePlayer; i < numberPlayer; i+= 2) {
				CompetitiveMatch newCompetitiveMatch = new CompetitiveMatch();
				newCompetitiveMatch.setCompetitiveType(competitiveType);
				newCompetitiveMatch.setRound(round);
				newCompetitiveMatch.setFirstPlayer(listPlayers.get(i));
				newCompetitiveMatch.setSecondPlayer(listPlayers.get(i + 1));
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
			}
			responseMessage.setData(listMatch);
			responseMessage.setMessage("Danh sách trận đấu vòng " + round);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}
	
	public int nextPower(int number) {
		int power = 1;
		while (power < number) {
			power *= 2;
		}
		return power;
	}
}
