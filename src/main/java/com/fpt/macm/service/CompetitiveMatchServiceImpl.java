package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.CompetitiveMatchDto;
import com.fpt.macm.model.CompetitiveMatch;
import com.fpt.macm.model.CompetitivePlayerBracket;
import com.fpt.macm.model.CompetitiveResult;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.User;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class CompetitiveMatchServiceImpl implements CompetitiveMatchService{
	
	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;
	
	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Autowired
	CompetitiveResultRepository competitiveResultRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseMessage spawnMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveType competitiveType = competitiveTypeRepository.findById(competitiveTypeId).get();
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			int numberPlayer = listPlayers.size();
			if(numberPlayer < 4) {
				responseMessage.setMessage("Số lượng tuyển thủ không đạt 4 người trở lên, không thi đấu hạng cân này");
			}
			else {
				int nextPower = nextPower(numberPlayer);
				int freePlayer = nextPower - numberPlayer;
				int round = 1;
				while (nextPower > 1) {
					int countMatch = nextPower/2;
					for(int i = 0; i < countMatch; i++) {
						CompetitiveMatch newMatch = new CompetitiveMatch();
						newMatch.setRound(round);
						newMatch.setCompetitiveType(competitiveType);
						newMatch.setCreatedBy("LinhLHN");
						newMatch.setCreatedOn(LocalDateTime.now());
						newMatch.setUpdatedBy("LinhLHN");
						newMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(newMatch);
					}
					nextPower = countMatch;
					round++;
				}
				List<CompetitiveMatch> listMatch = competitiveMatchRepository.listMatchsByTypeDesc(competitiveTypeId);
				for(int i = 1; i < listMatch.size(); i++) {
					CompetitiveMatch getMatch = listMatch.get(i);
					getMatch.setNextIsFirst(i%2 == 0);
					getMatch.setNextMatchId(listMatch.get(i/2).getId());
					competitiveMatchRepository.save(getMatch);
				}
				List<CompetitiveMatch> listMatchRound1 = competitiveMatchRepository.listMatchsByTypeAndRound(competitiveTypeId, 1);
				int currentMatch = 0;
				for(int i = 0; i < freePlayer; i++) {
					CompetitiveMatch getMatch = listMatchRound1.get(currentMatch);
					getMatch.setCompetitiveType(competitiveType);
					getMatch.setRound(1);
					getMatch.setFirstStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
					getMatch.setSecondStudentId(null);
					getMatch.setCreatedBy("LinhLHN");
					getMatch.setCreatedOn(LocalDateTime.now());
					getMatch.setUpdatedBy("LinhLHN");
					getMatch.setUpdatedOn(LocalDateTime.now());
					competitiveMatchRepository.save(getMatch);
					listMatch.add(getMatch);
					currentMatch++;
				}
				for(int i = freePlayer; i < numberPlayer; i+= 2) {
					CompetitiveMatch newCompetitiveMatch = listMatchRound1.get(currentMatch);
					newCompetitiveMatch.setCompetitiveType(competitiveType);
					newCompetitiveMatch.setRound(1);
					newCompetitiveMatch.setFirstStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
					newCompetitiveMatch.setSecondStudentId(listPlayers.get(i + 1).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
					newCompetitiveMatch.setCreatedBy("LinhLHN");
					newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
					newCompetitiveMatch.setUpdatedBy("LinhLHN");
					newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
					competitiveMatchRepository.save(newCompetitiveMatch);
					listMatch.add(newCompetitiveMatch);
					currentMatch++;
				}
				responseMessage.setData(listMatch);
				responseMessage.setMessage("Danh sách trận đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage listMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitiveMatch> listMatchs = competitiveMatchRepository.listMatchsByType(competitiveTypeId);
			List<CompetitiveMatchDto> listMatchDto = new ArrayList<CompetitiveMatchDto>();
			for (CompetitiveMatch competitiveMatch : listMatchs) {
				CompetitiveMatchDto newCompetitiveMatchDto = new CompetitiveMatchDto();
				newCompetitiveMatchDto.setId(competitiveMatch.getId());
				newCompetitiveMatchDto.setRound(competitiveMatch.getRound());
				if(competitiveMatch.getFirstStudentId() != null) {
					newCompetitiveMatchDto.setFirstStudentId(competitiveMatch.getFirstStudentId());
					User fisrtUser = userRepository.getByStudentId(competitiveMatch.getFirstStudentId());
					newCompetitiveMatchDto.setFirstNameAndId(fisrtUser.getName() + " - " + fisrtUser.getStudentId());
				}
				else {
					newCompetitiveMatchDto.setFirstStudentId(null);
					newCompetitiveMatchDto.setFirstNameAndId(null);
				}
				if(competitiveMatch.getSecondStudentId() != null) {
					newCompetitiveMatchDto.setSecondStudentId(competitiveMatch.getSecondStudentId());
					User secondUser = userRepository.getByStudentId(competitiveMatch.getSecondStudentId());
					newCompetitiveMatchDto.setSecondNameAndId(secondUser.getName() + " - " + secondUser.getStudentId());
				}
				else {
					newCompetitiveMatchDto.setSecondStudentId(null);
					newCompetitiveMatchDto.setSecondNameAndId(null);
				}
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(competitiveMatch.getId());
				if(getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					newCompetitiveMatchDto.setArea(getResult.getArea().getName());
					newCompetitiveMatchDto.setTime(getResult.getTime());
					if(getResult.getFirstPoint() != null) {
						newCompetitiveMatchDto.setFirstPoint(getResult.getFirstPoint());
					}
					if(getResult.getSecondPoint() != null) {
						newCompetitiveMatchDto.setSecondPoint(getResult.getSecondPoint());
					}
				}
				listMatchDto.add(newCompetitiveMatchDto);
			}
			responseMessage.setData(listMatchDto);
			responseMessage.setMessage("Danh sách trận đấu");
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			responseMessage.setTotalResult(maxRound(listPlayers.size()));
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
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
	
	public int maxRound(int number) {
		int i = 1;
		int power = 2;
		while (power < number) {
			power *= 2;
			i++;
		}
		return i;
	}
}
