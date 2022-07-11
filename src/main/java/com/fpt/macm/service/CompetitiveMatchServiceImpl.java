package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
				newCompetitiveMatch.setFirstStudentId(listLoseSemi.get(0).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setSecondStudentId(listLoseSemi.get(1).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
				newCompetitiveMatch.setCompetitiveType(competitiveType);
				newCompetitiveMatch.setRound(round);
				newCompetitiveMatch.setFirstStudentId(listPlayers.get(0).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setSecondStudentId(listPlayers.get(1).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
			}
			for(int i = 0; i < freePlayer; i++) {
				CompetitiveMatch newCompetitiveMatch = new CompetitiveMatch();
				newCompetitiveMatch.setCompetitiveType(competitiveType);
				newCompetitiveMatch.setRound(round);
				newCompetitiveMatch.setFirstStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setSecondStudentId(null);
				newCompetitiveMatch.setCreatedBy("LinhLHN");
				newCompetitiveMatch.setCreatedOn(LocalDateTime.now());
				newCompetitiveMatch.setUpdatedBy("LinhLHN");
				newCompetitiveMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(newCompetitiveMatch);
				listMatch.add(newCompetitiveMatch);
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
				newCompetitiveMatch.setFirstStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				newCompetitiveMatch.setSecondStudentId(listPlayers.get(i + 1).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
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
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listByTypeAndRound(competitiveTypeId, 1);
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
