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
import com.fpt.macm.repository.CompetitiveResultRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CompetitiveResultServiceImpl implements CompetitiveResultService{
	
	@Autowired
	CompetitiveMatchRepository competitiveMatchRepository;
	
	@Autowired
	CompetitiveResultRepository competitiveResultRepository;
	
	@Autowired
	AreaRepository areaRepository;
	
	@Override
	public ResponseMessage updateTimeAndPlaceMatch(int matchId, int areaId, String time) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
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
			}
			else {
				CompetitiveResult newResult = new CompetitiveResult();
				CompetitiveMatch getMatch = competitiveMatchRepository.getById(matchId);
				newResult.setMatch(getMatch);
				Area area = areaRepository.getById(areaId);
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
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateResultMatch(int resultId, int firstPoint, int secondPoint) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<CompetitiveResult> getResultOp = competitiveResultRepository.findById(resultId);
			if(getResultOp.isPresent()) {
				CompetitiveResult getResult = getResultOp.get();
				if(getResult.getArea() != null && getResult.getTime() != null) {
					getResult.setFirstPoint(firstPoint);
					getResult.setSecondPoint(secondPoint);
					getResult.setUpdatedBy("LinhLHN");
					getResult.setUpdatedOn(LocalDateTime.now());
					competitiveResultRepository.save(getResult);
					responseMessage.setData(Arrays.asList(getResult));
					responseMessage.setMessage("Cập nhật kết quả thành công");
				}
				else {
					responseMessage.setMessage("Chưa có thời gian địa điểm nên không thể cập nhật kết quả");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
