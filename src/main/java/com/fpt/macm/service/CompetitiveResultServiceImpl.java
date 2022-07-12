package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Area;
import com.fpt.macm.model.CompetitiveMatch;
import com.fpt.macm.model.CompetitiveResult;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;
import com.fpt.macm.repository.CompetitiveMatchRepository;
import com.fpt.macm.repository.CompetitivePlayerBracketRepository;
import com.fpt.macm.repository.CompetitivePlayerRepository;
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.repository.CompetitiveTypeRepository;
import com.fpt.macm.repository.TournamentPlayerRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CompetitiveResultServiceImpl implements CompetitiveResultService{
	
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
	
	@Override
	public ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			CompetitiveMatch getMatch = competitiveMatchRepository.findById(matchId).get();
			if(getMatch.getFirstStudentId() == null || getMatch.getSecondStudentId() == null) {
				responseMessage.setMessage("Không thể tổ chức trận đấu chỉ có một tuyển thủ");
			}
			else {
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(matchId);
				if(getResultOp.isPresent()) {
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
				}
				else {
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
			CompetitiveMatch nextMatch = competitiveMatchRepository.getById(getMatch.getNextMatchId());
			if(getMatch.getFirstStudentId() == null || getMatch.getSecondStudentId() == null) {
				responseMessage.setMessage("Trận đấu này chưa đủ tuyển thủ");
			}
			else {
				Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findByMatchId(matchId);
				if(getResultOp.isPresent()) {
					CompetitiveResult getResult = getResultOp.get();
					if(getResult.getTime() != null) {
						getResult.setFirstPoint(firstPoint);
						getResult.setSecondPoint(secondPoint);
						getResult.setUpdatedBy("LinhLHN");
						getResult.setUpdatedOn(LocalDateTime.now());
						competitiveResultRepository.save(getResult);
						if(firstPoint > secondPoint) {
							if(getMatch.isNextIsFirst()) {
								nextMatch.setFirstStudentId(getMatch.getFirstStudentId());
							}
							else {
								nextMatch.setSecondStudentId(getMatch.getFirstStudentId());
							}
							nextMatch.setUpdatedBy("LinhLHN");
							nextMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(nextMatch);
							responseMessage.setData(Arrays.asList(getMatch.getFirstStudentId()));
							responseMessage.setMessage("Tuyển thủ thứ nhất thắng");
						}
						else {
							if(getMatch.isNextIsFirst()) {
								nextMatch.setFirstStudentId(getMatch.getSecondStudentId());
							}
							else {
								nextMatch.setSecondStudentId(getMatch.getSecondStudentId());
							}
							nextMatch.setUpdatedBy("LinhLHN");
							nextMatch.setUpdatedOn(LocalDateTime.now());
							competitiveMatchRepository.save(nextMatch);
							responseMessage.setData(Arrays.asList(getMatch.getSecondStudentId()));
							responseMessage.setMessage("Tuyển thủ thứ hai thắng");
						}
					}
					else {
						responseMessage.setMessage("Chưa có thời gian nên không thể cập nhật kết quả");
					}
				}
				else {
					responseMessage.setMessage("Chưa có địa điểm nên không thể cập nhật kết quả");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
