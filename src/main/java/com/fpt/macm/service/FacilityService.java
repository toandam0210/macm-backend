package com.fpt.macm.service;

import com.fpt.macm.model.Facility;
import com.fpt.macm.model.FacilityRequest;
import com.fpt.macm.model.ResponseMessage;

public interface FacilityService {

	ResponseMessage createNewFacility(Facility facility);
	ResponseMessage updateFacilityById(int facilityId, Facility facility);
	ResponseMessage getAllFacility(int pageNo, int pageSize, String sortBy);
	ResponseMessage getAllReport();
	ResponseMessage createRequestToBuyFacility(FacilityRequest facilityRequest);
	ResponseMessage getAllRequestToBuyFacility(int pageNo, int pageSize, String sortBy);
}
