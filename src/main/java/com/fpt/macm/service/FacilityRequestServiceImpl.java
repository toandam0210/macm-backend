package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.FacilityRequest;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityRequestRepository;

@Service
public class FacilityRequestServiceImpl implements FacilityRequestService{

	@Autowired
	FacilityRequestRepository facilityRequestRepository;
	
	@Override
	public ResponseMessage createRequestToBuyFacility(FacilityRequest facilityRequest) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			facilityRequest.setCreatedBy("toandv");
			facilityRequest.setCreatedOn(LocalDateTime.now());
			facilityRequest.setStatus(true);
			facilityRequestRepository.save(facilityRequest);
			responseMessage.setData(Arrays.asList(facilityRequest));
			responseMessage.setMessage(Constant.MSG_031);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
