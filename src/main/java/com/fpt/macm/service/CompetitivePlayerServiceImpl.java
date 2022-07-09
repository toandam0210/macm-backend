package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CompetitivePlayer;
import com.fpt.macm.model.CompetitivePlayerBracket;
import com.fpt.macm.model.CompetitiveType;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Tournament;
import com.fpt.macm.model.TournamentPlayer;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
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
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;
	
	@Override
	public ResponseMessage addNewCompetitivePlayer(int userId, int tournamentId, double weight) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(userId, tournamentId);
			if(!tournamentPlayerOp.isPresent()) {
				Tournament getTounament = tournamentRepository.findById(tournamentId).get();
				Set<TournamentPlayer> players = getTounament.getTournamentPlayers();
				TournamentPlayer newTournamentPlayer = new TournamentPlayer();
				newTournamentPlayer.setUser(userRepository.findById(userId).get());
				newTournamentPlayer.setPaymentStatus(false);
				newTournamentPlayer.setCreatedBy("LinhLHN");
				newTournamentPlayer.setCreatedOn(LocalDateTime.now());
				newTournamentPlayer.setUpdatedBy("LinhLHN");
				newTournamentPlayer.setUpdatedOn(LocalDateTime.now());
				players.add(newTournamentPlayer);
				getTounament.setTournamentPlayers(players);
				tournamentRepository.save(getTounament);
				TournamentPlayer getTournamentPlayer = tournamentPlayerRepository.getPlayerByUserIdAndTournamentId(userId, tournamentId).get();
				CompetitivePlayer newCompetitivePlayer = new CompetitivePlayer();
				newCompetitivePlayer.setTournamentPlayer(getTournamentPlayer);
				if(weight != 0) {
					newCompetitivePlayer.setWeight(weight);
				}
				newCompetitivePlayer.setCreatedBy("LinhLHN");
				newCompetitivePlayer.setCreatedOn(LocalDateTime.now());
				newCompetitivePlayer.setUpdatedBy("LinhLHN");
				newCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
				competitivePlayerRepository.save(newCompetitivePlayer);
				responseMessage.setData(Arrays.asList(newCompetitivePlayer));
				responseMessage.setMessage("Đăng ký thành công");
				if(weight != 0) {
					List<CompetitiveType> listType = competitiveTypeRepository.findByGender(userRepository.findById(userId).get().isGender());
					for (CompetitiveType competitiveType : listType) {
						if(competitiveType.getWeightMin() < weight && weight <= competitiveType.getWeightMax()) {
							CompetitivePlayer getCompetitivePlayer = competitivePlayerRepository.findByTournamentPlayerId(getTournamentPlayer.getId()).get();
							CompetitivePlayerBracket newCompetitivePlayerBracket = new CompetitivePlayerBracket();
							newCompetitivePlayerBracket.setCompetitiveType(competitiveType);
							newCompetitivePlayerBracket.setCompetitivePlayer(getCompetitivePlayer);
							newCompetitivePlayerBracket.setRound(1);
							newCompetitivePlayerBracket.setCreatedBy("LinhLHN");
							newCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
							newCompetitivePlayerBracket.setUpdatedBy("LinhLHN");
							newCompetitivePlayerBracket.setUpdatedOn(LocalDateTime.now());
							competitivePlayerBracketRepository.save(newCompetitivePlayerBracket);
							break;
						}
					}
				}
			} else {
				TournamentPlayer getTournamentPlayer = tournamentPlayerOp.get();
				Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findByTournamentPlayerId(getTournamentPlayer.getId());
				if(!competitivePlayerOp.isPresent()) {
					CompetitivePlayer newCompetitivePlayer = new CompetitivePlayer();
					newCompetitivePlayer.setTournamentPlayer(getTournamentPlayer);
					if(weight != 0) {
						newCompetitivePlayer.setWeight(weight);
					}
					newCompetitivePlayer.setCreatedBy("LinhLHN");
					newCompetitivePlayer.setCreatedOn(LocalDateTime.now());
					newCompetitivePlayer.setUpdatedBy("LinhLHN");
					newCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
					competitivePlayerRepository.save(newCompetitivePlayer);
					responseMessage.setData(Arrays.asList(newCompetitivePlayer));
					responseMessage.setMessage("Đăng ký thành công");
					if(weight != 0) {
						List<CompetitiveType> listType = competitiveTypeRepository.findByGender(userRepository.findById(userId).get().isGender());
						for (CompetitiveType competitiveType : listType) {
							if(competitiveType.getWeightMin() < weight && weight <= competitiveType.getWeightMax()) {
								CompetitivePlayer getCompetitivePlayer = competitivePlayerRepository.findByTournamentPlayerId(getTournamentPlayer.getId()).get();
								CompetitivePlayerBracket newCompetitivePlayerBracket = new CompetitivePlayerBracket();
								newCompetitivePlayerBracket.setCompetitiveType(competitiveType);
								newCompetitivePlayerBracket.setCompetitivePlayer(getCompetitivePlayer);
								newCompetitivePlayerBracket.setRound(1);
								newCompetitivePlayerBracket.setCreatedBy("LinhLHN");
								newCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
								newCompetitivePlayerBracket.setUpdatedBy("LinhLHN");
								newCompetitivePlayerBracket.setUpdatedOn(LocalDateTime.now());
								competitivePlayerBracketRepository.save(newCompetitivePlayerBracket);
								break;
							}
						}
					}
				}
				else {
					responseMessage.setMessage("Đã đăng ký vào giải này rồi");
				}
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
				if(getCompetitivePlayer.getWeight() == 0) {
					getCompetitivePlayer.setWeight(weight);
					List<CompetitiveType> listType = competitiveTypeRepository.findByGender(getCompetitivePlayer.getTournamentPlayer().getUser().isGender());
					for (CompetitiveType competitiveType : listType) {
						if(competitiveType.getWeightMin() < weight && weight <= competitiveType.getWeightMax()) {
							CompetitivePlayerBracket newCompetitivePlayerBracket = new CompetitivePlayerBracket();
							newCompetitivePlayerBracket.setCompetitiveType(competitiveType);
							newCompetitivePlayerBracket.setCompetitivePlayer(getCompetitivePlayer);
							newCompetitivePlayerBracket.setRound(1);
							newCompetitivePlayerBracket.setCreatedBy("LinhLHN");
							newCompetitivePlayerBracket.setCreatedOn(LocalDateTime.now());
							newCompetitivePlayerBracket.setUpdatedBy("LinhLHN");
							newCompetitivePlayerBracket.setUpdatedOn(LocalDateTime.now());
							competitivePlayerBracketRepository.save(newCompetitivePlayerBracket);
							break;
						}
					}
					getCompetitivePlayer.setCreatedBy("LinhLHN");
					getCompetitivePlayer.setCreatedOn(LocalDateTime.now());
					getCompetitivePlayer.setUpdatedBy("LinhLHN");
					getCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
					competitivePlayerRepository.save(getCompetitivePlayer);
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					responseMessage.setMessage("Cập nhật cân nặng tuyển thủ thành công");
				} else {
					CompetitivePlayerBracket getCompetitivePlayerBracket = competitivePlayerBracketRepository.findByPlayerId(getCompetitivePlayer.getId()).get();
					CompetitiveType getCompetitiveType = getCompetitivePlayerBracket.getCompetitiveType();
					if(getCompetitiveType.getWeightMin() > weight || getCompetitiveType.getWeightMax() < weight) {
						getCompetitivePlayerBracket.setRound(0);
						competitivePlayerBracketRepository.save(getCompetitivePlayerBracket);
						responseMessage.setMessage("Loại khỏi giải đấu vì đăng ký sai hạng cân");
					}
					getCompetitivePlayer.setWeight(weight);
					getCompetitivePlayer.setCreatedBy("LinhLHN");
					getCompetitivePlayer.setCreatedOn(LocalDateTime.now());
					getCompetitivePlayer.setUpdatedBy("LinhLHN");
					getCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
					competitivePlayerRepository.save(getCompetitivePlayer);
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
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
				Optional<CompetitivePlayerBracket> getCompetitivePlayerBracketOp = competitivePlayerBracketRepository.findByPlayerId(getCompetitivePlayer.getId());
				if(getCompetitivePlayerBracketOp.isPresent()) {
					CompetitivePlayerBracket getCompetitivePlayerBracket = getCompetitivePlayerBracketOp.get();
					competitivePlayerBracketRepository.delete(getCompetitivePlayerBracket);
				}
				competitivePlayerRepository.delete(getCompetitivePlayer);
				responseMessage.setMessage("Xóa tuyển thủ thành công");
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
	
}
