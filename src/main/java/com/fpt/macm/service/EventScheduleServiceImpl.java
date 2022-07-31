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

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Event;
import com.fpt.macm.model.entity.EventSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.EventRepository;
import com.fpt.macm.repository.EventScheduleRepository;
import com.fpt.macm.repository.MemberEventRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.utils.Utils;

@Service
public class EventScheduleServiceImpl implements EventScheduleService{

	@Autowired
	EventScheduleRepository eventScheduleRepository;
	
	@Autowired
	MemberEventRepository memberEventRepository;
	
	@Autowired
	EventRepository eventRepository;
	
	@Autowired
	CommonScheduleRepository commonScheduleRepository;
	
	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Autowired
	CommonScheduleService commonScheduleService;
	
	@Autowired
	TrainingScheduleService trainingScheduleService;
	
	@Autowired
	SemesterService semesterService;
	
	@Autowired
	NotificationService notificationService;
	
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
			else if(startLocalDate.compareTo(finishLocalDate) == 0 && startLocalTime.compareTo(finishLocalTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			}
			else if(startLocalDate.compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_065);
			}
			else {
				Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
				if(finishLocalDate.compareTo(currentSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				}
				else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					for(LocalDate currentLocalDate = startLocalDate; currentLocalDate.compareTo(finishLocalDate) <= 0; currentLocalDate = currentLocalDate.plusDays(1)) {
						if(currentLocalDate.compareTo(LocalDate.now()) > 0) {
							ScheduleDto eventSessionDto = new ScheduleDto();
							eventSessionDto.setDate(currentLocalDate);
							eventSessionDto.setTitle(eventName);
							if(currentLocalDate.compareTo(startLocalDate) == 0) {
								eventSessionDto.setStartTime(startLocalTime);
							} else {
								eventSessionDto.setStartTime(LocalTime.MIN);
							}
							if(currentLocalDate.compareTo(finishLocalDate) == 0) {
								eventSessionDto.setFinishTime(finishLocalTime);
							} else {
								eventSessionDto.setFinishTime(LocalTime.MAX);
							}
							if(commonScheduleService.getCommonSessionByDate(currentLocalDate) == null) {
								eventSessionDto.setExisted(false);
							}
							else {
								eventSessionDto.setTitle("Trùng với " + commonScheduleService.getCommonSessionByDate(currentLocalDate).getTitle());
								eventSessionDto.setExisted(true);
							}
							listPreview.add(eventSessionDto);
						}
					}
					responseMessage.setData(listPreview);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage createEventSchedule(int eventId, List<ScheduleDto> listPreview, Boolean isOverwritten) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listEventSchedule = new ArrayList<EventSchedule>();
			List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
			List<CommonSchedule> listCommonOverwritten = new ArrayList<CommonSchedule>();
			List<TrainingSchedule> listTrainingOverwritten = new ArrayList<TrainingSchedule>();
			Boolean isInterrupted = false;
			String title = "";
			Event event = eventRepository.findById(eventId).get();
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
					commonSession.setType(1);
					listCommon.add(commonSession);
				}
				else {
					if(isOverwritten && scheduleDto.getTitle().toString().equals("Trùng với Lịch tập")) {
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
						commonSession.setType(1);
						listCommon.add(commonSession);
						CommonSchedule getCommonSession = commonScheduleService.getCommonSessionByDate(scheduleDto.getDate());
						listCommonOverwritten.add(getCommonSession);
						TrainingSchedule getTrainingSession = trainingScheduleService.getTrainingScheduleByDate(scheduleDto.getDate());
						listTrainingOverwritten.add(getTrainingSession);
					}
					else {
						isInterrupted = true;
						title = scheduleDto.getTitle();
						break;
					}
				}
			}
			if(isInterrupted) {
				responseMessage.setMessage(Constant.MSG_093 + title);
			}
			else {
				if(listEventSchedule.isEmpty()) {
					responseMessage.setMessage(Constant.MSG_040);
				} else {
					eventScheduleRepository.saveAll(listEventSchedule);
					commonScheduleRepository.deleteAll(listCommonOverwritten);
					trainingScheduleRepository.deleteAll(listTrainingOverwritten);
					commonScheduleRepository.saveAll(listCommon);
					responseMessage.setData(listEventSchedule);
					responseMessage.setMessage(Constant.MSG_066);
					
					notificationService.createEventNotification(eventId, event.getName());
				}
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
						commonSession.setType(1);
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
	public ResponseMessage getEventSessionByDate(String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			Optional<EventSchedule> getEventSessionOp = eventScheduleRepository.findByDate(getDate);
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

	@Override
	public EventSchedule getEventScheduleByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<EventSchedule> getEventSessionOp = eventScheduleRepository.findByDate(date);
			EventSchedule getEventSession = getEventSessionOp.get();
			if(getEventSession != null) {
				return getEventSession;
			}
			else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

	@Override
	public ResponseMessage updatePreviewEventSchedule(int eventId, String startDate, String finishDate,
			String startTime, String finishTime) {
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
			else if(startLocalDate.compareTo(finishLocalDate) == 0 && startLocalTime.compareTo(finishLocalTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			}
			else if(startLocalDate.compareTo(LocalDate.now()) <= 0) {
				responseMessage.setMessage(Constant.MSG_065);
			}
			else {
				Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
				if(finishLocalDate.compareTo(currentSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				}
				else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					for(LocalDate currentLocalDate = startLocalDate; currentLocalDate.compareTo(finishLocalDate) <= 0; currentLocalDate = currentLocalDate.plusDays(1)) {
						if(currentLocalDate.compareTo(LocalDate.now()) > 0) {
							ScheduleDto eventSessionDto = new ScheduleDto();
							eventSessionDto.setDate(currentLocalDate);
							String title = eventRepository.findById(eventId).get().getName();
							eventSessionDto.setTitle(title);
							if(currentLocalDate.compareTo(startLocalDate) == 0) {
								eventSessionDto.setStartTime(startLocalTime);
							} else {
								eventSessionDto.setStartTime(LocalTime.MIN);
							}
							if(currentLocalDate.compareTo(finishLocalDate) == 0) {
								eventSessionDto.setFinishTime(finishLocalTime);
							} else {
								eventSessionDto.setFinishTime(LocalTime.MAX);
							}
							CommonSchedule getCommonSession = commonScheduleService.getCommonSessionByDate(currentLocalDate);
							if(getCommonSession ==  null) {
								eventSessionDto.setExisted(false);
							}
							else {
								if(getCommonSession.getTitle().equals(title)) {
									eventSessionDto.setExisted(false);
								}
								else {
									eventSessionDto.setTitle("Trùng với " + getCommonSession.getTitle());
									eventSessionDto.setExisted(true);
								}
							}
							listPreview.add(eventSessionDto);
						}
					}
					responseMessage.setData(listPreview);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateEventSchedule(int eventId, List<ScheduleDto> listPreview, Boolean isOverwritten) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> listEventSchedule = new ArrayList<EventSchedule>();
			List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
			List<CommonSchedule> listCommonOverwritten = new ArrayList<CommonSchedule>();
			List<TrainingSchedule> listTrainingOverwritten = new ArrayList<TrainingSchedule>();
			Boolean isInterrupted = false;
			String title = "";
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
					commonSession.setType(1);
					listCommon.add(commonSession);
				}
				else {
					if(isOverwritten && scheduleDto.getTitle().toString().equals("Trùng với Lịch tập")) {
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
						commonSession.setType(1);
						listCommon.add(commonSession);
						CommonSchedule getCommonSession = commonScheduleService.getCommonSessionByDate(scheduleDto.getDate());
						listCommonOverwritten.add(getCommonSession);
						TrainingSchedule getTrainingSession = trainingScheduleService.getTrainingScheduleByDate(scheduleDto.getDate());
						listTrainingOverwritten.add(getTrainingSession);
					}
					else {
						isInterrupted = true;
						title = scheduleDto.getTitle();
						break;
					}
				}
			}
			if(isInterrupted) {
				responseMessage.setMessage(Constant.MSG_093 + title);
			}
			else {
				if(listEventSchedule.isEmpty()) {
					responseMessage.setMessage(Constant.MSG_040);
				} else {
					List<EventSchedule> oldSchedule = eventScheduleRepository.findByEventId(eventId);
					for (EventSchedule getEventSession : oldSchedule) {
						CommonSchedule getCommonSession = commonScheduleService.getCommonSessionByDate(getEventSession.getDate());
						eventScheduleRepository.delete(getEventSession);
						commonScheduleRepository.delete(getCommonSession);
					}
					eventScheduleRepository.saveAll(listEventSchedule);
					commonScheduleRepository.deleteAll(listCommonOverwritten);
					trainingScheduleRepository.deleteAll(listTrainingOverwritten);
					commonScheduleRepository.saveAll(listCommon);
					responseMessage.setData(listEventSchedule);
					responseMessage.setMessage(Constant.MSG_102);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getPeriodTimeOfEvent(int eventId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<EventSchedule> getEventSchedules = eventScheduleRepository.findByEventId(eventId);
			String [] result = new String [4];
			result[0] = getEventSchedules.get(0).getDate().toString();
			result[1] = getEventSchedules.get(getEventSchedules.size() - 1).getDate().toString();
			result[2] = getEventSchedules.get(0).getStartTime().toString();
			result[3] = getEventSchedules.get(getEventSchedules.size() - 1).getFinishTime().toString();
			responseMessage.setData(Arrays.asList(result));
			responseMessage.setMessage("Lấy thông tin thời gian sự kiện thành công");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public List<EventSchedule> listEventScheduleByEvent(int eventId) {
		// TODO Auto-generated method stub
		try {
			return eventScheduleRepository.findByEventId(eventId);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
