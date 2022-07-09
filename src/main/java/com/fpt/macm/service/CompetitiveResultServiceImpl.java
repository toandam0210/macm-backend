package com.fpt.macm.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Area;
import com.fpt.macm.model.CompetitiveMatch;
import com.fpt.macm.model.CompetitivePlayerBracket;
import com.fpt.macm.model.CompetitiveResult;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CompetitiveResultServiceImpl implements CompetitiveResultService{

	@Autowired
	CompetitiveResultRepository competitiveResultRepository;
	
	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;
	
	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Override
	public ResponseMessage updateResultMatch(int matchId, int areaId, String time, int firstPoint, int secondPoint) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveMatch getMatch = competitiveMatchRepository.getById(matchId);
			Area area = areaRepository.getById(areaId);
			LocalDateTime getTime = Utils.ConvertStringToLocalDateTime(time);
			CompetitiveResult competitiveResult = new CompetitiveResult();
			competitiveResult.setMatch(getMatch);
			competitiveResult.setArea(area);
			competitiveResult.setTime(getTime);
			competitiveResult.setFirstPoint(firstPoint);
			competitiveResult.setSecondPoint(secondPoint);
			competitiveResult.setCreatedBy("LinhLHN");
			competitiveResult.setCreatedOn(LocalDateTime.now());
			competitiveResult.setUpdatedBy("LinhLHN");
			competitiveResult.setUpdatedOn(LocalDateTime.now());
			competitiveResultRepository.save(competitiveResult);
			CompetitivePlayerBracket winCompetitivePlayerBracket = new CompetitivePlayerBracket();
			winCompetitivePlayerBracket.setCompetitiveType(getMatch.getCompetitiveType());
			winCompetitivePlayerBracket.setRound(getMatch.getRound() + 1);
			if(competitiveResult.getFirstPoint() > competitiveResult.getSecondPoint()) {
				winCompetitivePlayerBracket.setCompetitivePlayer(getMatch.getFirstPlayer().getCompetitivePlayer());
				winCompetitivePlayerBracket.setNumerical_order_id(getMatch.getFirstPlayer().getNumerical_order_id());
				responseMessage.setMessage("Tuyển thủ thứ nhất thắng");
			}
			else {
				winCompetitivePlayerBracket.setCompetitivePlayer(getMatch.getSecondPlayer().getCompetitivePlayer());
				winCompetitivePlayerBracket.setNumerical_order_id(getMatch.getSecondPlayer().getNumerical_order_id());
				responseMessage.setMessage("Tuyển thủ thứ hai thắng");
			}
			winCompetitivePlayerBracket.setCreatedBy("LinhLHN");
			winCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
			winCompetitivePlayerBracket.setUpdatedBy("LinhLHN");
			winCompetitivePlayerBracket.setUpdatedOn(LocalDateTime.now());
			competitivePlayerBracketRepository.save(winCompetitivePlayerBracket);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}

}
