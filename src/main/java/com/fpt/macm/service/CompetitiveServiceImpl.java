package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.dto.CompetitiveMatchByTypeDto;
import com.fpt.macm.model.dto.CompetitiveMatchDto;
import com.fpt.macm.model.dto.CompetitivePlayerByTypeDto;
import com.fpt.macm.model.dto.CompetitiveResultByTypeDto;
import com.fpt.macm.model.dto.PlayerMatchDto;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class CompetitiveServiceImpl implements CompetitiveService {

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

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	CompetitiveResultRepository competitiveResultRepository;

	@Autowired
	ExhibitionPlayerRepository exhibitionPlayerRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Override
	public ResponseMessage getAllCompetitiveType(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Tournament> getTournamentOp = tournamentRepository.findById(tournamentId);
			if (getTournamentOp.isPresent()) {
				Tournament getTournament = getTournamentOp.get();
				List<CompetitiveType> listType = new ArrayList<CompetitiveType>();
				Set<CompetitiveType> getAllTypeByTournament = getTournament.getCompetitiveTypes();
				for (CompetitiveType competitiveType : getAllTypeByTournament) {
					listType.add(competitiveType);
				}
				Collections.sort(listType, new Comparator<CompetitiveType>() {
					@Override
					public int compare(CompetitiveType o1, CompetitiveType o2) {
						// TODO Auto-generated method stub
						if (o1.isGender() == o2.isGender()) {
							return o1.getWeightMin() - o2.getWeightMin() > 0 ? 1 : -1;
						}
						return o1.isGender() ? -1 : 1;
					}
				});
				responseMessage.setData(Arrays.asList(listType));
				responseMessage.setMessage("Danh sách các thể thức thi đấu");
			} else {
				responseMessage.setMessage("Không tìm thấy giải đấu");
			}
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
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				Tournament getTournament = tournamentRepository
						.findById(competitiveTypeRepository.findTournamentOfType(competitiveTypeId)).get();
				List<User> userJoined = new ArrayList<User>();
				List<User> listActive = userRepository.findAllActiveUser();
				List<TournamentPlayer> listPlayers = tournamentPlayerRepository
						.getPlayerByTournamentId(getTournament.getId());
				for (TournamentPlayer tournamentPlayer : listPlayers) {
					Optional<CompetitivePlayer> getCompetitivePlayerOp = competitivePlayerRepository
							.findByTournamentPlayerId(tournamentPlayer.getId());
					if (getCompetitivePlayerOp.isPresent()) {
						User getUser = tournamentPlayer.getUser();
						userJoined.add(getUser);
					}
				}

				List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
						.findByTournamentId(getTournament.getId());
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					userJoined.add(tournamentOrganizingCommittee.getUser());
				}

				List<User> userNotJoined = new ArrayList<User>();
				for (User user : listActive) {
					if (!userJoined.contains(user) && getType.isGender() == user.isGender()) {
						userNotJoined.add(user);
					}
				}
				responseMessage.setData(userNotJoined);
				responseMessage.setTotalResult(userNotJoined.size());
				responseMessage.setMessage("Danh sách thành viên chưa đăng ký tham gia thi đấu đối kháng");
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
							if (competitiveMatchDto.getRound() != 1 || (competitiveMatchDto.getFirstPlayer() != null
									&& competitiveMatchDto.getSecondPlayer() != null)) {
								index++;
							}
							competitiveMatchDto.setMatchNo(index);
						}

						CompetitiveMatchByTypeDto competitiveMatchByTypeDto = new CompetitiveMatchByTypeDto();
						competitiveMatchByTypeDto.setName((getType.isGender() ? "Nam " : "Nữ ") + getType.getWeightMin()
								+ " - " + getType.getWeightMax());
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

	@Override
	public ResponseMessage addNewCompetitivePlayer(List<User> users, int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveType> getCompetitiveTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getCompetitiveTypeOp.isPresent()) {
				CompetitiveType getType = getCompetitiveTypeOp.get();
				if (getType.getStatus() == 0) {
					int tournamentId = competitiveTypeRepository.findTournamentOfType(competitiveTypeId);
					Tournament getTounament = tournamentRepository.findById(tournamentId).get();
					List<String> listUsers = new ArrayList<String>();
					for (User user : users) {
						if (user.isGender() == getType.isGender()) {
							TournamentPlayer getTournamentPlayer = new TournamentPlayer();
							Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
									.getPlayerByUserIdAndTournamentId(user.getId(), tournamentId);
							if (!tournamentPlayerOp.isPresent()) {
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
								getTournamentPlayer = tournamentPlayerRepository
										.findPlayerByUserIdAndTournamentId(user.getId(), tournamentId).get();
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
						}
					}
					autoSpawnMatchs(competitiveTypeId);
					getType.setCanDelete(false);
					competitiveTypeRepository.save(getType);
					responseMessage.setData(listUsers);
					responseMessage.setMessage(
							"Danh sách đăng ký tham gia thi đấu thể thức " + (getType.isGender() ? "Nam: " : "Nữ: ")
									+ getType.getWeightMin() + " kg - " + getType.getWeightMax() + " kg");
				} else {
					responseMessage.setMessage("Đã quá thời gian để đăng ký thi đấu");
				}
			} else {
				responseMessage.setMessage("Không tìm thấy thể thức thi đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateWeightForCompetitivePlayer(int competitivePlayerId, double weight) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitivePlayer> competitivePlayerOp = competitivePlayerRepository.findById(competitivePlayerId);
			if (competitivePlayerOp.isPresent()) {
				CompetitivePlayer getCompetitivePlayer = competitivePlayerOp.get();
				CompetitiveType getType = getCompetitivePlayer.getCompetitiveType();
				if (getType.getStatus() < 2) {
					getCompetitivePlayer.setWeight(weight);
					getCompetitivePlayer.setUpdatedBy("LinhLHN");
					getCompetitivePlayer.setUpdatedOn(LocalDateTime.now());
					if (weight >= getType.getWeightMin() && weight <= getType.getWeightMax()) {
						getCompetitivePlayer.setIsEligible(true);
						competitivePlayerRepository.save(getCompetitivePlayer);
						autoSpawnMatchs(getType.getId());
					} else {
						getCompetitivePlayer.setIsEligible(false);
						competitivePlayerRepository.save(getCompetitivePlayer);
						autoSpawnMatchs(getType.getId());
					}
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					responseMessage.setMessage("Cập nhật cân nặng thành công. Tuyển thủ"
							+ (getCompetitivePlayer.getIsEligible() ? " " : " không ")
							+ "phù hợp thi đấu hạng cân này");
				} else {
					responseMessage.setMessage("Quá thời gian cập nhật cân nặng");
				}
			} else {
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
			if (competitivePlayerOp.isPresent()) {
				List<ExhibitionPlayer> exhibitionPlayers = exhibitionPlayerRepository
						.findAllByPlayerId(competitivePlayerOp.get().getTournamentPlayer().getId());
				CompetitivePlayer getCompetitivePlayer = competitivePlayerOp.get();
				CompetitiveType getType = getCompetitivePlayer.getCompetitiveType();
				if (getType.getStatus() < 2) {
					competitivePlayerRepository.delete(getCompetitivePlayer);
					responseMessage.setMessage("Xóa tuyển thủ thành công");
					responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					autoSpawnMatchs(getType.getId());
					if (exhibitionPlayers.size() == 0) {
						TournamentPlayer tournamentPlayer = tournamentPlayerRepository
								.findById(getCompetitivePlayer.getTournamentPlayer().getId()).get();
						tournamentPlayerRepository.delete(tournamentPlayer);
					}
				} else {
					if (getCompetitivePlayer.getIsEligible()) {
						responseMessage
								.setMessage("Không thể xóa. Tuyển thủ đã nằm trong danh sách thi đấu chính thức");
					} else {
						competitivePlayerRepository.delete(getCompetitivePlayer);
						responseMessage.setMessage("Xóa tuyển thủ thành công");
						responseMessage.setData(Arrays.asList(getCompetitivePlayer));
					}
				}
			} else {
				responseMessage.setMessage("Không tồn tại tuyển thủ để xóa");
			}
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
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				List<CompetitivePlayer> listPlayers = competitivePlayerRepository
						.findByCompetitiveTypeId(competitiveTypeId);
				CompetitivePlayerByTypeDto competitivePlayerByTypeDto = new CompetitivePlayerByTypeDto();
				competitivePlayerByTypeDto.setName((getType.isGender() ? "Nam " : "Nữ ") + getType.getWeightMin()
						+ " - " + getType.getWeightMax());
				competitivePlayerByTypeDto.setChanged(getType.isChanged());
				competitivePlayerByTypeDto.setStatus(getType.getStatus());
				competitivePlayerByTypeDto.setListPlayers(listPlayers);
				responseMessage.setData(Arrays.asList(competitivePlayerByTypeDto));
				responseMessage.setMessage("Danh sách tuyển thủ");
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
	public ResponseMessage updateResultMatch(int matchId, int firstPoint, int secondPoint) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveMatch getMatch = competitiveMatchRepository.findById(matchId).get();
			if (!getMatch.getStatus()) {
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findResultByMatchId(matchId);
				if (getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					getResult.setFirstPoint(firstPoint);
					getResult.setSecondPoint(secondPoint);
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(getResult);
					responseMessage.setMessage("Đã cập nhật tỉ số trận đấu");
					getMatch.setStatus(true);
					competitiveMatchRepository.save(getMatch);
					if (getMatch.getNextMatchId() != null) {
						CompetitiveMatch nextMatch = competitiveMatchRepository.getById(getMatch.getNextMatchId());
						String studentId = firstPoint > secondPoint ? getMatch.getFirstStudentId()
								: getMatch.getSecondStudentId();
						if (getMatch.isNextIsFirst()) {
							nextMatch.setFirstStudentId(studentId);
						} else {
							nextMatch.setSecondStudentId(studentId);
						}
						nextMatch.setUpdatedBy("LinhLHN");
						nextMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(nextMatch);
					}
					if (getMatch.getLoseMatchId() != null) {
						CompetitiveMatch loseMatch = competitiveMatchRepository.getById(getMatch.getLoseMatchId());
						String studentId = firstPoint < secondPoint ? getMatch.getFirstStudentId()
								: getMatch.getSecondStudentId();
						if (getMatch.isNextIsFirst()) {
							loseMatch.setFirstStudentId(studentId);
						} else {
							loseMatch.setSecondStudentId(studentId);
						}
						loseMatch.setUpdatedBy("LinhLHN");
						loseMatch.setUpdatedOn(LocalDateTime.now());
						competitiveMatchRepository.save(loseMatch);
					}
				} else {
					responseMessage.setMessage("Chưa tổ chức trận đấu");
				}
				return responseMessage;
			} else {
				responseMessage.setMessage("Trận đấu này đã có tỉ số chính thức");
				return responseMessage;
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getResultByType(int competitiveTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveResultByTypeDto competitiveResultByTypeDto = new CompetitiveResultByTypeDto();
			Optional<CompetitiveType> getTypeOp = competitiveTypeRepository.findById(competitiveTypeId);
			if (getTypeOp.isPresent()) {
				CompetitiveType getType = getTypeOp.get();
				competitiveResultByTypeDto.setCompetitiveType(getType);
				User getUser = new User();
				User[] listResult = new User[3];
				if (getType.getStatus() == 3) {
					List<CompetitiveMatch> listMatchs = competitiveMatchRepository
							.listMatchsByTypeDesc(competitiveTypeId);
					CompetitiveResult getResult = competitiveResultRepository
							.findResultByMatchId(listMatchs.get(1).getId()).get();
					if (getResult.getFirstPoint() == null || getResult.getSecondPoint() == null) {
						responseMessage.setMessage("Trận tranh hạng ba chưa diễn ra");
					} else {
						getUser = userRepository.findByStudentId(getResult.getFirstPoint() > getResult.getSecondPoint()
								? listMatchs.get(0).getFirstStudentId()
								: listMatchs.get(0).getSecondStudentId()).get();
						listResult[2] = getUser;
						getResult = competitiveResultRepository.findByMatchId(listMatchs.get(0).getId()).get();
						if (getResult.getFirstPoint() == null || getResult.getSecondPoint() == null) {
							responseMessage.setMessage("Trận chung kết chưa diễn ra");
						} else {
							if (getResult.getFirstPoint() > getResult.getSecondPoint()) {
								getUser = userRepository.findByStudentId(listMatchs.get(1).getFirstStudentId()).get();
								listResult[0] = getUser;
								getUser = userRepository.findByStudentId(listMatchs.get(1).getSecondStudentId()).get();
								listResult[1] = getUser;
							} else {
								getUser = userRepository.findByStudentId(listMatchs.get(1).getFirstStudentId()).get();
								listResult[1] = getUser;
								getUser = userRepository.findByStudentId(listMatchs.get(1).getSecondStudentId()).get();
								listResult[0] = getUser;
							}
							competitiveResultByTypeDto.setListResult(listResult);
							responseMessage
									.setMessage("Kết quả thi đấu ở thể thức " + (getType.isGender() ? "Nam: " : "Nữ: ")
											+ getType.getWeightMin() + " kg - " + getType.getWeightMax() + " kg");
						}
					}
				} else {
					responseMessage.setMessage("Chưa tổ chức thi đấu");
				}
				responseMessage.setData(Arrays.asList(competitiveResultByTypeDto));
			} else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
