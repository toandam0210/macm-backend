package com.fpt.macm.service;

import com.fpt.macm.model.response.ResponseMessage;

public interface RoleEventService {

	ResponseMessage getAllRoleEvent();

	ResponseMessage updateRoleEventName(int roleEventId, String newName);
}
