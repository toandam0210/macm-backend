package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class CompetitiveTypeServiceImpl implements CompetitiveTypeService{

	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;
	
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
			responseMessage.setTotalResult(userNotJoined.size());
			responseMessage.setMessage("Danh sách thành viên chưa đăng ký tham gia thi đấu đối kháng");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
