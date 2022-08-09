package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpt.macm.model.entity.Event;

public class EventCreateDto {

	private Event event;
	private List<RoleEventDto> rolesEventDto = new ArrayList<RoleEventDto>();
	private List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<RoleEventDto> getRolesEventDto() {
		return rolesEventDto;
	}

	public void setRolesEventDto(List<RoleEventDto> rolesEventDto) {
		this.rolesEventDto = rolesEventDto;
	}

	public List<ScheduleDto> getListPreview() {
		return listPreview;
	}

	public void setListPreview(List<ScheduleDto> listPreview) {
		this.listPreview = listPreview;
	}

}
