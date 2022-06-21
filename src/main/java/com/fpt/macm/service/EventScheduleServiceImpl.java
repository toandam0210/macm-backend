package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.dto.ScheduleDto;
import com.fpt.macm.model.CommonSchedule;
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.EventSchedule;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.utils.Utils;

@Service
public class EventScheduleServiceImpl implements EventScheduleService{

	@Autowired
	EventScheduleRepository eventScheduleRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CommonScheduleRepository commonScheduleRepository;
	
	@Autowired
	CommonScheduleService commonScheduleService;
	
	@Autowired
	SemesterService semesterService;
	
	@Override
	public ResponseMessage createPreviewEventSchedule(String eventName, String startDate, String finishDate, String startTime, String finishTime) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate startLocalDate = Utils.ConvertStringToLocalDate(startDate);
			LocalDate finishLocalDate = Utils.ConvertStringToLocalDate(finishDate);
			LocalTime startLocalTime = LocalTime.parse(startTime);
			LocalTime finishLocalTime = LocalTime.parse(finishTime);
			if(startLocalDate.compareTo(finishLocalDate) > 0) {
				responseMessage.setMessage(Constant.MSG_081);
			}
			else if(startLocalTime.compareTo(finishLocalTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			}
			else if(finishLocalDate.compareTo(LocalDate.now()) < 0) {
				responseMessage.setMessage(Constant.MSG_065);
			}
			else {
				Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
				if(finishLocalDate.compareTo(currentSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				} 
				else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					while(startLocalDate.compareTo(finishLocalDate) <= 0) {
						if(startLocalDate.compareTo(LocalDate.now()) > 0) {
							ScheduleDto eventSessionDto = new ScheduleDto();
							eventSessionDto.setDate(startLocalDate);
							eventSessionDto.setTitle(eventName);
							eventSessionDto.setStartTime(startLocalTime);
							eventSessionDto.setFinishTime(finishLocalTime);
							if(commonScheduleService.getCommonSessionByDate(startLocalDate) == null) {
								eventSessionDto.setExisted(false);
							}
							else {
								eventSessionDto.setExisted(true);
							}
							listPreview.add(eventSessionDto);
						} 
						startLocalDate = startLocalDate.plusDays(1);
					}
					if(listPreview.isEmpty()) {
						responseMessage.setMessage(Constant.MSG_040);
					} 
					else {
						responseMessage.setData(listPreview);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createEventSchedule(int eventId, List<ScheduleDto> listPreview) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listEventSchedule = new ArrayList<EventSchedule>();
			List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
			for (ScheduleDto scheduleDto : listPreview) {
				if(!scheduleDto.getExisted()) {
					EventSchedule eventSchedule = new EventSchedule();
					eventSchedule.setEvent(eventRepository.findById(eventId).get());
					eventSchedule.setDate(scheduleDto.getDate());
					eventSchedule.setStartTime(scheduleDto.getStartTime());
					eventSchedule.setFinishTime(scheduleDto.getFinishTime());
					eventSchedule.setCreatedBy("LinhLHN");
					eventSchedule.setCreatedOn(LocalDateTime.now());
					eventSchedule.setUpdatedBy("LinhLHN");
					eventSchedule.setUpdatedOn(LocalDateTime.now());
					listEventSchedule.add(eventSchedule);
					CommonSchedule commonSession = new CommonSchedule();
					commonSession.setTitle(eventSchedule.getEvent().getName());
					commonSession.setDate(scheduleDto.getDate());
					commonSession.setStartTime(scheduleDto.getStartTime());
					commonSession.setFinishTime(scheduleDto.getFinishTime());
					commonSession.setCreatedOn(LocalDateTime.now());
					commonSession.setUpdatedOn(LocalDateTime.now());
					listCommon.add(commonSession);
				}
			}
			if(listEventSchedule.isEmpty()) {
				responseMessage.setMessage(Constant.MSG_040);
			} else {
				eventScheduleRepository.saveAll(listEventSchedule);
				commonScheduleRepository.saveAll(listCommon);
				responseMessage.setData(listEventSchedule);
				responseMessage.setMessage(Constant.MSG_066);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createEventSession(int eventId, EventSchedule eventSchedule) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(eventSchedule.getStartTime().compareTo(eventSchedule.getFinishTime()) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				if(eventSchedule.getDate().compareTo(LocalDate.now()) > 0) {
					if (commonScheduleService.getCommonSessionByDate(eventSchedule.getDate()) == null) {
						eventSchedule.setEvent(eventRepository.findById(eventId).get());
						eventSchedule.setCreatedBy("LinhLHN");
						eventSchedule.setCreatedOn(LocalDateTime.now());
						eventSchedule.setUpdatedBy("LinhLHN");
						eventSchedule.setUpdatedOn(LocalDateTime.now());
						eventScheduleRepository.save(eventSchedule);
						responseMessage.setData(Arrays.asList(eventSchedule));
						responseMessage.setMessage(Constant.MSG_067);
						CommonSchedule commonSession = new CommonSchedule();
						commonSession.setTitle(eventSchedule.getEvent().getName());
						commonSession.setDate(eventSchedule.getDate());
						commonSession.setStartTime(eventSchedule.getStartTime());
						commonSession.setFinishTime(eventSchedule.getFinishTime());
						commonSession.setCreatedOn(LocalDateTime.now());
						commonSession.setUpdatedOn(LocalDateTime.now());
						commonScheduleRepository.save(commonSession);
					}
					else {
						responseMessage.setMessage(Constant.MSG_041);
					}
				}
				else {
					responseMessage.setMessage(Constant.MSG_065);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListEventSchedule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findAll();
			responseMessage.setData(listSchedule);
			responseMessage.setMessage("Danh sách lịch sự kiện");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListEventScheduleByEvent(int eventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listSchedule = eventScheduleRepository.findByEventId(eventId);
			responseMessage.setData(listSchedule);
			responseMessage.setMessage("Danh sách lịch của sự kiện " + eventRepository.findById(eventId).get().getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateEventSessionTime(int eventScheduleId, CommonSchedule updateCommonSession) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<EventSchedule> currentSession = eventScheduleRepository.findById(eventScheduleId);
			EventSchedule getEventSession = currentSession.get();
			if(getEventSession.getDate().compareTo(LocalDate.now()) > 0) {
				getEventSession.setStartTime(updateCommonSession.getStartTime());
				getEventSession.setFinishTime(updateCommonSession.getFinishTime());
				getEventSession.setUpdatedBy("LinhLHN");
				getEventSession.setUpdatedOn(LocalDateTime.now());
				eventScheduleRepository.save(getEventSession);
				responseMessage.setData(Arrays.asList(getEventSession));
				responseMessage.setMessage(Constant.MSG_068);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getEventSession.getDate());
				commonSession.setStartTime(updateCommonSession.getStartTime());
				commonSession.setFinishTime(updateCommonSession.getFinishTime());
				commonSession.setUpdatedOn(LocalDateTime.now());
				commonScheduleRepository.save(commonSession);
			}
			else {
				responseMessage.setMessage(Constant.MSG_069);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteEventSession(int eventScheduleId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<EventSchedule> currentSession = eventScheduleRepository.findById(eventScheduleId);
			EventSchedule getEventSession = currentSession.get();
			if(getEventSession.getDate().compareTo(LocalDate.now()) > 0) {
				eventScheduleRepository.delete(getEventSession);
				responseMessage.setData(Arrays.asList(getEventSession));
				responseMessage.setMessage(Constant.MSG_044);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getEventSession.getDate());
				commonScheduleRepository.delete(commonSession);
			}
			else {
				responseMessage.setMessage(Constant.MSG_070);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	@Override
	public ResponseMessage getEventSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<EventSchedule> getEventSessionOp = eventScheduleRepository.findByDate(date);
			EventSchedule getEventSession = getEventSessionOp.get();
			if(getEventSession != null) {
				responseMessage.setData(Arrays.asList(getEventSession));
			}
			else {
				responseMessage.setMessage(Constant.MSG_071);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
