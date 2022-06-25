package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.utils.Utils;

@Service
public class CommonScheduleServiceImpl implements CommonScheduleService{

	@Autowired
	CommonScheduleRepository commonScheduleRepository;
	
	@Override
	public CommonSchedule getCommonSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<CommonSchedule> getSessionOp = commonScheduleRepository.findByDate(date);
			if(getSessionOp != null) {
				return getSessionOp.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ResponseMessage getCommonSchedule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<CommonSchedule> getAllCommonSchedule = commonScheduleRepository.findAll();
			responseMessage.setData(getAllCommonSchedule);
			responseMessage.setMessage(Constant.MSG_105);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getCommonSessionByDate(String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			Optional<CommonSchedule> getSessionOp = commonScheduleRepository.findByDate(getDate);
			if(getSessionOp != null) {
				responseMessage.setData(Arrays.asList(getSessionOp.get()));
				responseMessage.setMessage(Constant.MSG_103);
			}
			else {
				responseMessage.setMessage(Constant.MSG_104);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
