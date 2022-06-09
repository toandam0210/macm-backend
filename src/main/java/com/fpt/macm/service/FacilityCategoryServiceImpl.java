package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

	@Override
	public ResponseMessage getAllCategories() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<FacilityCategory> facilityCategories = facilityCategoryRepository.findAllFacilityCategory();
			if (facilityCategories.size() != 0) {
				responseMessage.setData(facilityCategories);
				responseMessage.setMessage(Constant.MSG_026);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateCategory(FacilityCategory facilityCategory, int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<FacilityCategory> facilityCategoryOp = facilityCategoryRepository.findById(id);
			if (facilityCategoryOp.isPresent()) {
				FacilityCategory currentFacilityCategory = facilityCategoryOp.get();
				currentFacilityCategory.setName(facilityCategory.getName());
				currentFacilityCategory.setUpdatedBy("toandv");
				currentFacilityCategory.setUpdatedOn(LocalDateTime.now());
				facilityCategoryRepository.save(currentFacilityCategory);
				responseMessage.setData(Arrays.asList(currentFacilityCategory));
				responseMessage.setMessage(Constant.MSG_027);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteCategory(int id) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<FacilityCategory> facilityCategoryOp = facilityCategoryRepository.findById(id);
			if (facilityCategoryOp.isPresent()) {
				FacilityCategory currentFacilityCategory = facilityCategoryOp.get();
				currentFacilityCategory.setStatus(false);
				currentFacilityCategory.setUpdatedBy("toandv");
				currentFacilityCategory.setUpdatedOn(LocalDateTime.now());
				facilityCategoryRepository.save(currentFacilityCategory);
				responseMessage.setMessage(Constant.MSG_028);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;	}

}
