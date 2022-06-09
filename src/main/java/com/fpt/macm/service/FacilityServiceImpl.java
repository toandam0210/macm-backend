package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Facility;
import com.fpt.macm.model.FacilityStatus;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityRepository;
import com.fpt.macm.repository.FacilityStatusRepository;

@Service
public class FacilityServiceImpl implements FacilityService{

	@Autowired
	FacilityRepository facilityRepository;
	
	@Autowired
	FacilityStatusRepository facilityStatusRepository;

	@Override
	public ResponseMessage createNewFacility(Facility facility) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Facility> oldFacilities = (List<Facility>) facilityRepository.findAll();
			if (!isExistFacility(oldFacilities, facility)) {
				facility.setCreatedBy("toandv");
				facility.setCreatedOn(LocalDateTime.now());
				facilityRepository.save(facility);
				responseMessage.setData(Arrays.asList(facility));
				responseMessage.setMessage(Constant.MSG_029);
				
				Optional<Facility> facilityOp = facilityRepository.findFacilityByFacilityNameAndFacilityCategoryId(facility.getName(), facility.getFacilityCategory().getId());
				Facility newFacility = facilityOp.get();
				
				FacilityStatus facilityStatusTrue = new FacilityStatus();
				facilityStatusTrue.setFacility(newFacility);
				facilityStatusTrue.setQuantity(0);
				facilityStatusTrue.setStatus(true);
				facilityStatusTrue.setCreatedBy("toandv");
				facilityStatusTrue.setCreatedOn(LocalDateTime.now());
				facilityStatusRepository.save(facilityStatusTrue);
				
				FacilityStatus facilityStatusFalse = new FacilityStatus();
				facilityStatusFalse.setFacility(newFacility);
				facilityStatusFalse.setQuantity(0);
				facilityStatusFalse.setStatus(false);
				facilityStatusFalse.setCreatedBy("toandv");
				facilityStatusFalse.setCreatedOn(LocalDateTime.now());
				facilityStatusRepository.save(facilityStatusFalse);
			}
			else {
				responseMessage.setMessage(Constant.MSG_030);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	private boolean isExistFacility(List<Facility> oldFacilities, Facility newFacility) {
		for (Facility oldFacility : oldFacilities) {
			if (oldFacility.getName().equals(newFacility.getName()) && oldFacility.getFacilityCategory().equals(newFacility.getFacilityCategory())) {
				return true;
			}
		}
		return false;
	}
	
}
