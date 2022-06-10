package com.fpt.macm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.FacilityReportDto;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.FacilityReport;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityReportRepository;

@Service
public class FacilityReportServiceImpl implements FacilityReportService {

	@Autowired
	FacilityReportRepository facilityReportRepository;
	
	@Autowired
	FacilityService facilityService;

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

}
