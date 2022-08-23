package com.fpt.macm.service;

import java.util.List;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;

public interface AreaService {
	ResponseMessage getAllArea();
	ResponseMessage addNewArea(Area area);
	public ResponseMessage updateListArea(List<Area> newListArea);
}
