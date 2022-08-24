package com.fpt.macm.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.fpt.macm.model.entity.Event;

public class EventCreateDto {

	private Event event;
	private List<EventRoleDto> eventRolesDto = new ArrayList<EventRoleDto>();
	private List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public List<EventRoleDto> getEventRolesDto() {
		return eventRolesDto;
	}

	public void setEventRolesDto(List<EventRoleDto> eventRolesDto) {
		this.eventRolesDto = eventRolesDto;
	}

	public List<ScheduleDto> getListPreview() {
		return listPreview;
	}

	public void setListPreview(List<ScheduleDto> listPreview) {
		this.listPreview = listPreview;
	}

}
