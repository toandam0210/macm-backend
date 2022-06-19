package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.FacilityDto;
import com.fpt.macm.dto.FacilityReportDto;
import com.fpt.macm.dto.FacilityRequestDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.Facility;
import com.fpt.macm.model.FacilityReport;
import com.fpt.macm.model.FacilityRequest;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityReportRepository;
import com.fpt.macm.repository.FacilityRepository;
import com.fpt.macm.repository.FacilityRequestRepository;

@Service
public class FacilityServiceImpl implements FacilityService {

	@Autowired
	FacilityRepository facilityRepository;

	@Autowired
	FacilityReportRepository facilityReportRepository;
	
	@Autowired
	FacilityRequestRepository facilityRequestRepository;

	@Override
	public ResponseMessage createNewFacility(Facility facility) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!isExistFacilityToCreate(facility)) {
				facility.setQuantityUsable(0);
				facility.setQuantityBroken(0);
				facility.setCreatedBy("toandv");
				facility.setCreatedOn(LocalDateTime.now());
				facilityRepository.save(facility);
				responseMessage.setData(Arrays.asList(facility));
				responseMessage.setMessage(Constant.MSG_029);
			} else {
				responseMessage.setMessage(Constant.MSG_030);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
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
	public ResponseMessage updateFacilityById(int facilityId, Facility facility) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Facility> facilityOp = facilityRepository.findById(facilityId);
			Facility oldFacility = facilityOp.get();

			if (!isExistFacilityToUpdate(oldFacility, facility)) {
				oldFacility.setName(facility.getName());
				oldFacility.setFacilityCategory(facility.getFacilityCategory());

				if (oldFacility.getQuantityUsable() != facility.getQuantityUsable()
						|| oldFacility.getQuantityBroken() != facility.getQuantityBroken()) {
					if (isValidQuantity(oldFacility.getQuantityUsable(), oldFacility.getQuantityBroken(), facility.getQuantityUsable(), facility.getQuantityBroken())) {
						createFacilityReport(oldFacility, facility);
						oldFacility.setQuantityUsable(facility.getQuantityUsable());
						oldFacility.setQuantityBroken(facility.getQuantityBroken());
						oldFacility.setUpdatedBy("toandv");
						oldFacility.setUpdatedOn(LocalDateTime.now());
						facilityRepository.save(oldFacility);
						responseMessage.setData(Arrays.asList(oldFacility));
						responseMessage.setMessage(Constant.MSG_032);
					}
					else {
						responseMessage.setMessage(Constant.MSG_046);
					}
				}
				else {
					oldFacility.setUpdatedBy("toandv");
					oldFacility.setUpdatedOn(LocalDateTime.now());
					facilityRepository.save(oldFacility);
					responseMessage.setData(Arrays.asList(oldFacility));
					responseMessage.setMessage(Constant.MSG_032);
				}
				
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
	
	private boolean isValidQuantity(int oldQuantityUsable, int oldQuantityBroken, int newQuantityUsable, int newQuantityBroken) {
		if ((oldQuantityUsable - newQuantityUsable) > 0 && (oldQuantityUsable - newQuantityUsable) < (newQuantityBroken - oldQuantityBroken)) {
			return false;
		}
		
		return true;
	}

	private void createFacilityReport(Facility oldFacility, Facility newFacility) {
		int newQuantityUsable = newFacility.getQuantityUsable();
		int oldQuantityUsable = oldFacility.getQuantityUsable();
		int newQuantityBroken = newFacility.getQuantityBroken();
		int oldQuantityBroken = oldFacility.getQuantityBroken();

		String description = "";

		int quantityUsableDifference = newQuantityUsable - oldQuantityUsable;
		int quantityBrokenDifference = newQuantityBroken - oldQuantityBroken;

		if (quantityUsableDifference > 0) {
			description += Constant.FACILITY_STATUS_001 + " " + quantityUsableDifference;
		} else if (quantityUsableDifference == 0) {
			if (quantityBrokenDifference < 0) {
				description += Constant.FACILITY_STATUS_002 + " " + (-quantityBrokenDifference);
			}
		} else {
			if (quantityBrokenDifference == 0) {
				description += Constant.FACILITY_STATUS_002 + " " + (-quantityUsableDifference);
			} else if (quantityBrokenDifference == -quantityUsableDifference) {
				description += Constant.FACILITY_STATUS_003 + " " + (quantityBrokenDifference);
			} else if (quantityBrokenDifference > 0 && quantityBrokenDifference < -quantityUsableDifference) {
				description += Constant.FACILITY_STATUS_003 + " " + (quantityBrokenDifference) + " và "
						+ Constant.FACILITY_STATUS_002 + " " + (-quantityUsableDifference - quantityBrokenDifference);
			} else if (quantityBrokenDifference < 0) {
				description += Constant.FACILITY_STATUS_002 + " "
						+ (-quantityUsableDifference + -quantityBrokenDifference);
			}
		}

		if (description != "") {
			FacilityReport facilityReport = new FacilityReport();
			facilityReport.setFacility(oldFacility);
			facilityReport.setDescription(description);
			facilityReport.setCreatedBy("toandv");
			facilityReport.setCreatedOn(LocalDateTime.now());

			facilityReportRepository.save(facilityReport);
		}
	}

	@Override
	public ResponseMessage getAllFacility(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
			Page<Facility> pageResponse = facilityRepository.findAll(paging);
			List<Facility> facilities = new ArrayList<Facility>();
			List<FacilityDto> facilitiesDto = new ArrayList<FacilityDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				facilities = pageResponse.getContent();
			}
			for (Facility facility : facilities) {
				FacilityDto facilityDto = new FacilityDto();
				facilityDto.setFacilityId(facility.getId());
				facilityDto.setFacilityName(facility.getName());
				facilityDto.setFacilityCategoryName(facility.getFacilityCategory().getName());
				facilityDto.setQuantityUsable(facility.getQuantityUsable());
				facilityDto.setQuantityBroken(facility.getQuantityBroken());

				facilitiesDto.add(facilityDto);
			}
			responseMessage.setData(facilitiesDto);
			responseMessage.setPageNo(pageNo);
			responseMessage.setPageSize(pageSize);
			responseMessage.setMessage(Constant.MSG_033);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllReport() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<FacilityReportDto> facilityReportsDto = new ArrayList<FacilityReportDto>();

			List<FacilityReport> facilityReports = (List<FacilityReport>) facilityReportRepository.findAll();
			for (FacilityReport facilityReport : facilityReports) {
				FacilityReportDto facilityReportDto = new FacilityReportDto();
				String description = facilityReport.getDescription() + " " + facilityReport.getFacility().getName();
				facilityReportDto.setDescription(description);
				facilityReportDto.setCreatedOn(facilityReport.getCreatedOn());

				facilityReportsDto.add(facilityReportDto);
			}
			responseMessage.setData(facilityReportsDto);
			responseMessage.setMessage(Constant.MSG_034);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

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

	@Override
	public ResponseMessage getAllRequestToBuyFacility(int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<FacilityRequest> facilityRequests = (List<FacilityRequest>) facilityRequestRepository.findAll();
			List<FacilityRequestDto> facilityRequestsDto = new ArrayList<FacilityRequestDto>();
			for (FacilityRequest facilityRequest : facilityRequests) {
				FacilityRequestDto facilityRequestDto = new FacilityRequestDto();
				facilityRequestDto.setId(facilityRequest.getId());
				facilityRequestDto.setFacilityName(facilityRequest.getFacility().getName());
				facilityRequestDto.setFacilityCategory(facilityRequest.getFacility().getFacilityCategory().getName());
				facilityRequestDto.setQuantity(facilityRequest.getQuantity());
				facilityRequestDto.setUnitPrice(facilityRequest.getUnitPrice());
				facilityRequestDto.setStatus(facilityRequest.getStatus());
				facilityRequestsDto.add(facilityRequestDto);
			}
			responseMessage.setData(facilityRequestsDto);
			responseMessage.setMessage(Constant.MSG_063);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
