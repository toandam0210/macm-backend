package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ExhibitionPlayerDto;
import com.fpt.macm.model.dto.ExhibitionResultByTypeDto;
import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.CompetitivePlayer;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionPlayerRegistration;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionTeamRegistration;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.ExhibitionTypeRegistration;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentOrganizingCommittee;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.TournamentPlayerPaymentStatusReport;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRegistrationRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentOrganizingCommitteeRepository;
import com.fpt.macm.repository.TournamentPlayerPaymentStatusReportRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {

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

	@Autowired
	ExhibitionTeamRepository exhibitionTeamRepository;

	@Autowired
	ExhibitionResultRepository exhibitionResultRepository;

	@Autowired
	TournamentOrganizingCommitteeRepository tournamentOrganizingCommitteeRepository;

	@Autowired
	ExhibitionTypeRegistrationRepository exhibitionTypeRegistrationRepository;

	@Autowired
	ClubFundService clubFundService;

	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;

	@Autowired
	TournamentPlayerPaymentStatusReportRepository tournamentPlayerPaymentStatusReportRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

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
			if (getTypeOp.isPresent()) {
				ExhibitionType getType = getTypeOp.get();
				Tournament getTournament = tournamentRepository
						.findById(exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId)).get();
				List<User> userJoined = new ArrayList<User>();
				List<User> listActive = userRepository.findAllActiveUser();
				List<TournamentPlayer> listPlayers = tournamentPlayerRepository
						.getPlayerByTournamentId(getTournament.getId());
				List<ExhibitionTypeRegistration> exhibitionTypeRegistrations = exhibitionTypeRegistrationRepository
						.findByExhibitionTypeId(exhibitionTypeId);
				for (TournamentPlayer tournamentPlayer : listPlayers) {
					boolean isContinue = false;
					for (ExhibitionTypeRegistration exhibitionTypeRegistration : exhibitionTypeRegistrations) {
						if (exhibitionTypeRegistration.getRegisterStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
							ExhibitionTeamRegistration exhibitionTeamRegistration = exhibitionTypeRegistration
									.getExhibitionTeamRegistration();
							Set<ExhibitionPlayerRegistration> exhibitionPlayersRegistration = exhibitionTeamRegistration
									.getExhibitionPlayersRegistration();
							for (ExhibitionPlayerRegistration exhibitionPlayerRegistration : exhibitionPlayersRegistration) {
								if (tournamentPlayer.getId() == exhibitionPlayerRegistration.getTournamentPlayer()
										.getId()) {
									User getUser = tournamentPlayer.getUser();
									userJoined.add(getUser);
									isContinue = true;
									break;
								}
							}
						}
						if (isContinue) {
							break;
						}
					}
					if (isContinue) {
						continue;
					}

					Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository
							.findByTournamentPlayerAndType(tournamentPlayer.getId(), exhibitionTypeId);
					if (getExhibitionPlayerOp.isPresent()) {
						User getUser = tournamentPlayer.getUser();
						userJoined.add(getUser);
					}
				}

				List<TournamentOrganizingCommittee> tournamentOrganizingCommittees = tournamentOrganizingCommitteeRepository
						.findByTournamentId(getTournament.getId());
				for (TournamentOrganizingCommittee tournamentOrganizingCommittee : tournamentOrganizingCommittees) {
					if (tournamentOrganizingCommittee.getRegisterStatus().equals(Constant.REQUEST_STATUS_APPROVED)
							|| tournamentOrganizingCommittee.getRegisterStatus()
									.equals(Constant.REQUEST_STATUS_PENDING)) {
						userJoined.add(tournamentOrganizingCommittee.getUser());
					}
				}

				List<User> userNotJoined = new ArrayList<User>();
				for (User user : listActive) {
					if (!userJoined.contains(user)) {
						userNotJoined.add(user);
					}
				}
				responseMessage.setData(userNotJoined);
				responseMessage.setTotalResult(userNotJoined.size());
				responseMessage.setMessage(
						"Danh sách thành viên chưa đăng ký tham gia thi đấu biểu diễn thể thức " + getType.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage registerTeam(int exhibitionTypeId, String name, List<String> listStudentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> exhibitionTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if (exhibitionTypeOp.isPresent()) {
				ExhibitionType exhibitionType = exhibitionTypeOp.get();
				ExhibitionTeam newTeam = new ExhibitionTeam();
				newTeam.setTeamName(name);
				Set<ExhibitionPlayer> listMembers = new HashSet<>();
				boolean isRegister = true;
				int countMale = 0;
				int countFemale = 0;
				for (String studentId : listStudentId) {
					User user = userRepository.findByStudentId(studentId).get();
					if (user.isGender()) {
						countMale++;
					} else {
						countFemale++;
					}
					Optional<TournamentPlayer> tournamentPlayerOp = tournamentPlayerRepository
							.getPlayerByUserIdAndTournamentId(user.getId(),
									exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
					if (tournamentPlayerOp.isPresent()) {
						TournamentPlayer tournamentPlayer = tournamentPlayerOp.get();

						List<ExhibitionTypeRegistration> exhibitionTypeRegistrations = exhibitionTypeRegistrationRepository
								.findByExhibitionTypeId(exhibitionTypeId);
						for (ExhibitionTypeRegistration exhibitionTypeRegistration : exhibitionTypeRegistrations) {
							if (exhibitionTypeRegistration.getRegisterStatus()
									.equals(Constant.REQUEST_STATUS_PENDING)) {
								ExhibitionTeamRegistration exhibitionTeamRegistration = exhibitionTypeRegistration
										.getExhibitionTeamRegistration();
								Set<ExhibitionPlayerRegistration> exhibitionPlayersRegistration = exhibitionTeamRegistration
										.getExhibitionPlayersRegistration();
								for (ExhibitionPlayerRegistration exhibitionPlayerRegistration : exhibitionPlayersRegistration) {
									if (tournamentPlayer.getId() == exhibitionPlayerRegistration.getTournamentPlayer()
											.getId()) {
										responseMessage.setMessage("Thành viên " + tournamentPlayer.getUser().getName()
												+ " - " + tournamentPlayer.getUser().getStudentId()
												+ " đã đăng ký nội dung này");
										return responseMessage;
									}
								}
							}
						}

						Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository
								.findByTournamentPlayerAndType(tournamentPlayer.getId(), exhibitionType.getId());
						if (getExhibitionPlayerOp.isPresent()) {
							isRegister = false;
							responseMessage.setMessage("Thành viên " + user.getName() + " - " + user.getStudentId()
									+ " đã đăng ký nội dung này");
							return responseMessage;
						} else {
							continue;
						}
					}
				}
				if (countMale != exhibitionType.getNumberMale() || countFemale != exhibitionType.getNumberFemale()) {
					responseMessage.setMessage("Số lượng thành viên không hợp lệ");
				} else if (isRegister) {
					for (int i = 0; i < listStudentId.size(); i++) {
						User getUser = userRepository.findByStudentId(listStudentId.get(i)).get();
						Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository
								.getPlayerByUserIdAndTournamentId(getUser.getId(),
										exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
						if (getTournamentPlayerOp.isPresent()) {
							TournamentPlayer getTournamentPlayer = getTournamentPlayerOp.get();
							ExhibitionPlayer newExhibitionPlayer = new ExhibitionPlayer();
							newExhibitionPlayer.setTournamentPlayer(getTournamentPlayer);
							newExhibitionPlayer.setRoleInTeam(i == 0 ? true : false);
							listMembers.add(newExhibitionPlayer);
						} else {
							TournamentPlayer newTournamentPlayer = new TournamentPlayer();
							newTournamentPlayer.setUser(getUser);
							newTournamentPlayer.setPaymentStatus(false);
							newTournamentPlayer.setCreatedBy("LinhLHN");
							newTournamentPlayer.setCreatedOn(LocalDateTime.now());
							newTournamentPlayer.setUpdatedBy("LinhLHN");
							newTournamentPlayer.setUpdatedOn(LocalDateTime.now());
							tournamentPlayerRepository.save(newTournamentPlayer);
							Tournament getTournament = tournamentRepository
									.getById(exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
							Set<TournamentPlayer> getTournamentPlayers = getTournament.getTournamentPlayers();
							getTournamentPlayers.add(newTournamentPlayer);
							getTournament.setTournamentPlayers(getTournamentPlayers);
							tournamentRepository.save(getTournament);
							TournamentPlayer getTournamentPlayer = tournamentPlayerRepository
									.findPlayerByUserIdAndTournamentId(getUser.getId(),
											exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId))
									.get();
							ExhibitionPlayer newExhibitionPlayer = new ExhibitionPlayer();
							newExhibitionPlayer.setTournamentPlayer(getTournamentPlayer);
							newExhibitionPlayer.setRoleInTeam(i == 0);
							listMembers.add(newExhibitionPlayer);
						}
					}
					newTeam.setExhibitionPlayers(listMembers);
					newTeam.setCreatedBy("LinhLHN");
					newTeam.setCreatedOn(LocalDateTime.now());
					newTeam.setUpdatedBy("LinhLHN");
					newTeam.setUpdatedOn(LocalDateTime.now());
					Set<ExhibitionTeam> getTeams = exhibitionType.getExhibitionTeams();
					getTeams.add(newTeam);
					exhibitionType.setExhibitionTeams(getTeams);
					exhibitionTeamRepository.save(newTeam);
					exhibitionTypeRepository.save(exhibitionType);
					responseMessage.setData(Arrays.asList(newTeam));
					responseMessage.setMessage("Đăng ký thành công");
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
	public ResponseMessage getTeamByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if (getTypeOp.isPresent()) {
				ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				responseMessage.setData(Arrays.asList(getTeams));
				responseMessage.setMessage("Danh sách các đội biểu diễn nội dung " + getType.getName());
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
	public ResponseMessage updateTeam(String studentId, int exhibitionTeamId, List<User> teamUsers) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionTeam> getTeamOp = exhibitionTeamRepository.findById(exhibitionTeamId);
			User admin = userRepository.findByStudentId(studentId).get();
			if (getTeamOp.isPresent()) {
				ExhibitionTeam getTeam = getTeamOp.get();
				List<ExhibitionPlayer> listOld = new ArrayList<ExhibitionPlayer>(getTeam.getExhibitionPlayers());
				ExhibitionType getType = exhibitionTypeRepository
						.findById(exhibitionTeamRepository.findTypeOfExhibitionTeam(exhibitionTeamId)).get();
				Tournament getTournament = tournamentRepository
						.findById(exhibitionTypeRepository.findTournamentOfType(getType.getId())).get();
				Set<ExhibitionPlayer> newPlayers = new HashSet<ExhibitionPlayer>();
				boolean role = true;
				for (User user : teamUsers) {
					User getUser = userRepository.getByStudentId(user.getStudentId());
					TournamentPlayer getTournamentPlayer = new TournamentPlayer();
					Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository
							.findPlayerByUserIdAndTournamentId(getUser.getId(), getTournament.getId());
					if (getTournamentPlayerOp.isPresent()) {
						getTournamentPlayer = getTournamentPlayerOp.get();
					} else {
						getTournamentPlayer.setUser(getUser);
						getTournamentPlayer.setPaymentStatus(false);
						getTournamentPlayer.setCreatedBy("LinhLHN");
						getTournamentPlayer.setCreatedOn(LocalDateTime.now());
						Set<TournamentPlayer> tournamentPlayers = getTournament.getTournamentPlayers();
						tournamentPlayers.add(getTournamentPlayer);
						getTournament.setTournamentPlayers(tournamentPlayers);
						tournamentRepository.save(getTournament);
						getTournamentPlayer = tournamentPlayerRepository
								.getPlayerByUserIdAndTournamentId(getUser.getId(), getTournament.getId()).get();
					}
					ExhibitionPlayer newPlayer = new ExhibitionPlayer();
					newPlayer.setTournamentPlayer(getTournamentPlayer);
					newPlayer.setRoleInTeam(role);
					newPlayer.setCreatedBy("LinhLHN");
					newPlayer.setCreatedOn(LocalDateTime.now());
					newPlayers.add(newPlayer);
					role = false;
				}
				getTeam.setExhibitionPlayers(newPlayers);
				exhibitionTeamRepository.save(getTeam);
				responseMessage.setData(Arrays.asList(getTeam));
				responseMessage.setMessage("Cập nhật thành viên thành công");
				for (ExhibitionPlayer exhibitionPlayer : listOld) {
					TournamentPlayer getTournamentPlayer = exhibitionPlayer.getTournamentPlayer();
					Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository
							.findByTournamentPlayerAndType(getTournamentPlayer.getId(), getType.getId());
					List<ExhibitionPlayer> listExhibitionByUserAndExhibitionNull = exhibitionPlayerRepository
							.findAllByPlayerIdAndExhibitionNull(getTournamentPlayer.getId());
					for (ExhibitionPlayer exhibitionPlayerNull : listExhibitionByUserAndExhibitionNull) {
						exhibitionPlayerRepository.delete(exhibitionPlayerNull);
					}
					if (getExhibitionPlayerOp.isPresent()) {
						continue;
					}
					List<ExhibitionPlayer> listExhibitionByUser = exhibitionPlayerRepository
							.findAllByPlayerId(getTournamentPlayer.getId());
					Optional<CompetitivePlayer> getCompetitivePlayerOp = competitivePlayerRepository
							.findCompetitivePlayerByTournamentPlayerId(getTournamentPlayer.getId());
					if (listExhibitionByUser.size() == 0 && !getCompetitivePlayerOp.isPresent()) {
						if (getTournamentPlayer.isPaymentStatus()) {
							clubFundService.withdrawFromClubFund(admin.getStudentId(), getTournament.getFeePlayerPay(),
									("Hoàn phí đăng ký tham gia giải đấu cho tuyển thủ "
											+ getTournamentPlayer.getUser().getName()) + " - "
											+ getTournamentPlayer.getUser().getStudentId());

							List<ClubFund> clubFunds = clubFundRepository.findAll();
							ClubFund clubFund = clubFunds.get(0);
							double fundAmount = clubFund.getFundAmount();

							double tournamentFee = getTournament.getFeePlayerPay();

							double fundBalance = fundAmount - tournamentFee;

							TournamentPlayerPaymentStatusReport tournamentPlayerPaymentStatusReport = new TournamentPlayerPaymentStatusReport();
							tournamentPlayerPaymentStatusReport.setTournament(getTournament);
							tournamentPlayerPaymentStatusReport.setUser(getTournamentPlayer.getUser());
							tournamentPlayerPaymentStatusReport.setPaymentStatus(false);
							tournamentPlayerPaymentStatusReport.setFundChange(-tournamentFee);
							tournamentPlayerPaymentStatusReport.setFundBalance(fundBalance);
							tournamentPlayerPaymentStatusReport
									.setCreatedBy(admin.getName() + " - " + admin.getStudentId());
							tournamentPlayerPaymentStatusReport.setCreatedOn(LocalDateTime.now());
							tournamentPlayerPaymentStatusReportRepository.save(tournamentPlayerPaymentStatusReport);

						}
						tournamentPlayerRepository.deleteById(getTournamentPlayer.getId());
					}
				}
			} else {
				responseMessage.setMessage("Không tìm thấy đội");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListExhibitionResult(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ExhibitionResult> listResult = new ArrayList<ExhibitionResult>();
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if (getTypeOp.isPresent()) {
				ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
				ExhibitionResultByTypeDto resultByTypeDto = new ExhibitionResultByTypeDto();
				List<ExhibitionTeamDto> listTeamDto = new ArrayList<ExhibitionTeamDto>();
				resultByTypeDto.setExhibitionType(getType);
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				for (ExhibitionTeam exhibitionTeam : getTeams) {
					ExhibitionTeamDto teamDto = new ExhibitionTeamDto();
					teamDto.setId(exhibitionTeam.getId());
					teamDto.setTeamName(exhibitionTeam.getTeamName());
					teamDto.setExhibitionTypeId(exhibitionTypeId);
					teamDto.setExhibitionTypeName(getType.getName());
					Set<ExhibitionPlayerDto> listExhibitionPlayerDtos = new HashSet<ExhibitionPlayerDto>();
					for (ExhibitionPlayer exhibitionPlayer : exhibitionTeam.getExhibitionPlayers()) {
						ExhibitionPlayerDto exhibitionPlayerDto = new ExhibitionPlayerDto();
						exhibitionPlayerDto.setId(exhibitionPlayer.getId());
						exhibitionPlayerDto
								.setPlayerGender(exhibitionPlayer.getTournamentPlayer().getUser().isGender());
						exhibitionPlayerDto.setPlayerId(exhibitionPlayer.getTournamentPlayer().getId());
						exhibitionPlayerDto.setPlayerName(exhibitionPlayer.getTournamentPlayer().getUser().getName());
						exhibitionPlayerDto
								.setPlayerStudentId(exhibitionPlayer.getTournamentPlayer().getUser().getStudentId());
						exhibitionPlayerDto.setRoleInTeam(exhibitionPlayer.isRoleInTeam());
						listExhibitionPlayerDtos.add(exhibitionPlayerDto);
					}
					teamDto.setExhibitionPlayersDto(listExhibitionPlayerDtos);
					Optional<ExhibitionResult> getResultOp = exhibitionResultRepository
							.findByTeam(exhibitionTeam.getId());
					if (getResultOp.isPresent()) {
						ExhibitionResult getResult = getResultOp.get();
						teamDto.setScore(getResult.getScore());
						teamDto.setTime(getResult.getTime());
						teamDto.setAreaName(getResult.getArea().getName());
					}

					listTeamDto.add(teamDto);
				}
				resultByTypeDto.setListResult(listTeamDto);
				responseMessage.setData(Arrays.asList(resultByTypeDto));
				responseMessage.setMessage("Danh sách trận đấu biểu diễn thể thức " + getType.getName());
			} else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
			if (listResult.size() > 0) {
				Collections.sort(listResult, new Comparator<ExhibitionResult>() {
					@Override
					public int compare(ExhibitionResult o1, ExhibitionResult o2) {
						// TODO Auto-generated method stub
						return o1.getTime().compareTo(o2.getTime());
					}
				});
				responseMessage.setData(listResult);
				responseMessage.setMessage("Danh sách các trận đấu");
			} else {
				responseMessage.setMessage("Không có trận nào");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateExhibitionResult(int exhibitionTeamId, double score) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionResult> getResultOp = exhibitionResultRepository.findByTeam(exhibitionTeamId);
			if (getResultOp.isPresent()) {
				ExhibitionResult getResult = getResultOp.get();
				getResult.setScore(score);
				getResult.setUpdatedBy("LinhLHN");
				getResult.setUpdatedOn(LocalDateTime.now());
				exhibitionResultRepository.save(getResult);
				responseMessage.setData(Arrays.asList(getResult));
				responseMessage.setMessage("Cập nhật điểm cho đội "
						+ exhibitionTeamRepository.findById(exhibitionTeamId).get().getTeamName());
			} else {
				responseMessage.setMessage("Không tìm thấy đội");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getExhibitionResultByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if (getTypeOp.isPresent()) {
				ExhibitionResultByTypeDto exhibitionResultByTypeDto = new ExhibitionResultByTypeDto();
				ExhibitionType getType = getTypeOp.get();
				exhibitionResultByTypeDto.setExhibitionType(getType);
				responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				if (getTeams.size() < 3) {
					responseMessage.setMessage("Thể thức này không đủ số đội tham gia");
				} else {
					List<ExhibitionTeamDto> listResult = new ArrayList<ExhibitionTeamDto>();
					for (ExhibitionTeam exhibitionTeam : getTeams) {
						Optional<ExhibitionResult> exhibitionResultOp = exhibitionResultRepository
								.findByTeam(exhibitionTeam.getId());
						if (exhibitionResultOp.isPresent()) {
							ExhibitionResult exhibitionResult = exhibitionResultOp.get();
							if (exhibitionResult.getScore() == null) {
								responseMessage.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa có điểm");
								return responseMessage;
							} else {
								ExhibitionTeamDto exhibitionTeamDto = new ExhibitionTeamDto();
								exhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
								exhibitionTeamDto.setScore(exhibitionResult.getScore());
								listResult.add(exhibitionTeamDto);
							}
						} else {
							responseMessage
									.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa được xếp lịch thi đấu");
							return responseMessage;
						}
					}
					Collections.sort(listResult);
					List<Double> listScore = new ArrayList<Double>();
					for (ExhibitionTeamDto exhibitionTeamDto : listResult) {
						if (!listScore.contains(exhibitionTeamDto.getScore())) {
							listScore.add(exhibitionTeamDto.getScore());
						}
					}
					for (ExhibitionTeamDto exhibitionTeamDto : listResult) {
						for (int i = 0; i < listScore.size(); i++) {
							if (exhibitionTeamDto.getScore().equals(listScore.get(i))) {
								exhibitionTeamDto.setRank(i + 1);
							}
						}
					}
					exhibitionResultByTypeDto.setListResult(listResult);
					responseMessage.setMessage("Kết quả thi đấu của nội dung " + getType.getName());
				}
				responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
