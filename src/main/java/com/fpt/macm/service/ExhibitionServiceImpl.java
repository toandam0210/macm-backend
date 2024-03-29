package com.fpt.macm.service;

import java.time.LocalDate;
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

import com.fpt.macm.model.dto.ExhibitionResultByTypeDto;
import com.fpt.macm.model.dto.ExhibitionTeamDto;
import com.fpt.macm.model.entity.ExhibitionPlayer;
import com.fpt.macm.model.entity.ExhibitionResult;
import com.fpt.macm.model.entity.ExhibitionTeam;
import com.fpt.macm.model.entity.ExhibitionType;
import com.fpt.macm.model.entity.Tournament;
import com.fpt.macm.model.entity.TournamentPlayer;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ExhibitionPlayerRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.ExhibitionTeamRepository;
import com.fpt.macm.repository.ExhibitionTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class ExhibitionServiceImpl implements ExhibitionService{

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
	
	@Override
	public ResponseMessage registerTeam(int exhibitionTypeId, String name, List<String> listStudentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if(getTypeOp.isPresent()) {
				ExhibitionType getType = getTypeOp.get();
				ExhibitionTeam newTeam = new ExhibitionTeam();
				newTeam.setTeamName(name);
				Set<ExhibitionPlayer> listMembers = new HashSet<>();
				boolean isRegister = true;
				int countMale = 0;
				int countFemale = 0;
				for (String studentId : listStudentId) {
					User getUser = userRepository.findByStudentId(studentId).get();
					if (getUser.isGender()) {
						countMale++;
					} else {
						countFemale++;
					}
					Optional<TournamentPlayer> getTournamentPlayerOp = tournamentPlayerRepository
							.getPlayerByUserIdAndTournamentId(getUser.getId(),
									exhibitionTypeRepository.findTournamentOfType(exhibitionTypeId));
					if (getTournamentPlayerOp.isPresent()) {
						TournamentPlayer getTournamentPlayer = getTournamentPlayerOp.get();
						Optional<ExhibitionPlayer> getExhibitionPlayerOp = exhibitionPlayerRepository
								.findByTournamentPlayerAndType(getTournamentPlayer.getId(), getType.getId());
						if (getExhibitionPlayerOp.isPresent()) {
							isRegister = false;
							responseMessage.setMessage("Thành viên " + getUser.getName() + " - " + getUser.getStudentId()
									+ " đã đăng ký nội dung này");
							return responseMessage;
						} else {
							continue;
						}
					}
				}
				if (countMale != getType.getNumberMale() || countFemale != getType.getNumberFemale()) {
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
					Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
					getTeams.add(newTeam);
					getType.setExhibitionTeams(getTeams);
					exhibitionTeamRepository.save(newTeam);
					exhibitionTypeRepository.save(getType);
					responseMessage.setData(Arrays.asList(newTeam));
					responseMessage.setMessage("Đăng ký thành công");
				}
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

	@Override
	public ResponseMessage getTeamByType(int exhibitionTypeId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
			if(getTypeOp.isPresent()) {
				ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				responseMessage.setData(Arrays.asList(getTeams));
				responseMessage.setMessage("Danh sách các đội biểu diễn nội dung " + getType.getName());
			}
			else {
				responseMessage.setMessage("Không tìm thấy thể thức");
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTeam(int exhibitionTeamId, List<User> teamUsers) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ExhibitionTeam> getTeamOp = exhibitionTeamRepository.findById(exhibitionTeamId);
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
					if (listExhibitionByUser.size() == 0) {
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
	public ResponseMessage getListExhibitionResult(int exhibitionTypeId, String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ExhibitionResult> listResult = new ArrayList<ExhibitionResult>();
			if (exhibitionTypeId != 0) {
				Optional<ExhibitionType> getTypeOp = exhibitionTypeRepository.findById(exhibitionTypeId);
				if(getTypeOp.isPresent()) {
					ExhibitionType getType = exhibitionTypeRepository.findById(exhibitionTypeId).get();
					Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
					for (ExhibitionTeam exhibitionTeam : getTeams) {
						ExhibitionResult getResult = exhibitionResultRepository.findByTeam(exhibitionTeam.getId()).get();
						listResult.add(getResult);
					}
				}
				else {
					responseMessage.setMessage("Không tìm thấy thể thức");
				}
			} else if (date != "") {
				LocalDate getDate = Utils.ConvertStringToLocalDate(date);
				List<ExhibitionResult> listAll = exhibitionResultRepository.findAll();
				for (ExhibitionResult exhibitionResult : listAll) {
					if (exhibitionResult.getTime().toLocalDate().equals(getDate)) {
						listResult.add(exhibitionResult);
					}
				}
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
			if(getResultOp.isPresent()) {
				ExhibitionResult getResult = getResultOp.get();
				getResult.setScore(score);
				getResult.setUpdatedBy("LinhLHN");
				getResult.setUpdatedOn(LocalDateTime.now());
				exhibitionResultRepository.save(getResult);
				responseMessage.setData(Arrays.asList(getResult));
				responseMessage.setMessage(
						"Cập nhật điểm cho đội " + exhibitionTeamRepository.findById(exhibitionTeamId).get().getTeamName());
			}
			else {
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
			if(getTypeOp.isPresent()) {
				ExhibitionResultByTypeDto exhibitionResultByTypeDto = new ExhibitionResultByTypeDto();
				ExhibitionType getType = getTypeOp.get();
				exhibitionResultByTypeDto.setExhibitionType(getType);
				//responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
				Set<ExhibitionTeam> getTeams = getType.getExhibitionTeams();
				List<ExhibitionTeamDto> listResult = new ArrayList<ExhibitionTeamDto>();
				for (ExhibitionTeam exhibitionTeam : getTeams) {
					Optional<ExhibitionResult> exhibitionResultOp = exhibitionResultRepository.findByTeam(exhibitionTeam.getId());
					if(exhibitionResultOp.isPresent()) {
						ExhibitionResult exhibitionResult = exhibitionResultOp.get();
						if(exhibitionResult.getScore() == null) {
							responseMessage.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa có điểm");
							return responseMessage;
						} else {
							ExhibitionTeamDto exhibitionTeamDto = new ExhibitionTeamDto();
							exhibitionTeamDto.setTeamName(exhibitionTeam.getTeamName());
							exhibitionTeamDto.setScore(exhibitionResult.getScore());
							listResult.add(exhibitionTeamDto);
						}
					}
					else {
						responseMessage.setMessage("Đội " + exhibitionTeam.getTeamName() + " chưa được xếp lịch thi đấu");
						return responseMessage;
					}
				}
				Collections.sort(listResult);
				List<Double> listScore = new ArrayList<Double>();
				for (ExhibitionTeamDto exhibitionTeamDto : listResult) {
					if(!listScore.contains(exhibitionTeamDto.getScore())) {
						listScore.add(exhibitionTeamDto.getScore());
					}
				}
				for (ExhibitionTeamDto exhibitionTeamDto : listResult) {
					for (int i = 0; i < listScore.size(); i++) {
						if(exhibitionTeamDto.getScore().equals(listScore.get(i))) {
							exhibitionTeamDto.setRank(i + 1);
						}
					}
				}
				exhibitionResultByTypeDto.setListResult(listResult);
				responseMessage.setData(Arrays.asList(exhibitionResultByTypeDto));
				responseMessage.setMessage("Kết quả thi đấu của nội dung " + getType.getName());
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
