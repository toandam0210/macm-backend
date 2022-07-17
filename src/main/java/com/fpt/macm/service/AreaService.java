package com.fpt.macm.service;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;

public interface AreaService {
	ResponseMessage getAllArea();
	ResponseMessage addNewArea(Area area);
	
}
