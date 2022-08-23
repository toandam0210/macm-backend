package com.fpt.macm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.RoleEventRepository;

@Service
public class RoleEventServiceImpl implements RoleEventService {

	@Autowired
	RoleEventRepository roleEventRepository;

	@Override
	public ResponseMessage getAllRoleEvent() {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleEvent> rolesEvent = roleEventRepository.findByIsActiveOrderByIdAsc(true);
			if (!rolesEvent.isEmpty()) {
				responseMessage.setData(rolesEvent);
				responseMessage.setMessage("Lấy tất cả vai trò mặc định trong sự kiện thành công");
			} else {
				responseMessage.setMessage("Chưa có vai trò mặc định nào trong sự kiện");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateRoleEventName(int roleEventId, String newName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<RoleEvent> rolesEvent = roleEventRepository.findByIsActiveOrderByIdAsc(true);
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
