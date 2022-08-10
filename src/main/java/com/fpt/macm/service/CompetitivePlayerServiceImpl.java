package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.CompetitiveMatchByTypeDto;
import com.fpt.macm.model.dto.CompetitivePlayerByTypeDto;
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
public class CompetitivePlayerServiceImpl implements CompetitivePlayerService{
	
	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TournamentRepository tournamentRepository;
	
	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;
	
	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;
	
	@Autowired
	CompetitiveMatchService competitiveMatchService;
	
	@Override
	public ResponseMessage addNewCompetitivePlayer(List<User> users, int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getCompetitiveTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getCompetitiveTypeOp.isPresent()) {
				CompetitiveType getType = getCompetitiveTypeOp.get();
				if(getType.getStatus() == 0) {
					int tournamentId = competitiveTypeRepository.findTournamentOfType(competitiveTypeId);
					Tournament getTounament = tournamentRepository.findById(tournamentId).get();
					List<String> listUsers = new ArrayList<String>();
					for (User user : users) {
						if(user.isGender() == getType.isGender()) {
							TournamentPlayer getTournamentPlayer = new TournamentPlayer();
							Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(user.getId(), tournamentId);
							if(!tournamentPlayerOp.isPresent()) {
								Set<TournamentPlayer> players = getTounament.getTournamentPlayers();
								TournamentPlayer newTournamentPlayer = new TournamentPlayer();
								newTournamentPlayer.setUser(userRepository.findById(user.getId()).get());
								newTournamentPlayer.setPaymentStatus(false);
								newTournamentPlayer.setCreatedBy("LinhLHN");
								newTournamentPlayer.setCreatedOn(LocalDateTime.now());
								newTournamentPlayer.setUpdatedBy("LinhLHN");
								newTournamentPlayer.setUpdatedOn(LocalDateTime.now());
								players.add(newTournamentPlayer);
								getTounament.setTournamentPlayers(players);
								tournamentRepository.save(getTounament);
								getTournamentPlayer = tournamentPlayerRepository.findPlayerByUserIdAndTournamentId(user.getId(), tournamentId).get();
							} else {
								getTournamentPlayer = tournamentPlayerOp.get();
							}
							CompetitivePlayer newCompetitivePlayer = new CompetitivePlayer();
							newCompetitivePlayer.setTournamentPlayer(getTournamentPlayer);
							newCompetitivePlayer.setWeight(0);
							newCompetitivePlayer.setIsEligible(true);
							newCompetitivePlayer.setCompetitiveType(getType);
							newCompetitivePlayer.setCreatedBy("LinhLHN");
							newCompetitivePlayer.setCreatedOn(LocalDateTime.now());
							newCompetitivePlayer.setUpdatedBy("LinhLHN");
							newCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
							competitivePlayerRepository.save(newCompetitivePlayer);
							listUsers.add(user.getName() + " - " + user.getStudentId());
							competitiveMatchService.autoSpawnMatchs(competitiveTypeId);
						}
					}				
					responseMessage.setData(listUsers);
					responseMessage.setMessage("Danh sách đăng ký tham gia thi đấu thể thức " + (getType.isGender()? "Nam: " : "Nữ: ") + getType.getWeightMin() + " kg - " + getType.getWeightMax() + " kg");
				}
				else {
					responseMessage.setMessage("Đã quá thời gian để đăng ký thi đấu");
				}
			}
			else {
				responseMessage.setMessage("Không tìm thấy thể thức thi đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateWeightForCompetitivePlayer (int competitivePlayerId, double weight) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findById(competitivePlayerId);
			if(competitivePlayerOp.isPresent()) {
				CompetitivePlayer getCompetitivePlayer = competitivePlayerOp.get();
				CompetitiveType getType = getCompetitivePlayer.getCompetitiveType();
				if(getType.getStatus() < 2) {
					getCompetitivePlayer.setWeight(weight);
					getCompetitivePlayer.setUpdatedBy("LinhLHN");
					getCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
					if(weight >= getType.getWeightMin() && weight <= getType.getWeightMax()) {
						getCompetitivePlayer.setIsEligible(true);
					}
					else {
						getCompetitivePlayer.setIsEligible(false);
						competitiveMatchService.autoSpawnMatchs(getType.getId());
					}
					competitivePlayerRepository.save(getCompetitivePlayer);
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					responseMessage.setMessage("Cập nhật cân nặng thành công. Tuyển thủ" + (getCompetitivePlayer.getIsEligible()? " " : " không ") + "phù hợp thi đấu hạng cân này");
				} else {
					responseMessage.setMessage("Quá thời gian cập nhật cân nặng");
				}
			}
			else {
				responseMessage.setMessage("Không tồn tại tuyển thủ để cập nhật");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteCompetitivePlayer(int competitivePlayerId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findById(competitivePlayerId);
			if(competitivePlayerOp.isPresent()) {
				CompetitivePlayer getCompetitivePlayer = competitivePlayerOp.get();
				CompetitiveType getType = getCompetitivePlayer.getCompetitiveType();
				if(getType.getStatus() < 2) {
					competitivePlayerRepository.delete(getCompetitivePlayer);
					responseMessage.setMessage("Xóa tuyển thủ thành công");
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					competitiveMatchService.autoSpawnMatchs(getType.getId());
				}
				else {
					if(getCompetitivePlayer.getIsEligible()) {
						responseMessage.setMessage("Không thể xóa. Tuyển thủ đã nằm trong danh sách thi đấu chính thức");
					}
				}
			}
			else {
				responseMessage.setMessage("Không tồn tại tuyển thủ để xóa");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage listUserNotJoinCompetitive(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<User> userJoined= new ArrayList<User>();
			List<User> listActive = userRepository.findAllActiveUser();
			List<TournamentPlayer> listPlayers = tournamentPlayerRepository.getPlayerByTournamentId(tournamentId);
			for (TournamentPlayer tournamentPlayer : listPlayers) {
				Optional<CompetitivePlayer> getCompetitivePlayerOp = competitivePlayerRepository.findByTournamentPlayerId(tournamentPlayer.getId());
				if(getCompetitivePlayerOp.isPresent()) {
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
			responseMessage.setMessage("Danh sách thành viên chưa đăng ký tham gia thi đấu đối kháng");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListPlayer(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if(getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				List<CompetitivePlayer> listPlayers = competitivePlayerRepository.findByCompetitiveTypeId(competitiveTypeId);
				CompetitivePlayerByTypeDto competitivePlayerByTypeDto = new CompetitivePlayerByTypeDto();
				competitivePlayerByTypeDto.setName((getType.isGender()? "Nam " : "Nữ ") + getType.getWeightMin() + " - " + getType.getWeightMax());
				competitivePlayerByTypeDto.setChanged(getType.isChanged());
				competitivePlayerByTypeDto.setStatus(getType.getStatus());
				competitivePlayerByTypeDto.setListPlayers(listPlayers);
				responseMessage.setData(Arrays.asList(competitivePlayerByTypeDto));
				responseMessage.setMessage("Danh sách tuyển thủ");
			}
			else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
}
