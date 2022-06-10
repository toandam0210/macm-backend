package com.fpt.macm.service;

import com.fpt.macm.model.Facility;
import com.fpt.macm.model.ResponseMessage;

public interface FacilityService {

	ResponseMessage createNewFacility(Facility facility);
	ResponseMessage updateFacilityById(int facilityId, Facility facility, int quantityUsable, int quantityBroken);
	
}
