package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class ExhibitionTypeServiceImpl implements ExhibitionTypeService{

	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	ExhibitionTypeRepository exhibitionTypeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	ExhibitionPlayerRepository exhibitionPlayerRepository;
	
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

	@Override
	public ResponseMessage getListNotJoinExhibition(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if(getTypeOp.isPresent()) {
				ExhibitionType getType = getTypeOp.get();
				Tournament getTournament = tournamentRepository.findById(exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId)).get();
				List<User> userJoined= new ArrayList<User>();
				List<User> listActive = userRepository.findAllActiveUser();
				List<TournamentPlayer> listPlayers = tournamentPlayerRepository.getPlayerByTournamentId(getTournament.getId());
				for (TournamentPlayer tournamentPlayer : listPlayers) {
					Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository.findByTournamentPlayerAndType(tournamentPlayer.getId(), exhibitionTypeId);
					if(getExhibitionPlayerOp.isPresent()) {
						User getUser = tournamentPlayer.getUser();
						userJoined.add(getUser);
					}
				}
				List<User> userNotJoined= new ArrayList<User>();
				for (User user : listActive) {
					if(!userJoined.contains(user)) {
						userNotJoined.add(user);
					}
				}
				responseMessage.setData(userNotJoined);
				responseMessage.setTotalResult(userNotJoined.size());
				responseMessage.setMessage("Danh sách thành viên chưa đăng ký tham gia thi đấu biểu diễn thể thức " + getType.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}
}
