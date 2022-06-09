package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.Constant;
import com.fpt.macm.model.FacilityCategory;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.repository.FacilityCategoryRepository;

@Service
public class FacilityCategoryServiceImpl implements FacilityCategoryService {
	@Autowired
	FacilityCategoryRepository facilityCategoryRepository;

	@Override
	public ResponseMessage addNewCategory(List<FacilityCategory> facilityCategories) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<FacilityCategory> newFacilityCategories = new ArrayList<FacilityCategory>();
			for (FacilityCategory facilityCategory : facilityCategories) {
				if (!Strings.isEmpty(facilityCategory.getName())) {
					newFacilityCategories.add(facilityCategory);
					facilityCategory.setStatus(true);
					facilityCategory.setCreatedBy("toandv");
					facilityCategory.setCreatedOn(LocalDateTime.now());
				}
				facilityCategoryRepository.saveAll(newFacilityCategories);
				responseMessage.setData(newFacilityCategories);
				responseMessage.setMessage(Constant.MSG_025);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
