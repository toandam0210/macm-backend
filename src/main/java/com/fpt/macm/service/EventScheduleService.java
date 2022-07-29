package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.response.ResponseMessage;

public interface EventScheduleService {
	ResponseMessage createPreviewEventSchedule(String eventName, String startDate, String finishDate, String startTime, String finishTime);
	ResponseMessage createEventSchedule(int eventId, List<ScheduleDto> listPreview, Boolean isOverwritten);
	ResponseMessage createEventSession(int eventId, EventSchedule eventSchedule);
	ResponseMessage getListEventSchedule();
	ResponseMessage getListEventScheduleByEvent(int eventId);
	ResponseMessage updateEventSessionTime(int eventScheduleId, CommonSchedule updateCommonSession);
	ResponseMessage deleteEventSession(int eventScheduleId);
	ResponseMessage getEventSessionByDate(String date);
	EventSchedule getEventScheduleByDate(LocalDate date);
	ResponseMessage updatePreviewEventSchedule(int eventId, String startDate, String finishDate, String startTime, String finishTime);
	ResponseMessage updateEventSchedule(int eventId, List<ScheduleDto> listPreview, Boolean isOverwritten);
	ResponseMessage getPeriodTimeOfEvent(int eventId);
	List<EventSchedule> listEventScheduleByEvent(int eventId);
}
