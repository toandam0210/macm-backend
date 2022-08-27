package com.fpt.macm.service;

import com.fpt.macm.model.entity.RoleEvent;
import com.fpt.macm.model.response.ResponseMessage;

public interface RoleEventService {

	ResponseMessage getAllRoleEvent();
	
	ResponseMessage addNewRoleEvent(RoleEvent roleEvent);

	ResponseMessage updateRoleEventName(int roleEventId, RoleEvent roleEvent);
	
	ResponseMessage deleteRoleEvent(int roleEventId);
}
