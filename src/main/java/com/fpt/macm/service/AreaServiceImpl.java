package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.Area;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AreaRepository;

@Service
public class AreaServiceImpl implements AreaService{

	@Autowired
	AreaRepository areaRepository;
	
	@Override
	public ResponseMessage getAllArea() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Area> getAllArea = areaRepository.findAll();
			responseMessage.setData(getAllArea);
			responseMessage.setMessage("Danh sách sân đấu");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage addNewArea(Area area) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			area.setCreatedBy("LinhLHN");
			area.setCreatedOn(LocalDateTime.now());
			area.setUpdatedBy("LinhLHN");
			area.setUpdatedOn(LocalDateTime.now());
			areaRepository.save(area);
			responseMessage.setData(Arrays.asList(area));
			responseMessage.setMessage("Thêm sân đấu " + area.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
