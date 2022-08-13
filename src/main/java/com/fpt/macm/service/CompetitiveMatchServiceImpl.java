package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.CompetitiveMatchByTypeDto;
import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.dto.PlayerMatchDto;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class CompetitiveMatchServiceImpl implements CompetitiveMatchService {

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;

	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;

	@Autowired
	CompetitiveResultRepository competitiveResultRepository;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	public ResponseMessage listMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				responseMessage.setCode(getType.getStatus());
				List<CompetitivePlayer> listPlayers = competitivePlayerRepository
						.findEligibleByCompetitiveTypeId(competitiveTypeId);
				if (listPlayers.size() == 0) {
					responseMessage.setMessage("Chưa có danh sách tuyển thủ thi đấu");
				} else {
					List<CompetitiveMatch> listMatchs = competitiveMatchRepository.listMatchsByType(competitiveTypeId);
					if (listMatchs.size() == 0) {
						responseMessage.setMessage("Chưa tạo trận đấu cho thể thức này");
					} else {
						List<CompetitiveMatchDto> listMatchDto = new ArrayList<CompetitiveMatchDto>();
						for (CompetitiveMatch competitiveMatch : listMatchs) {
							CompetitiveMatchDto newCompetitiveMatchDto = new CompetitiveMatchDto();
							newCompetitiveMatchDto.setId(competitiveMatch.getId());
							newCompetitiveMatchDto.setRound(competitiveMatch.getRound());
							newCompetitiveMatchDto.setStatus(competitiveMatch.getStatus());
							if (competitiveMatch.getFirstStudentId() != null) {
								PlayerMatchDto firstPlayer = new PlayerMatchDto();
								firstPlayer.setStudentId(competitiveMatch.getFirstStudentId());
								User fisrtUser = userRepository.getByStudentId(competitiveMatch.getFirstStudentId());
								firstPlayer.setStudentName(fisrtUser.getName());
								newCompetitiveMatchDto.setFirstPlayer(firstPlayer);
							} else {
								newCompetitiveMatchDto.setFirstPlayer(null);
							}
							if (competitiveMatch.getSecondStudentId() != null) {
								PlayerMatchDto secondPlayer = new PlayerMatchDto();
								secondPlayer.setStudentId(competitiveMatch.getSecondStudentId());
								User secondUser = userRepository.getByStudentId(competitiveMatch.getSecondStudentId());
								secondPlayer.setStudentName(secondUser.getName());
								newCompetitiveMatchDto.setSecondPlayer(secondPlayer);
							} else {
								newCompetitiveMatchDto.setSecondPlayer(null);
							}
							Optional<CompetitiveResult> getResultOp = competitiveResultRepository
									.findResultByMatchId(competitiveMatch.getId());
							if (getResultOp.isPresent()) {
								CompetitiveResult getResult = getResultOp.get();
								newCompetitiveMatchDto.setArea(getResult.getArea().getName());
								newCompetitiveMatchDto.setTime(getResult.getTime());
								if (getResult.getFirstPoint() != null) {
									newCompetitiveMatchDto.getFirstPlayer().setPoint(getResult.getFirstPoint());
								}
								if (getResult.getSecondPoint() != null) {
									newCompetitiveMatchDto.getSecondPlayer().setPoint(getResult.getSecondPoint());
								}
							}
							listMatchDto.add(newCompetitiveMatchDto);
						}
						Collections.sort(listMatchDto);

						int index = 0;
						for (CompetitiveMatchDto competitiveMatchDto : listMatchDto) {
							if ((competitiveMatchDto.getFirstPlayer() == null
									|| competitiveMatchDto.getSecondPlayer() == null)
									&& competitiveMatchDto.getRound() == 1) {
								
							} else {
								index++;
							}
							competitiveMatchDto.setMatchNo(index);
						}

						CompetitiveMatchByTypeDto competitiveMatchByTypeDto = new CompetitiveMatchByTypeDto();
						competitiveMatchByTypeDto.setName((getType.isGender() ? "Nam " : "Nữ ") + getType.getWeightMin()
								+ " - " + getType.getWeightMax());
						competitiveMatchByTypeDto.setChanged(getType.isChanged());
						competitiveMatchByTypeDto.setStatus(getType.getStatus());
						competitiveMatchByTypeDto.setListMatchDto(listMatchDto);
						responseMessage.setData(Arrays.asList(competitiveMatchByTypeDto));
						if (getType.getStatus() <= 1) {
							responseMessage.setMessage("Danh sách trận đấu dự kiến");
						} else {
							responseMessage.setMessage("Danh sách trận đấu chính thức");
						}
						responseMessage.setTotalResult(maxRound(listPlayers.size()) + 1);
					}
				}
			}
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
				CompetitiveMatch getMatch = competitiveMatchRepository.findById(matchDto.getId()).get();

				if (getMatch.getFirstStudentId() != null && matchDto.getFirstPlayer() != null) {
					getMatch.setFirstStudentId(matchDto.getFirstPlayer().getStudentId());
				}
				if (getMatch.getSecondStudentId() != null && matchDto.getSecondPlayer() != null) {
					getMatch.setSecondStudentId(matchDto.getSecondPlayer().getStudentId());
				}

				getMatch.setUpdatedBy("LinhLHN");
				getMatch.setUpdatedOn(LocalDateTime.now());
				competitiveMatchRepository.save(getMatch);
				listMatchUpdated.add(getMatch);
			}
			responseMessage.setData(listMatchUpdated);
			responseMessage.setMessage("Danh sách trận đấu sau chỉnh sửa");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage confirmListMatchsPlayer(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				if (getType.getStatus() <= 1) {
					getType.setStatus(2);
					competitiveTypeRepository.save(getType);
				}
				responseMessage.setData(Arrays.asList(getType));
				responseMessage.setMessage(
						"Xác nhận danh sách thi đấu chính thức cho thể thức " + (getType.isGender() ? "Nam: " : "Nữ: ")
								+ getType.getWeightMin() + " - " + getType.getWeightMax());
			} else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage spawnMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				if (getType.getStatus() <= 1) {
					List<CompetitiveMatch> listOldMatch = competitiveMatchRepository
							.listMatchsByType(competitiveTypeId);
					if (listOldMatch.size() > 0) {
						competitiveMatchRepository.deleteAll(listOldMatch);
					}

					List<CompetitivePlayer> listPlayers = competitivePlayerRepository
							.findEligibleByCompetitiveTypeId(competitiveTypeId);
					int numberPlayer = listPlayers.size();
					if (numberPlayer < 4) {
						responseMessage
								.setMessage("Số lượng tuyển thủ không đạt 4 người trở lên, không thi đấu hạng cân này");
					} else {
						int nextPower = nextPower(numberPlayer);
						int round = 1;
						while (nextPower > 1) {
							int countMatch = nextPower / 2;
							for (int i = 0; i < countMatch; i++) {
								CompetitiveMatch newMatch = new CompetitiveMatch();
								newMatch.setRound(round);
								newMatch.setCompetitiveType(getType);
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
						newMatch.setCompetitiveType(getType);
						newMatch.setCreatedBy("LinhLHN");
						newMatch.setCreatedOn(LocalDateTime.now());
						newMatch.setUpdatedBy("LinhLHN");
						newMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(newMatch);
						List<CompetitiveMatch> listMatchReverse = competitiveMatchRepository
								.listMatchsByTypeDesc(competitiveTypeId);
						for (int i = 2; i < listMatchReverse.size(); i++) {
							CompetitiveMatch getMatch = listMatchReverse.get(i);
							getMatch.setNextIsFirst(i % 2 == 1);
							getMatch.setNextMatchId(listMatchReverse.get(i / 2).getId());
							if (i == 2 || i == 3) {
								getMatch.setLoseMatchId(listMatchReverse.get(0).getId());
							} else {
								getMatch.setLoseMatchId(null);
							}
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
						}
						nextPower = nextPower(numberPlayer);
						int freePlayer = nextPower - numberPlayer;
						List<CompetitiveMatch> listMatch = competitiveMatchRepository
								.listMatchsByTypeAsc(competitiveTypeId);
						int currentMatch = 0;
						for (int i = 0; i < freePlayer; i++) {
							CompetitiveMatch getMatch = listMatch.get(currentMatch);
							String studentId = listPlayers.get(i).getTournamentPlayer().getUser().getStudentId();
							getMatch.setFirstStudentId(studentId);
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
							Optional<CompetitiveMatch> nextMatchOp = competitiveMatchRepository
									.findById(getMatch.getNextMatchId());
							if (nextMatchOp.isPresent()) {
								CompetitiveMatch nextMatch = competitiveMatchRepository
										.findById(getMatch.getNextMatchId()).get();
								if (getMatch.isNextIsFirst()) {
									nextMatch.setFirstStudentId(studentId);
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								} else {
									nextMatch.setSecondStudentId(studentId);
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
							}
							currentMatch++;
						}
						for (int i = freePlayer; i < numberPlayer; i += 2) {
							CompetitiveMatch getMatch = listMatch.get(currentMatch);
							getMatch.setFirstStudentId(
									listPlayers.get(i).getTournamentPlayer().getUser().getStudentId());
							getMatch.setSecondStudentId(
									listPlayers.get(i + 1).getTournamentPlayer().getUser().getStudentId());
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
							currentMatch++;
						}

						List<CompetitiveMatch> listSpawnMatch = competitiveMatchRepository
								.listMatchsByTypeAsc(competitiveTypeId);
						List<CompetitiveMatchDto> listMatchPreview = new ArrayList<CompetitiveMatchDto>();
						for (CompetitiveMatch competitiveMatch : listSpawnMatch) {
							CompetitiveMatchDto newMatchPreview = new CompetitiveMatchDto();
							newMatchPreview.setId(competitiveMatch.getId());
							newMatchPreview.setRound(competitiveMatch.getRound());
							PlayerMatchDto firstPlayer = new PlayerMatchDto();
							firstPlayer.setStudentId(competitiveMatch.getFirstStudentId());
							if (firstPlayer.getStudentId() != null) {
								firstPlayer.setStudentName(
										userRepository.findByStudentId(firstPlayer.getStudentId()).get().getName());
							}
							newMatchPreview.setFirstPlayer(firstPlayer);
							PlayerMatchDto secondPlayer = new PlayerMatchDto();
							secondPlayer.setStudentId(competitiveMatch.getSecondStudentId());
							if (secondPlayer.getStudentId() != null) {
								secondPlayer.setStudentName(
										userRepository.findByStudentId(secondPlayer.getStudentId()).get().getName());
							}
							newMatchPreview.setSecondPlayer(secondPlayer);
							listMatchPreview.add(newMatchPreview);
						}
						responseMessage.setData(listMatchPreview);
						responseMessage.setMessage("Danh sách trận đấu dự kiến");
						getType.setChanged(false);
						competitiveTypeRepository.save(getType);
					}
				} else {
					responseMessage.setMessage("Đã qua giai đoạn tạo trận đấu");
				}
			} else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public void autoSpawnMatchs(int competitiveTypeId) {
		// TODO Auto-generated method stub
		try {
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				if (getType.getStatus() <= 1) {
					List<CompetitiveMatch> listOldMatch = competitiveMatchRepository
							.listMatchsByType(competitiveTypeId);
					if (listOldMatch.size() > 0) {
						competitiveMatchRepository.deleteAll(listOldMatch);
					}

					List<CompetitivePlayer> listPlayers = competitivePlayerRepository
							.findEligibleByCompetitiveTypeId(competitiveTypeId);
					int numberPlayer = listPlayers.size();
					if (numberPlayer >= 4) {
						int nextPower = nextPower(numberPlayer);
						int round = 1;
						while (nextPower > 1) {
							int countMatch = nextPower / 2;
							for (int i = 0; i < countMatch; i++) {
								CompetitiveMatch newMatch = new CompetitiveMatch();
								newMatch.setRound(round);
								newMatch.setCompetitiveType(getType);
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
						newMatch.setCompetitiveType(getType);
						newMatch.setCreatedBy("LinhLHN");
						newMatch.setCreatedOn(LocalDateTime.now());
						newMatch.setUpdatedBy("LinhLHN");
						newMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(newMatch);
						List<CompetitiveMatch> listMatchReverse = competitiveMatchRepository
								.listMatchsByTypeDesc(competitiveTypeId);
						for (int i = 2; i < listMatchReverse.size(); i++) {
							CompetitiveMatch getMatch = listMatchReverse.get(i);
							getMatch.setNextIsFirst(i % 2 == 1);
							getMatch.setNextMatchId(listMatchReverse.get(i / 2).getId());
							if (i == 2 || i == 3) {
								getMatch.setLoseMatchId(listMatchReverse.get(0).getId());
							} else {
								getMatch.setLoseMatchId(null);
							}
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
						}
						nextPower = nextPower(numberPlayer);
						int freePlayer = nextPower - numberPlayer;
						List<CompetitiveMatch> listMatch = competitiveMatchRepository
								.listMatchsByTypeAsc(competitiveTypeId);
						int currentMatch = 0;
						for (int i = 0; i < freePlayer; i++) {
							CompetitiveMatch getMatch = listMatch.get(currentMatch);
							String studentId = listPlayers.get(i).getTournamentPlayer().getUser().getStudentId();
							getMatch.setFirstStudentId(studentId);
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
							Optional<CompetitiveMatch> nextMatchOp = competitiveMatchRepository
									.findById(getMatch.getNextMatchId());
							if (nextMatchOp.isPresent()) {
								CompetitiveMatch nextMatch = competitiveMatchRepository
										.findById(getMatch.getNextMatchId()).get();
								if (getMatch.isNextIsFirst()) {
									nextMatch.setFirstStudentId(studentId);
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								} else {
									nextMatch.setSecondStudentId(studentId);
									nextMatch.setUpdatedBy("LinhLHN");
									nextMatch.setUpdatedOn(LocalDateTime.now());
									competitiveMatchRepository.save(nextMatch);
								}
							}
							currentMatch++;
						}
						for (int i = freePlayer; i < numberPlayer; i += 2) {
							CompetitiveMatch getMatch = listMatch.get(currentMatch);
							getMatch.setFirstStudentId(
									listPlayers.get(i).getTournamentPlayer().getUser().getStudentId());
							getMatch.setSecondStudentId(
									listPlayers.get(i + 1).getTournamentPlayer().getUser().getStudentId());
							getMatch.setUpdatedBy("LinhLHN");
							getMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(getMatch);
							currentMatch++;
						}

						List<CompetitiveMatch> listSpawnMatch = competitiveMatchRepository
								.listMatchsByTypeAsc(competitiveTypeId);
						List<CompetitiveMatchDto> listMatchPreview = new ArrayList<CompetitiveMatchDto>();
						for (CompetitiveMatch competitiveMatch : listSpawnMatch) {
							CompetitiveMatchDto newMatchPreview = new CompetitiveMatchDto();
							newMatchPreview.setId(competitiveMatch.getId());
							newMatchPreview.setRound(competitiveMatch.getRound());
							PlayerMatchDto firstPlayer = new PlayerMatchDto();
							firstPlayer.setStudentId(competitiveMatch.getFirstStudentId());
							if (firstPlayer.getStudentId() != null) {
								firstPlayer.setStudentName(
										userRepository.findByStudentId(firstPlayer.getStudentId()).get().getName());
							}
							newMatchPreview.setFirstPlayer(firstPlayer);
							PlayerMatchDto secondPlayer = new PlayerMatchDto();
							secondPlayer.setStudentId(competitiveMatch.getSecondStudentId());
							if (secondPlayer.getStudentId() != null) {
								secondPlayer.setStudentName(
										userRepository.findByStudentId(secondPlayer.getStudentId()).get().getName());
							}
							newMatchPreview.setSecondPlayer(secondPlayer);
							listMatchPreview.add(newMatchPreview);
						}
						getType.setChanged(false);
						competitiveTypeRepository.save(getType);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
