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
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentScheduleRepository;
import com.fpt.macm.repository.UserRepository;

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
	public ResponseMessage spawnTimeAndArea(int tournamentId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CompetitiveMatch> listMatch = new ArrayList<CompetitiveMatch>();
			Set<CompetitiveType> listType = competitiveTypeService.getAllTypeByTournament(tournamentId);
			List<Area> listArea = areaRepository.findAll();
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
				int matchEveryDay = countMatchNeedHeld / listTournamentSchedules.size();
				int matchSurplus = countMatchNeedHeld % listTournamentSchedules.size();
				int index = 0;
				CompetitiveResult oldResult = new CompetitiveResult();
				int countSchedule = 0;
				boolean isJumpDay;
				for (TournamentSchedule tournamentSchedule : listTournamentSchedules) {
					isJumpDay = false;
					countSchedule++;
					int countMatchEveryDay = 0;
					if (isRunning) {
						LocalDate date = tournamentSchedule.getDate();
						LocalTime startTime = tournamentSchedule.getStartTime();
						LocalTime finishTime = tournamentSchedule.getFinishTime();
						while (startTime.isBefore(finishTime)) {
							if (isJumpDay) {
								startTime = startTime.plusMinutes(10);
								continue;
							}
							if (isRunning) {
								for (Area area : listArea) {
									if (isJumpDay) {
										continue;
									}
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
										countMatchEveryDay++;
										index++;
										if (countSchedule > matchSurplus) {
											if (countMatchEveryDay == matchEveryDay) {
												isJumpDay = true;
											}
										} else {
											if (countMatchEveryDay == matchEveryDay + 1) {
												isJumpDay = true;
											}
										}
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

	@Override
	public ResponseMessage updateTimeAndArea(int matchId, CompetitiveResult newResult) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = LocalDate.of(newResult.getTime().getYear(), newResult.getTime().getMonthValue(),
					newResult.getTime().getDayOfMonth());
			List<CompetitiveResult> listResult = competitiveResultRepository
					.listResultByAreaOrderTime(newResult.getArea().getId());
			List<CompetitiveResult> listResultAtDate = new ArrayList<CompetitiveResult>();
			for (CompetitiveResult competitiveResult : listResult) {
				LocalDate currentDate = competitiveResult.getTime().toLocalDate();
				if (getDate.equals(currentDate)) {
					listResultAtDate.add(competitiveResult);
				}
			}
			int checkExisted = -1;
			for (int i = 0; i < listResultAtDate.size(); i++) {
				if (i == listResultAtDate.size() - 1) {
					if (listResultAtDate.get(i).getTime().compareTo(newResult.getTime()) <= 0
							&& listResultAtDate.get(i).getTime().plusMinutes(10).compareTo(newResult.getTime()) > 0
							&& !listResultAtDate.get(i).getMatch().equals(newResult.getMatch())) {
						checkExisted = i;
						break;
					}
				}
				if (listResultAtDate.get(i).getTime().compareTo(newResult.getTime()) <= 0
						&& listResultAtDate.get(i).getTime().plusMinutes(10).compareTo(newResult.getTime()) > 0
						&& listResultAtDate.get(i + 1).getTime().compareTo(newResult.getTime().plusMinutes(10)) < 0) {
					if (listResultAtDate.get(i).getMatch().equals(newResult.getMatch())) {
						checkExisted = i + 1;
						break;
					} else {
						checkExisted = i;
						break;
					}
				}
			}
			if (checkExisted == -1) {
				CompetitiveResult getResult = competitiveResultRepository.findByMatchId(matchId).get();
				getResult.setArea(newResult.getArea());
				getResult.setTime(newResult.getTime());
				getResult.setUpdatedBy("LinhLHN");
				getResult.setUpdatedOn(LocalDateTime.now());
				competitiveResultRepository.save(getResult);
				responseMessage.setData(Arrays.asList(getResult));
				responseMessage.setMessage("Cập nhật thời gian và địa điểm thành công");
			} else {
				responseMessage.setMessage("Bị trùng với trận khác diễn ra trên cùng sân vào lúc "
						+ listResultAtDate.get(checkExisted).getTime());
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
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(matchId);
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
}
