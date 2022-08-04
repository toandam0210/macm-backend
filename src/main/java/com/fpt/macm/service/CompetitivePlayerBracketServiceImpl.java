package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class CompetitivePlayerBracketServiceImpl implements CompetitivePlayerBracketService{

	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseMessage getListPlayerBracket(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			int numberPlayer = listPlayers.size();
			boolean isOrder = true;
			for (CompetitivePlayerBracket competitivePlayerBracket : listPlayers) {
				if(competitivePlayerBracket.getNumericalOrderId() == null) {
					isOrder = false;
					break;
				}
			}
			if(!isOrder) {
				Collections.shuffle(listPlayers);
				for(int i = 0; i < numberPlayer; i++) {
					CompetitivePlayerBracket getcompetitivePlayerBracket = listPlayers.get(i);
					getcompetitivePlayerBracket.setNumericalOrderId(i + 1);
					competitivePlayerBracketRepository.save(getcompetitivePlayerBracket);
				}
			}
			List<CompetitivePlayerBracket> listSortByNumerical = competitivePlayerBracketRepository.listSortByNumerical(competitiveTypeId);
			responseMessage.setData(listSortByNumerical);
			responseMessage.setMessage("Danh sách tuyển thủ");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListNotJoinCompetitive(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveType getType = competitiveTypeRepository.findById(competitiveTypeId).get();
			Tournament getTournament = tournamentRepository.findById(competitiveTypeRepository.findTournamentOfType(competitiveTypeId)).get();
			List<User> userJoined= new ArrayList<User>();
			List<User> listActive = userRepository.findAllActiveUser();
			List<TournamentPlayer> listPlayers = tournamentPlayerRepository.getPlayerByTournamentId(getTournament.getId());
			for (TournamentPlayer tournamentPlayer : listPlayers) {
				Optional<CompetitivePlayer> getCompetitivePlayerOp = competitivePlayerRepository.findByTournamentPlayerId(tournamentPlayer.getId());
				if(getCompetitivePlayerOp.isPresent()) {
					User getUser = tournamentPlayer.getUser();
					userJoined.add(getUser);
				}
			}
			List<User> userNotJoined= new ArrayList<User>();
			for (User user : listActive) {
				if(!userJoined.contains(user) && getType.isGender() == user.isGender()) {
					userNotJoined.add(user);
				}
			}
			responseMessage.setData(userNotJoined);
			responseMessage.setMessage("Danh sách thành viên chưa đăng ký tham gia thi đấu đối kháng");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}
