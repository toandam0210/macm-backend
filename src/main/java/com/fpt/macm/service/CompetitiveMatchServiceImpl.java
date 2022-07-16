package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.dto.PlayerMatchDto;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayerBracket;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentRepository;
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
	TournamentRepository tournamentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public ResponseMessage spawnMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveType competitiveType = competitiveTypeRepository.findById(competitiveTypeId).get();
			Tournament getTournament = tournamentRepository.findById(competitiveTypeRepository.findTournamentOfType(competitiveTypeId)).get();
			if(getTournament.getRegistrationPlayerDeadline().compareTo(LocalDateTime.now()) <= 0) {
				List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
				int numberPlayer = listPlayers.size();
				if(numberPlayer < 4) {
					responseMessage.setMessage("Số lượng tuyển thủ không đạt 4 người trở lên, không thi đấu hạng cân này");
				}
				else {
					int nextPower = nextPower(numberPlayer);
					int round = 1;
					while (nextPower > 1) {
						int countMatch = nextPower/2;
						for(int i = 0; i < countMatch; i++) {
							CompetitiveMatch newMatch = new CompetitiveMatch();
							newMatch.setRound(round);
							newMatch.setCompetitiveType(competitiveType);
							newMatch.setStatus(false);
							newMatch.setCreatedBy("LinhLHN");
							newMatch.setCreatedOn(LocalDateTime.now());
							newMatch.setUpdatedBy("LinhLHN");
							newMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(newMatch);
						}
						nextPower = countMatch;
						round++;
					}
					CompetitiveMatch newMatch = new CompetitiveMatch();
					newMatch.setRound(round);
					newMatch.setCompetitiveType(competitiveType);
					newMatch.setCreatedBy("LinhLHN");
					newMatch.setCreatedOn(LocalDateTime.now());
					newMatch.setUpdatedBy("LinhLHN");
					newMatch.setUpdatedOn(LocalDateTime.now());
					competitiveMatchRepository.save(newMatch);
					List<CompetitiveMatch> listMatch = competitiveMatchRepository.listMatchsByTypeDesc(competitiveTypeId);
					List<CompetitiveMatch> listNewMatch = new ArrayList<CompetitiveMatch>();
					for(int i = 2; i < listMatch.size(); i++) {
						CompetitiveMatch getMatch = listMatch.get(i);
						getMatch.setNextIsFirst(i%2 == 1);
						getMatch.setNextMatchId(listMatch.get(i/2).getId());
						if(i == 2 || i == 3) {
							getMatch.setLoseMatchId(listMatch.get(0).getId());
						}
						else {
							getMatch.setLoseMatchId(null);
						}
						getMatch.setUpdatedBy("LinhLHN");
						getMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(getMatch);
						listNewMatch.add(getMatch);
					}
					responseMessage.setData(listNewMatch);
					responseMessage.setMessage("Danh sách trận đấu");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
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
				newCompetitiveMatchDto.setStatus(competitiveMatch.getStatus());
				if(competitiveMatch.getFirstStudentId() != null) {
					PlayerMatchDto firstPlayer = new PlayerMatchDto();
					firstPlayer.setStudentId(competitiveMatch.getFirstStudentId());
					User fisrtUser = userRepository.getByStudentId(competitiveMatch.getFirstStudentId());
					firstPlayer.setStudentName(fisrtUser.getName());
					newCompetitiveMatchDto.setFirstPlayer(firstPlayer);
				}
				else {
					newCompetitiveMatchDto.setFirstPlayer(null);
				}
				if(competitiveMatch.getSecondStudentId() != null) {
					PlayerMatchDto secondPlayer = new PlayerMatchDto();
					secondPlayer.setStudentId(competitiveMatch.getSecondStudentId());
					User secondUser = userRepository.getByStudentId(competitiveMatch.getSecondStudentId());
					secondPlayer.setStudentName(secondUser.getName());
					newCompetitiveMatchDto.setSecondPlayer(secondPlayer);
				}
				else {
					newCompetitiveMatchDto.setSecondPlayer(null);
				}
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(competitiveMatch.getId());
				if(getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					newCompetitiveMatchDto.setArea(getResult.getArea().getName());
					newCompetitiveMatchDto.setTime(getResult.getTime());
					if(getResult.getFirstPoint() != null) {
						newCompetitiveMatchDto.getFirstPlayer().setPoint(getResult.getFirstPoint());
					}
					if(getResult.getSecondPoint() != null) {
						newCompetitiveMatchDto.getSecondPlayer().setPoint(getResult.getSecondPoint());
					}
				}
				listMatchDto.add(newCompetitiveMatchDto);
			}
			Collections.sort(listMatchDto);
			responseMessage.setData(listMatchDto);
			responseMessage.setMessage("Danh sách trận đấu");
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			responseMessage.setTotalResult(maxRound(listPlayers.size()));
			Tournament getTournament = tournamentRepository.findById(competitiveTypeRepository.findTournamentByCompetitivePlayerId(competitiveTypeId)).get();
			responseMessage.setCode(getTournament.getStatus());
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

	@Override
	public ResponseMessage updateListMatchsPlayer(List<CompetitiveMatchDto> listUpdated) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitiveMatch> listMatchUpdated = new ArrayList<CompetitiveMatch>();
			for (CompetitiveMatchDto matchDto : listUpdated) {
				if(matchDto.getRound() == 1) {
					CompetitiveMatch getMatch = competitiveMatchRepository.findById(matchDto.getId()).get();
					if(matchDto.getFirstPlayer() != null) {
						getMatch.setFirstStudentId(matchDto.getFirstPlayer().getStudentId());
					}
					if(matchDto.getSecondPlayer() != null) {
						getMatch.setSecondStudentId(matchDto.getSecondPlayer().getStudentId());
					}
					getMatch.setUpdatedBy("LinhLHN");
					getMatch.setUpdatedOn(LocalDateTime.now());
					competitiveMatchRepository.save(getMatch);
					listMatchUpdated.add(getMatch);
				}
			}
			responseMessage.setData(listMatchUpdated);
			responseMessage.setMessage("Danh sách trận đấu chính thức");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage previewMatchsPlayer(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitivePlayerBracket> listPlayers = competitivePlayerBracketRepository.listPlayersByType(competitiveTypeId);
			int numberPlayer = listPlayers.size();
			int nextPower = nextPower(numberPlayer);
			int freePlayer = nextPower - numberPlayer;
			List<CompetitiveMatch> listMatch = competitiveMatchRepository.listMatchsByTypeAsc(competitiveTypeId);
			List<CompetitiveMatchDto> listMatchPreview = new ArrayList<CompetitiveMatchDto>();
			int currentMatch = 0;
			for(int i = 0; i < freePlayer; i++) {
				CompetitiveMatchDto newMatchPreview = new CompetitiveMatchDto();
				newMatchPreview.setId(listMatch.get(currentMatch).getId());
				newMatchPreview.setRound(1);
				PlayerMatchDto firstPlayer = new PlayerMatchDto();
				firstPlayer.setStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				firstPlayer.setStudentName(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getName());
				newMatchPreview.setFirstPlayer(firstPlayer);
				newMatchPreview.setSecondPlayer(null);
				listMatchPreview.add(newMatchPreview);
				currentMatch++;
			}
			for(int i = freePlayer; i < numberPlayer; i+= 2) {
				CompetitiveMatchDto newMatchPreview = new CompetitiveMatchDto();
				newMatchPreview.setId(listMatch.get(currentMatch).getId());
				newMatchPreview.setRound(1);
				PlayerMatchDto firstPlayer = new PlayerMatchDto();
				firstPlayer.setStudentId(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				firstPlayer.setStudentName(listPlayers.get(i).getCompetitivePlayer().getTournamentPlayer().getUser().getName());
				newMatchPreview.setFirstPlayer(firstPlayer);
				PlayerMatchDto secondPlayer = new PlayerMatchDto();
				secondPlayer.setStudentId(listPlayers.get(i + 1).getCompetitivePlayer().getTournamentPlayer().getUser().getStudentId());
				secondPlayer.setStudentName(listPlayers.get(i + 1).getCompetitivePlayer().getTournamentPlayer().getUser().getName());
				newMatchPreview.setSecondPlayer(secondPlayer);
				listMatchPreview.add(newMatchPreview);
				currentMatch++;
			}
			for(int i = currentMatch; i < listMatch.size(); i++) {
				CompetitiveMatchDto newMatchPreview = new CompetitiveMatchDto();
				newMatchPreview.setId(listMatch.get(i).getId());
				newMatchPreview.setRound(listMatch.get(i).getRound());
				listMatchPreview.add(newMatchPreview);
			}
			responseMessage.setData(listMatchPreview);
			responseMessage.setMessage("Bảng thi đấu dự kiến");
			responseMessage.setTotalResult(maxRound(numberPlayer));
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage confirmListMatchsPlayer(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Tournament getTournament = tournamentRepository.findById(tournamentId).get();
			if(getTournament.getStatus() == 1) {
				getTournament.setStatus(2);
				tournamentRepository.save(getTournament);
				Set<CompetitiveType> listType = getTournament.getCompetitiveTypes();
				List<CompetitiveMatch> listUpdated = new ArrayList<CompetitiveMatch>();
				for (CompetitiveType competitiveType : listType) {
					List<CompetitiveMatch> listMatchs = competitiveMatchRepository.listMatchsByType(competitiveType.getId());
					for (CompetitiveMatch getMatch : listMatchs) {
						if(getMatch.getRound() == 1) {
							CompetitiveMatch nextMatch = competitiveMatchRepository.findById(getMatch.getNextMatchId()).get();
							if(getMatch.getFirstStudentId() == null) {
								if(getMatch.isNextIsFirst()) {
									nextMatch.setFirstStudentId(getMatch.getSecondStudentId());
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
								else {
									nextMatch.setSecondStudentId(getMatch.getSecondStudentId());
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
							}
							else if(getMatch.getSecondStudentId() == null) {
								if(getMatch.isNextIsFirst()) {
									nextMatch.setFirstStudentId(getMatch.getFirstStudentId());
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
								else {
									nextMatch.setSecondStudentId(getMatch.getFirstStudentId());
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
							}
							listUpdated.add(nextMatch);
						}
					}
				}
				responseMessage.setData(listUpdated);
				responseMessage.setMessage("Xác nhận danh sách thi đấu chính thức");
			}
			else {
				responseMessage.setMessage("Đã có danh sách thi đấu chính thức");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
