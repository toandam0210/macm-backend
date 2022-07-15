package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.model.entity.TournamentSchedule;

import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CompetitiveResultServiceImpl implements CompetitiveResultService {

	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;

	@Autowired
	CompetitiveResultRepository competitiveResultRepository;

	@Autowired
	CompetitiveTypeRepository competitiveTypeRepository;

	@Autowired
	AreaRepository areaRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TournamentPlayerRepository tournamentPlayerRepository;

	@Autowired
	CompetitivePlayerRepository competitivePlayerRepository;

	@Autowired
	CompetitivePlayerBracketRepository competitivePlayerBracketRepository;

	@Autowired
	TournamentScheduleRepository tournamentScheduleRepository;

	@Autowired
	CompetitiveTypeService competitiveTypeService;

	@Override
	public ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveMatch getMatch = competitiveMatchRepository.findById(matchId).get();
			if (getMatch.getFirstStudentId() == null || getMatch.getSecondStudentId() == null) {
				responseMessage.setMessage("Không thể tổ chức trận đấu chỉ có một tuyển thủ");
			} else {
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(matchId);
				if (getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					Area area = areaRepository.getById(areaId);
					getResult.setArea(area);
					LocalDateTime getTime = Utils.ConvertStringToLocalDateTime(time);
					getResult.setTime(getTime);
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(getResult);
					responseMessage.setData(Arrays.asList(getResult));
					responseMessage.setMessage("Cập nhật thời gian và địa điểm cho trận đấu có id là " + matchId);
				} else {
					CompetitiveResult newResult = new CompetitiveResult();
					newResult.setMatch(getMatch);
					Area area = areaRepository.findById(areaId).get();
					newResult.setArea(area);
					LocalDateTime getTime = Utils.ConvertStringToLocalDateTime(time);
					newResult.setTime(getTime);
					newResult.setFirstPoint(0);
					newResult.setSecondPoint(0);
					newResult.setCreatedBy("LinhLHN");
					newResult.setCreatedOn(LocalDateTime.now());
					newResult.setUpdatedBy("LinhLHN");
					newResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(newResult);
					responseMessage.setData(Arrays.asList(newResult));
					responseMessage.setMessage("Tạo thời gian và địa điểm cho trận đấu có id là " + matchId);
				}
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
			if (getMatch.getFirstStudentId() == null || getMatch.getSecondStudentId() == null) {
				responseMessage.setMessage("Trận đấu này chưa đủ tuyển thủ");
			} else {
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(matchId);
				if (getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					getResult.setFirstPoint(firstPoint);
					getResult.setSecondPoint(secondPoint);
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(getResult);
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
					// responseMessage.setMessage("Chưa tổ chức trận đấu");
					CompetitiveResult newResult = new CompetitiveResult();
					newResult.setArea(areaRepository.getById(1));
					newResult.setTime(LocalDateTime.now());
					newResult.setMatch(getMatch);
					newResult.setFirstPoint(firstPoint);
					newResult.setSecondPoint(secondPoint);
					newResult.setCreatedBy("LinhLHN");
					newResult.setCreatedOn(LocalDateTime.now());
					newResult.setUpdatedBy("LinhLHN");
					newResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(newResult);
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
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage spawnTimeAndArea(int tournamentId, List<Area> listArea) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitiveMatch> listMatch = new ArrayList<CompetitiveMatch>();
			Set<CompetitiveType> listType = competitiveTypeService.getAllTypeByTournament(tournamentId);
			for (CompetitiveType competitiveType : listType) {
				List<CompetitiveMatch> listMatchByType = competitiveMatchRepository
						.listMatchsByType(competitiveType.getId());
				for (CompetitiveMatch competitiveMatch : listMatchByType) {
					if (competitiveMatch.getRound() == 1 && (competitiveMatch.getFirstStudentId() == null
							|| competitiveMatch.getSecondStudentId() == null)) {
						continue;
					}
					listMatch.add(competitiveMatch);
				}
			}
			Collections.sort(listMatch);
			List<TournamentSchedule> listTournamentSchedules = tournamentScheduleRepository
					.findByTournamentId(tournamentId);
			List<CompetitiveResult> listResult = new ArrayList<CompetitiveResult>();
			int countMatchNeedHeld = listMatch.size();
			int countMatchCanHeld = 0;
			for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
				LocalTime startTime = tournamentSchedule.getStartTime();
				LocalTime finishTime = tournamentSchedule.getFinishTime();
				countMatchCanHeld += ((finishTime.getHour() - startTime.getHour()) * 60 + finishTime.getMinute()
						- startTime.getMinute()) / 10;
			}
			if (countMatchCanHeld >= countMatchNeedHeld) {
				boolean isRunning = true;
				int index = 0;
				CompetitiveResult oldResult = new CompetitiveResult();
				for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
					if (isRunning) {
						LocalDate date = tournamentSchedule.getDate();
						LocalTime startTime = tournamentSchedule.getStartTime();
						LocalTime finishTime = tournamentSchedule.getFinishTime();
						while (startTime.isBefore(finishTime)) {
							if (isRunning) {
								for (Area area : listArea) {
									LocalDateTime timeMatch = LocalDateTime.of(date, startTime);
									if (isRunning) {
										CompetitiveResult newResult = new CompetitiveResult();
										newResult.setMatch(listMatch.get(index));
										if (index > 0
												&& oldResult.getMatch().getRound() < newResult.getMatch().getRound()
												&& oldResult.getTime().equals(timeMatch)) {
											startTime = startTime.plusMinutes(10);
											timeMatch = LocalDateTime.of(date, startTime);
										}
										newResult.setTime(timeMatch);
										newResult.setArea(area);
										newResult.setCreatedBy("LinhLHN");
										newResult.setCreatedOn(LocalDateTime.now());
										newResult.setUpdatedBy("LinhLHN");
										newResult.setUpdatedOn(LocalDateTime.now());
										competitiveResultRepository.save(newResult);
										listResult.add(newResult);
										oldResult = newResult;
										index++;
										if (index == listMatch.size()) {
											isRunning = false;
										}
									} else {
										break;
									}
								}
								startTime = startTime.plusMinutes(10);
							} else {
								break;
							}
						}
					} else {
						break;
					}
				}
				responseMessage.setData(listResult);
				responseMessage.setMessage("Danh sách trận đấu");
			} else {
				responseMessage.setMessage("Không thành công. Chỉ đủ thời gian để tổ chức " + countMatchCanHeld + "/"
						+ countMatchNeedHeld + " trận đấu");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	public int isEnoughTime(List<CompetitiveMatch> listMatch, List<TournamentSchedule> listTournamentSchedules) {
		try {
			int countMatchNeedHeld = 0;
			for (CompetitiveMatch competitiveMatch : listMatch) {
				if (competitiveMatch.getRound() == 1 && (competitiveMatch.getFirstStudentId() == null
						|| competitiveMatch.getSecondStudentId() == null)) {
					continue;
				}
				countMatchNeedHeld++;
			}
			int countMatchCanHeld = 0;
			for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
				LocalTime startTime = tournamentSchedule.getStartTime();
				LocalTime finishTime = tournamentSchedule.getFinishTime();
				countMatchCanHeld += ((finishTime.getHour() - startTime.getHour()) * 60 + finishTime.getMinute()
						- startTime.getMinute()) / 10;
			}
			return countMatchCanHeld - countMatchNeedHeld;
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
}
