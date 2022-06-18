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
import com.fpt.macm.model.Constant;
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.utils.Utils;


@Service
public class TrainingScheduleServiceImpl implements TrainingScheduleService{

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Override
	public ResponseMessage createPreviewTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(startTime.compareTo(finishTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				LocalDate startDate2 = Utils.ConvertStringToLocalDate(startDate);
				LocalDate finishDate2 = Utils.ConvertStringToLocalDate(finishDate);
				
				LocalTime startTime2 = LocalTime.parse(startTime);
				LocalTime finishTime2 = LocalTime.parse(finishTime);
				
				List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
				if(finishDate2.compareTo(LocalDate.now()) < 0) {
					responseMessage.setMessage(Constant.MSG_039);
				} else {
					while(startDate2.compareTo(finishDate2) <= 0) {
						if(startDate2.compareTo(LocalDate.now()) > 0 && dayOfWeek.contains(startDate2.getDayOfWeek().toString())) {
							ScheduleDto trainingSessionDto = new ScheduleDto();
							trainingSessionDto.setDate(startDate2);
							trainingSessionDto.setTitle("Lịch tập");
							trainingSessionDto.setStartTime(startTime2);
							trainingSessionDto.setFinishTime(finishTime2);
							if(getTrainingSessionByDate(startDate2) == null) {
								trainingSessionDto.setExisted(false);
							}
							else {
								trainingSessionDto.setExisted(true);
							}
							listPreview.add(trainingSessionDto);
						} 
						startDate2 = startDate2.plusDays(1);
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
	public ResponseMessage createTrainingSession(TrainingSchedule trainingSchedule) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(trainingSchedule.getStartTime().compareTo(trainingSchedule.getFinishTime()) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				if(trainingSchedule.getDate().compareTo(LocalDate.now()) > 0) {
					if (getTrainingSessionByDate(trainingSchedule.getDate()) == null) {
						trainingSchedule.setCreatedBy("LinhLHN");
						trainingSchedule.setCreatedOn(LocalDateTime.now());
						trainingSchedule.setUpdatedBy("LinhLHN");
						trainingSchedule.setUpdatedOn(LocalDateTime.now());
						trainingScheduleRepository.save(trainingSchedule);
						responseMessage.setData(Arrays.asList(trainingSchedule));
						responseMessage.setMessage(Constant.MSG_037);
					}
					else {
						responseMessage.setMessage(Constant.MSG_041);
					}
				}
				else {
					responseMessage.setMessage(Constant.MSG_039);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListTrainingSchedule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TrainingSchedule> listSchedule = trainingScheduleRepository.findAll();
			responseMessage.setData(listSchedule);
			responseMessage.setMessage("Danh sách lịch tập");
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTrainingSessionTime(int trainingId, TrainingSchedule updateTraningSession) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> currentSession = trainingScheduleRepository.findById(trainingId);
			TrainingSchedule getSession = currentSession.get();
			if(getSession.getDate().compareTo(LocalDate.now()) > 0) {
				getSession.setStartTime(updateTraningSession.getStartTime());
				getSession.setFinishTime(updateTraningSession.getFinishTime());
				getSession.setCreatedBy("LinhLHN");
				getSession.setCreatedOn(LocalDateTime.now());
				getSession.setUpdatedBy("LinhLHN");
				getSession.setUpdatedOn(LocalDateTime.now());
				trainingScheduleRepository.save(getSession);
				responseMessage.setData(Arrays.asList(getSession));
				responseMessage.setMessage(Constant.MSG_042);
			}
			else {
				responseMessage.setMessage(Constant.MSG_043);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteTrainingSession(int trainingId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> currentSession = trainingScheduleRepository.findById(trainingId);
			TrainingSchedule getSession = currentSession.get();
			if(getSession.getDate().compareTo(LocalDate.now()) > 0) {
				trainingScheduleRepository.delete(getSession);
				responseMessage.setData(Arrays.asList(getSession));
				responseMessage.setMessage(Constant.MSG_044);
			}
			else {
				responseMessage.setMessage(Constant.MSG_045);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getTrainingSessionByDate(String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			if(getTrainingSessionByDate(getDate) != null) {
				responseMessage.setData(Arrays.asList(getTrainingSessionByDate(getDate)));
			}
			else {
				responseMessage.setMessage(Constant.MSG_051);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
	
	public TrainingSchedule getTrainingSessionByDate(LocalDate date) {
		try {
			Optional<TrainingSchedule> getSessionOp = trainingScheduleRepository.findByDate(date);
			if(getSessionOp.isPresent()) {
				return getSessionOp.get();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public ResponseMessage createTrainingSchedule(List<ScheduleDto> listPreview) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TrainingSchedule> listTraining = new ArrayList<TrainingSchedule>();
			for (ScheduleDto scheduleDto : listPreview) {
				if(!scheduleDto.getExisted()) {
					TrainingSchedule trainingSchedule = new TrainingSchedule();
					trainingSchedule.setDate(scheduleDto.getDate());
					trainingSchedule.setStartTime(scheduleDto.getStartTime());
					trainingSchedule.setFinishTime(scheduleDto.getFinishTime());
					trainingSchedule.setCreatedBy("LinhLHN");
					trainingSchedule.setCreatedOn(LocalDateTime.now());
					trainingSchedule.setUpdatedBy("LinhLHN");
					trainingSchedule.setUpdatedOn(LocalDateTime.now());
					listTraining.add(trainingSchedule);
				}
			}
			if(listTraining.isEmpty()) {
				responseMessage.setMessage(Constant.MSG_040);
			} else {
				trainingScheduleRepository.saveAll(listTraining);
				responseMessage.setData(listTraining);
				responseMessage.setMessage(Constant.MSG_036);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
