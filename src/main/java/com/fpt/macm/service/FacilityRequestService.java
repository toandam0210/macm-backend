package com.fpt.macm.service;

import com.fpt.macm.model.FacilityRequest;
import com.fpt.macm.model.ResponseMessage;

public interface FacilityRequestService{

	ResponseMessage createRequestToBuyFacility(FacilityRequest facilityRequest);
	
}
