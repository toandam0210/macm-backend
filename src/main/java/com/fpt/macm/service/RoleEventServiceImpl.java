package com.fpt.macm.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
	public ResponseMessage addNewRoleEvent(String newName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if (!newName.trim().equals("")) {
				List<RoleEvent> rolesEvent = roleEventRepository.findAll();
				for (RoleEvent roleEvent : rolesEvent) {
					if (roleEvent.getName().toLowerCase().equals(newName.toLowerCase().trim())) {
						responseMessage.setMessage("Đã có vai trò này");
						return responseMessage;
					}
				}

				RoleEvent roleEvent = new RoleEvent();
				roleEvent.setName(newName);
				roleEvent.setActive(true);
				roleEventRepository.save(roleEvent);

				responseMessage.setData(Arrays.asList(roleEvent));
				responseMessage.setMessage("Thêm vai trò mặc định cho sự kiện thành công");
			} else {
				responseMessage.setMessage("Không được để trống tên vai trò");
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
			if (!newName.trim().equals("")) {
				List<RoleEvent> rolesEvent = roleEventRepository.findAll();
				for (RoleEvent roleEvent : rolesEvent) {
					if (roleEvent.getName().toLowerCase().equals(newName.toLowerCase().trim())) {
						responseMessage.setMessage("Đã có vai trò này");
						return responseMessage;
					}
				}

				Optional<RoleEvent> roleEventOp = roleEventRepository.findById(roleEventId);
				if (roleEventOp.isPresent()) {
					RoleEvent roleEvent = roleEventOp.get();
					roleEvent.setName(newName);
					roleEventRepository.save(roleEvent);
				} else {
					responseMessage.setMessage("Không có vai trò này");
				}
			} else {
				responseMessage.setMessage("Không được để trống tên vai trò");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateStatusRoleEvent(int roleEventId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<RoleEvent> roleEventOp = roleEventRepository.findById(roleEventId);
			if (roleEventOp.isPresent()) {
				RoleEvent roleEvent = roleEventOp.get();
				roleEvent.setActive(!roleEvent.isActive());
				roleEventRepository.save(roleEvent);

				responseMessage.setData(Arrays.asList(roleEvent));
				responseMessage.setMessage("Cập nhật trạng thái của vai trò mặc định trong sự kiện thành công");
			} else {
				responseMessage.setMessage("Không có vai trò này");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}