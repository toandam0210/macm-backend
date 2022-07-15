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

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.FacilityDto;
import com.fpt.macm.model.dto.FacilityReportDto;
import com.fpt.macm.model.dto.FacilityRequestDto;
import com.fpt.macm.model.entity.ClubFund;
import com.fpt.macm.model.entity.Facility;
import com.fpt.macm.model.entity.FacilityCategory;
import com.fpt.macm.model.entity.FacilityReport;
import com.fpt.macm.model.entity.FacilityRequest;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.ClubFundRepository;
import com.fpt.macm.repository.FacilityCategoryRepository;
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

	@Autowired
	FacilityCategoryRepository facilityCategoryRepository;

	@Autowired
	ClubFundRepository clubFundRepository;

	@Override
	public ResponseMessage createNewFacility(FacilityDto facilityDto) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Facility facility = convertFacilityDto(facilityDto);
			if (!isExistFacilityToCreate(facility)) {
				facility.setQuantityBroken(0);
				facility.setCreatedBy("toandv");
				facility.setCreatedOn(LocalDateTime.now());

				facilityRepository.save(facility);

				Optional<Facility> facilityOp = facilityRepository.findFacilityByFacilityNameAndFacilityCategoryId(
						facilityDto.getFacilityName(), facilityDto.getFacilityCategoryId());

				if (facility.getQuantityUsable() > 0) {

					FacilityReport facilityReport = new FacilityReport();
					facilityReport.setFacility(facilityOp.get());
					facilityReport.setDescription(Constant.FACILITY_STATUS_001 + " " + facility.getQuantityUsable());
					facilityReport.setAdd(true);
					facilityReport.setCreatedBy("toandv");
					facilityReport.setCreatedOn(LocalDateTime.now());
					facilityReportRepository.save(facilityReport);
				}

				Optional<FacilityCategory> facilityCategoryOp = facilityCategoryRepository
						.findById(facilityDto.getFacilityCategoryId());
				FacilityCategory facilityCategory = facilityCategoryOp.get();
				facilityDto.setFacilityCategoryName(facilityCategory.getName());
				facilityDto.setFacilityId(facilityOp.get().getId());

				responseMessage.setData(Arrays.asList(facilityDto));
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

	private Facility convertFacilityDto(FacilityDto facilityDto) {
		Facility facility = new Facility();
		facility.setId(facilityDto.getFacilityId());
		facility.setName(facilityDto.getFacilityName());
		facility.setQuantityUsable(facilityDto.getQuantityUsable());
		facility.setQuantityBroken(facilityDto.getQuantityBroken());
		FacilityCategory facilityCategory = new FacilityCategory();
		facilityCategory.setId(facilityDto.getFacilityCategoryId());
		facilityCategory.setName(facilityDto.getFacilityCategoryName());
		facility.setFacilityCategory(facilityCategory);
		return facility;
	}

	private FacilityDto convertFacility(Facility facility) {
		FacilityDto facilityDto = new FacilityDto();
		facilityDto.setFacilityId(facility.getId());
		facilityDto.setFacilityName(facility.getName());
		facilityDto.setFacilityCategoryId(facility.getFacilityCategory().getId());
		facilityDto.setFacilityCategoryName(facility.getFacilityCategory().getName());
		facilityDto.setQuantityUsable(facility.getQuantityUsable());
		facilityDto.setQuantityBroken(facility.getQuantityBroken());
		return facilityDto;
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
	public ResponseMessage updateFacilityById(int facilityId, FacilityDto facilityDto) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Facility facility = convertFacilityDto(facilityDto);

			Optional<Facility> facilityOp = facilityRepository.findById(facilityId);
			Facility oldFacility = facilityOp.get();

			if (!isExistFacilityToUpdate(oldFacility, facility)) {
				oldFacility.setName(facility.getName());
				oldFacility.setFacilityCategory(facility.getFacilityCategory());

				if (oldFacility.getQuantityUsable() != facility.getQuantityUsable()
						|| oldFacility.getQuantityBroken() != facility.getQuantityBroken()) {
					if (isValidQuantity(oldFacility.getQuantityUsable(), oldFacility.getQuantityBroken(),
							facility.getQuantityUsable(), facility.getQuantityBroken())) {
						createFacilityReport(oldFacility, facility);
						oldFacility.setQuantityUsable(facility.getQuantityUsable());
						oldFacility.setQuantityBroken(facility.getQuantityBroken());
						oldFacility.setUpdatedBy("toandv");
						oldFacility.setUpdatedOn(LocalDateTime.now());
						facilityRepository.save(oldFacility);

						FacilityDto updatedFacilityDto = convertFacility(oldFacility);
						Optional<FacilityCategory> facilityCategoryOp = facilityCategoryRepository
								.findById(updatedFacilityDto.getFacilityCategoryId());
						FacilityCategory facilityCategory = facilityCategoryOp.get();
						updatedFacilityDto.setFacilityCategoryName(facilityCategory.getName());

						responseMessage.setData(Arrays.asList(updatedFacilityDto));
						responseMessage.setMessage(Constant.MSG_032);
					} else {
						responseMessage.setMessage(Constant.MSG_046);
					}
				} else {
					oldFacility.setUpdatedBy("toandv");
					oldFacility.setUpdatedOn(LocalDateTime.now());
					facilityRepository.save(oldFacility);

					FacilityDto updatedFacilityDto = convertFacility(oldFacility);
					Optional<FacilityCategory> facilityCategoryOp = facilityCategoryRepository
							.findById(updatedFacilityDto.getFacilityCategoryId());
					FacilityCategory facilityCategory = facilityCategoryOp.get();
					updatedFacilityDto.setFacilityCategoryName(facilityCategory.getName());

					responseMessage.setData(Arrays.asList(updatedFacilityDto));
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

	private boolean isValidQuantity(int oldQuantityUsable, int oldQuantityBroken, int newQuantityUsable,
			int newQuantityBroken) {
		if ((oldQuantityUsable - newQuantityUsable) > 0
				&& (oldQuantityUsable - newQuantityUsable) < (newQuantityBroken - oldQuantityBroken)) {
			return false;
		}

		return true;
	}

	private void createFacilityReport(Facility oldFacility, Facility newFacility) {
		FacilityReport facilityReport = new FacilityReport();

		int newQuantityUsable = newFacility.getQuantityUsable();
		int oldQuantityUsable = oldFacility.getQuantityUsable();
		int newQuantityBroken = newFacility.getQuantityBroken();
		int oldQuantityBroken = oldFacility.getQuantityBroken();

		String description = "";

		int quantityUsableDifference = newQuantityUsable - oldQuantityUsable;
		int quantityBrokenDifference = newQuantityBroken - oldQuantityBroken;

		if (quantityUsableDifference > 0) {
			facilityReport.setAdd(true);
			description += Constant.FACILITY_STATUS_001 + " " + quantityUsableDifference;
		} else if (quantityUsableDifference == 0) {
			if (quantityBrokenDifference < 0) {
				facilityReport.setAdd(false);
				description += Constant.FACILITY_STATUS_002 + " " + (-quantityBrokenDifference);
			}
		} else {
			if (quantityBrokenDifference == 0) {
				facilityReport.setAdd(false);
				description += Constant.FACILITY_STATUS_002 + " " + (-quantityUsableDifference);
			} else if (quantityBrokenDifference == -quantityUsableDifference) {
				facilityReport.setAdd(false);
				description += Constant.FACILITY_STATUS_003 + " " + (quantityBrokenDifference);
			} else if (quantityBrokenDifference > 0 && quantityBrokenDifference < -quantityUsableDifference) {
				facilityReport.setAdd(false);
				description += Constant.FACILITY_STATUS_003 + " " + (quantityBrokenDifference) + " và "
						+ Constant.FACILITY_STATUS_002 + " " + (-quantityUsableDifference - quantityBrokenDifference);
			} else if (quantityBrokenDifference < 0) {
				facilityReport.setAdd(false);
				description += Constant.FACILITY_STATUS_002 + " "
						+ (-quantityUsableDifference + -quantityBrokenDifference);
			}
		}

		if (description != "") {

			facilityReport.setFacility(oldFacility);
			facilityReport.setDescription(description);
			facilityReport.setCreatedBy("toandv");
			facilityReport.setCreatedOn(LocalDateTime.now());

			facilityReportRepository.save(facilityReport);
		}
	}

	@Override
	public ResponseMessage getAllFacilityByFacilityCategoryId(int facilityCategoryId, int pageNo, int pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
			Page<Facility> pageResponse;
			if (facilityCategoryId == 0) {
				pageResponse = facilityRepository.findAll(paging);
			} else {
				pageResponse = facilityRepository.findByFacilityCategoryId(facilityCategoryId, paging);
			}

			List<Facility> facilities = new ArrayList<Facility>();
			List<FacilityDto> facilitiesDto = new ArrayList<FacilityDto>();
			if (pageResponse != null && pageResponse.hasContent()) {
				facilities = pageResponse.getContent();
			}
			for (Facility facility : facilities) {
				FacilityDto facilityDto = new FacilityDto();
				facilityDto.setFacilityId(facility.getId());
				facilityDto.setFacilityName(facility.getName());
				facilityDto.setFacilityCategoryId(facility.getFacilityCategory().getId());
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
	public ResponseMessage getAllReport(int filterIndex) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<FacilityReportDto> facilityReportsDto = new ArrayList<FacilityReportDto>();
			List<FacilityReport> facilityReports = (List<FacilityReport>) facilityReportRepository
					.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));

			switch (filterIndex) {
			case 0:
				for (FacilityReport facilityReport : facilityReports) {
					FacilityReportDto facilityReportDto = convertFacilityReport(facilityReport);
					facilityReportsDto.add(facilityReportDto);
				}
				break;
			case 1:
				for (FacilityReport facilityReport : facilityReports) {
					if (facilityReport.isAdd()) {
						FacilityReportDto facilityReportDto = convertFacilityReport(facilityReport);
						facilityReportsDto.add(facilityReportDto);
					}
				}
				break;
			case 2:
				for (FacilityReport facilityReport : facilityReports) {
					if (!facilityReport.isAdd()) {
						FacilityReportDto facilityReportDto = convertFacilityReport(facilityReport);
						facilityReportsDto.add(facilityReportDto);
					}
				}
				break;
			default:
				for (FacilityReport facilityReport : facilityReports) {
					FacilityReportDto facilityReportDto = convertFacilityReport(facilityReport);
					facilityReportsDto.add(facilityReportDto);
				}
				break;
			}

			responseMessage.setData(facilityReportsDto);
			responseMessage.setMessage(Constant.MSG_034);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private FacilityReportDto convertFacilityReport(FacilityReport facilityReport) {
		FacilityReportDto facilityReportDto = new FacilityReportDto();
		String description = facilityReport.getDescription() + " " + facilityReport.getFacility().getName();
		facilityReportDto.setDescription(description);
		facilityReportDto.setAdd(facilityReport.isAdd());
		facilityReportDto.setCreatedOn(facilityReport.getCreatedOn());
		return facilityReportDto;
	}

	@Override
	public ResponseMessage createRequestToBuyFacility(FacilityRequestDto facilityRequestDto) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			FacilityRequest facilityRequest = new FacilityRequest();
			Facility facility = new Facility();
			facility.setId(facilityRequestDto.getFacilityId());
			facilityRequest.setFacility(facility);
			facilityRequest.setQuantity(facilityRequestDto.getQuantity());
			facilityRequest.setUnitPrice(facilityRequestDto.getUnitPrice());
			facilityRequest.setStatus(Constant.REQUEST_STATUS_PENDING);
			facilityRequest.setCreatedBy("toandv");
			facilityRequest.setCreatedOn(LocalDateTime.now());
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
			List<FacilityRequest> facilityRequests = (List<FacilityRequest>) facilityRequestRepository.findAll(Sort.by(Sort.Direction.DESC, "createdOn"));
			List<FacilityRequestDto> facilityRequestsDto = new ArrayList<FacilityRequestDto>();
			for (FacilityRequest facilityRequest : facilityRequests) {
				FacilityRequestDto facilityRequestDto = convertFacilityRequestToFacilityRequestDto(facilityRequest);
				facilityRequestsDto.add(facilityRequestDto);
			}
			responseMessage.setData(facilityRequestsDto);
			responseMessage.setMessage(Constant.MSG_073);
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	private FacilityRequestDto convertFacilityRequestToFacilityRequestDto(FacilityRequest facilityRequest) {
		FacilityRequestDto facilityRequestDto = new FacilityRequestDto();
		facilityRequestDto.setId(facilityRequest.getId());
		facilityRequestDto.setFacilityId(facilityRequest.getFacility().getId());
		facilityRequestDto.setFacilityName(facilityRequest.getFacility().getName());
		facilityRequestDto.setFacilityCategoryId(facilityRequest.getFacility().getFacilityCategory().getId());
		facilityRequestDto.setFacilityCategory(facilityRequest.getFacility().getFacilityCategory().getName());
		facilityRequestDto.setQuantity(facilityRequest.getQuantity());
		facilityRequestDto.setUnitPrice(facilityRequest.getUnitPrice());
		facilityRequestDto.setStatus(facilityRequest.getStatus());
		facilityRequestDto.setCreatedBy(facilityRequest.getCreatedBy());
		facilityRequestDto.setCreatedOn(facilityRequest.getCreatedOn());
		return facilityRequestDto;
	}

	@Override
	public ResponseMessage approveRequestToBuyFacility(int facilityRequestId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<ClubFund> clubFunds = clubFundRepository.findAll();
			ClubFund clubFund = clubFunds.get(0);
			double fundAmount = clubFund.getFundAmount();

			Optional<FacilityRequest> facilityRequestOp = facilityRequestRepository.findById(facilityRequestId);
			FacilityRequest facilityRequest = facilityRequestOp.get();
			double totalAmount = facilityRequest.getQuantity() * facilityRequest.getUnitPrice();

			if (facilityRequest.getStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
				if (fundAmount >= totalAmount) {
					clubFund.setFundAmount(fundAmount - totalAmount);
					clubFundRepository.save(clubFund);

					facilityRequest.setStatus(Constant.REQUEST_STATUS_APPROVED);
					facilityRequestRepository.save(facilityRequest);

					responseMessage.setData(Arrays.asList(facilityRequest));
					responseMessage.setMessage(Constant.MSG_076);
				} else {
					responseMessage.setMessage(Constant.MSG_077);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage declineRequestToBuyFacility(int facilityRequestId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<FacilityRequest> facilityRequestOp = facilityRequestRepository.findById(facilityRequestId);
			FacilityRequest facilityRequest = facilityRequestOp.get();
			if (facilityRequest.getStatus().equals(Constant.REQUEST_STATUS_PENDING)) {
				facilityRequest.setStatus(Constant.REQUEST_STATUS_DECLINED);
				facilityRequestRepository.save(facilityRequest);
				responseMessage.setData(Arrays.asList(facilityRequest));
				responseMessage.setMessage(Constant.MSG_078);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
