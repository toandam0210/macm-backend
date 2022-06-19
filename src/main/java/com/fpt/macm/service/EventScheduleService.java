package com.fpt.macm.service;

import java.time.LocalDate;
import java.util.List;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.ResponseMessage;

public interface EventScheduleService {
	ResponseMessage createPreviewEventSchedule(int eventId, String startDate, String finishDate, String startTime, String finishTime);
	ResponseMessage createEventSchedule(int eventId, List<ScheduleDto> listPreview);
	ResponseMessage createEventSession(int eventId, EventSchedule eventSchedule);
	ResponseMessage getListEventSchedule();
	ResponseMessage getListEventScheduleByEvent(int eventId);
	ResponseMessage updateEventSessionTime(int eventScheduleId, CommonSchedule updateCommonSession);
	ResponseMessage deleteEventSession(int eventScheduleId);
	ResponseMessage getEventSessionByDate(String date);
	EventSchedule getEventSessionByDate(LocalDate date);
}
