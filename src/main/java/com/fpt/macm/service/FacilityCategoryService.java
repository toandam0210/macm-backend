package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.FacilityCategory;
import com.fpt.macm.model.ResponseMessage;

public interface FacilityCategoryService {
	ResponseMessage addNewCategory(List<FacilityCategory> facilityCategories);
}
