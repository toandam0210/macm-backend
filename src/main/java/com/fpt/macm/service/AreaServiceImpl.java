package com.fpt.macm.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
			if(getAllArea.size() > 0) {
			responseMessage.setData(getAllArea);
			responseMessage.setMessage("Danh sách sân đấu");
			}
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
		area.setCreatedBy("LinhLHN");
		area.setCreatedOn(LocalDateTime.now());
		area.setUpdatedBy("LinhLHN");
		area.setUpdatedOn(LocalDateTime.now());
		areaRepository.save(area);
		responseMessage.setData(Arrays.asList(area));
		responseMessage.setMessage("Thêm sân đấu " + area.getName());
		return responseMessage;
	}

	@Override
	public ResponseMessage updateListArea(List<Area> newListArea) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<Area> currentListArea = areaRepository.findAll();
			List<Area> listResult = new ArrayList<Area>();
			for (Area currentArea : currentListArea) {
				boolean isExisted = false;
				for (Area newArea : newListArea) {
					if(currentArea.getName().equals(newArea.getName())) {
						isExisted = true;
						currentArea.setUpdatedBy("LinhLHN");
						currentArea.setUpdatedOn(LocalDateTime.now());
						areaRepository.save(currentArea);
						listResult.add(currentArea);
					}
				}
				if(!isExisted) {
					areaRepository.delete(currentArea);
				}
			}
			for (Area newArea : newListArea) {
				boolean isExisted = false;
				for (Area currentArea : currentListArea) {
					if(currentArea.getName().equals(newArea.getName())) {
						isExisted = true;
					}
				}
				if(!isExisted) {
					newArea.setCreatedBy("LinhLHN");
					newArea.setCreatedOn(LocalDateTime.now());
					newArea.setUpdatedBy("LinhLHN");
					newArea.setUpdatedOn(LocalDateTime.now());
					areaRepository.save(newArea);
					listResult.add(newArea);
				}
			}
			responseMessage.setData(listResult);
			responseMessage.setMessage("Danh sách sân mới");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
