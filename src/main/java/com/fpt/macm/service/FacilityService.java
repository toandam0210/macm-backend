package com.fpt.macm.service;

import com.fpt.macm.model.dto.FacilityDto;
import com.fpt.macm.model.dto.FacilityRequestDto;
import com.fpt.macm.model.response.ResponseMessage;

public interface FacilityService {

	ResponseMessage createNewFacility(FacilityDto facilityDto);

	ResponseMessage updateFacilityById(int facilityId, FacilityDto facilityDto);

	ResponseMessage getAllFacilityByFacilityCategoryId(int facilityCategoryId, int pageNo, int pageSize, String sortBy);

	ResponseMessage getAllReport(int filterIndex);

	ResponseMessage createRequestToBuyFacility(FacilityRequestDto facilityRequestDto);

	ResponseMessage getAllRequestToBuyFacility(int pageNo, int pageSize, String sortBy);

	ResponseMessage approveRequestToBuyFacility(int facilityRequestId);

	ResponseMessage declineRequestToBuyFacility(int facilityRequestId);
}
