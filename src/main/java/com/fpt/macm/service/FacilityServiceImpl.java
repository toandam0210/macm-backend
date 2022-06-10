package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Facility;
import com.fpt.macm.model.FacilityReport;
import com.fpt.macm.model.FacilityStatus;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityReportRepository;
import com.fpt.macm.repository.FacilityRepository;
import com.fpt.macm.repository.FacilityStatusRepository;

@Service
public class FacilityServiceImpl implements FacilityService {

	@Autowired
	FacilityRepository facilityRepository;

	@Autowired
	FacilityStatusRepository facilityStatusRepository;

	@Autowired
	FacilityReportRepository facilityReportRepository;

	@Override
	public ResponseMessage createNewFacility(Facility facility) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!isExistFacilityToCreate(facility)) {
				facility.setCreatedBy("toandv");
				facility.setCreatedOn(LocalDateTime.now());
				facilityRepository.save(facility);
				responseMessage.setData(Arrays.asList(facility));
				responseMessage.setMessage(Constant.MSG_029);

				createFacilityStatus(facility, true);
				createFacilityStatus(facility, false);
			} else {
				responseMessage.setMessage(Constant.MSG_030);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private void createFacilityStatus(Facility facility, boolean status) {
		Optional<Facility> facilityOp = facilityRepository.findFacilityByFacilityNameAndFacilityCategoryId(
				facility.getName(), facility.getFacilityCategory().getId());
		Facility newFacility = facilityOp.get();

		FacilityStatus facilityStatus = new FacilityStatus();
		facilityStatus.setFacility(newFacility);
		facilityStatus.setQuantity(0);
		facilityStatus.setStatus(status);
		facilityStatus.setCreatedBy("toandv");
		facilityStatus.setCreatedOn(LocalDateTime.now());
		facilityStatusRepository.save(facilityStatus);
	}

	private boolean isExistFacilityToCreate(Facility newFacility) {
		List<Facility> oldFacilities = (List<Facility>) facilityRepository.findAll();
		for (Facility oldFacility : oldFacilities) {
			if (oldFacility.getName().equals(newFacility.getName())
					&& oldFacility.getFacilityCategory().getId() == newFacility.getFacilityCategory().getId()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public ResponseMessage updateFacilityById(int facilityId, Facility facility, int quantityUsable,
			int quantityBroken) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Facility> facilityOp = facilityRepository.findById(facilityId);
			Facility oldFacility = facilityOp.get();
			
			if (!isExistFacilityToUpdate(oldFacility, facility)) {
				oldFacility.setName(facility.getName());
				oldFacility.setFacilityCategory(facility.getFacilityCategory());
				oldFacility.setUpdatedBy("toandv");
				oldFacility.setUpdatedOn(LocalDateTime.now());
				facilityRepository.save(oldFacility);
				responseMessage.setData(Arrays.asList(oldFacility));
				responseMessage.setMessage(Constant.MSG_032);

				updateFacilityStatus(facilityId, quantityUsable, quantityBroken);
			} else {
				responseMessage.setMessage(Constant.MSG_030);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private boolean isExistFacilityToUpdate(Facility oldFacility, Facility newFacility) {
		List<Facility> oldFacilities = (List<Facility>) facilityRepository.findAll();

		if (!oldFacility.getName().equals(newFacility.getName())
				|| oldFacility.getFacilityCategory().getId() != newFacility.getFacilityCategory().getId()) {
			for (Facility facility : oldFacilities) {
				if (facility.getName().equals(newFacility.getName())
						&& facility.getFacilityCategory().getId() == newFacility.getFacilityCategory().getId()) {
					return true;
				}
			}
		}

		return false;
	}

	private void updateFacilityStatus(int facilityId, int quantityUsable, int quantityBroken) {
		List<FacilityStatus> listFacilityStatus = facilityStatusRepository.findByFacilityId(facilityId);
		for (FacilityStatus facilityStatus : listFacilityStatus) {
			if (facilityStatus.getStatus()) {
				facilityStatus.setQuantity(quantityUsable);
				facilityStatus.setUpdatedBy("toandv");
				facilityStatus.setUpdatedOn(LocalDateTime.now());
				facilityStatusRepository.save(facilityStatus);

				createFacilityReport(facilityStatus);
			} else {
				facilityStatus.setQuantity(quantityBroken);
				facilityStatus.setUpdatedBy("toandv");
				facilityStatus.setUpdatedOn(LocalDateTime.now());
				facilityStatusRepository.save(facilityStatus);

				createFacilityReport(facilityStatus);
			}
		}
	}

	private void createFacilityReport(FacilityStatus facilityStatus) {
		FacilityReport facilityReport = new FacilityReport();
		facilityReport.setFacilityStatus(facilityStatus);
		facilityReport.setQuantity(facilityStatus.getQuantity());
		facilityReport.setCreatedBy("toandv");
		facilityReport.setCreatedOn(LocalDateTime.now());
		facilityReportRepository.save(facilityReport);
	}

}
