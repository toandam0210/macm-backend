package com.fpt.macm.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.ClubFund;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;

@Service
public class ClubFundServiceImpl implements ClubFundService{

	@Autowired
	ClubFundRepository clubFundRepository;
	
	@Override
	public ResponseMessage getClubFund() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
			ClubFund clubFund = clubFundOp.get();
			responseMessage.setData(Arrays.asList(clubFund));
			responseMessage.setMessage(Constant.MSG_090);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage depositToClubFund(double amount) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
			ClubFund clubFund = clubFundOp.get();
			clubFund.setFundAmount(clubFund.getFundAmount() + amount);
			clubFundRepository.save(clubFund);
			responseMessage.setData(Arrays.asList(clubFund));
			responseMessage.setMessage(Constant.MSG_091);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage withdrawFromClubFund(double amount) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<ClubFund> clubFundOp = clubFundRepository.findById(1);
			ClubFund clubFund = clubFundOp.get();
			clubFund.setFundAmount(clubFund.getFundAmount() - amount);
			clubFundRepository.save(clubFund);
			responseMessage.setData(Arrays.asList(clubFund));
			responseMessage.setMessage(Constant.MSG_092);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
