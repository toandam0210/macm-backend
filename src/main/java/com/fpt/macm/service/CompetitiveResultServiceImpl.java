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

import com.fpt.macm.model.dto.CompetitiveResultByTypeDto;
import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.entity.CompetitiveMatch;
import com.fpt.macm.model.entity.CompetitiveResult;
import com.fpt.macm.model.entity.CompetitiveType;
import com.fpt.macm.model.entity.TournamentSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.ExhibitionResultRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.TournamentRepository;
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
	TournamentScheduleRepository tournamentScheduleRepository;

	@Autowired
	CompetitiveTypeService competitiveTypeService;

	@Autowired
	TournamentRepository tournamentRepository;

	@Autowired
	ExhibitionResultRepository exhibitionResultRepository;

	
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
