package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public ResponseMessage createTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime) {
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
				
				List<TrainingSchedule> listTraining = new ArrayList<TrainingSchedule>();
				if(finishDate2.compareTo(LocalDate.now()) < 0) {
					responseMessage.setMessage(Constant.MSG_039);
				} else {
					while(startDate2.compareTo(finishDate2) <= 0) {
						if(startDate2.compareTo(LocalDate.now()) > 0) {
							if(dayOfWeek.contains(startDate2.getDayOfWeek().toString())) {
								List<TrainingSchedule> getSession = trainingScheduleRepository.getTrainingSchedule(startDate2, startDate2);
								if (getSession.isEmpty()) {
									TrainingSchedule trainingSchedule = new TrainingSchedule();
									trainingSchedule.setDate(startDate2);
									trainingSchedule.setStartTime(startTime2);
									trainingSchedule.setFinishTime(finishTime2);
									trainingSchedule.setCreatedBy("LinhLHN");
									trainingSchedule.setCreatedOn(LocalDateTime.now());
									trainingSchedule.setUpdatedBy("LinhLHN");
									trainingSchedule.setUpdatedOn(LocalDateTime.now());
									listTraining.add(trainingSchedule);
								}
							}
						}
						startDate2 = startDate2.plusDays(1);
					}
					if(listTraining.isEmpty()) {
						responseMessage.setMessage(Constant.MSG_040);
					} 
					else {
						trainingScheduleRepository.saveAll(listTraining);
						responseMessage.setMessage(Constant.MSG_036);
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
					List<TrainingSchedule> getSession = trainingScheduleRepository.getTrainingSchedule(trainingSchedule.getDate(), trainingSchedule.getDate());
					if (getSession.isEmpty()) {
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
	public ResponseMessage getListTrainingSchedule(int month, int year) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate firstDay = LocalDate.now().withDayOfMonth(1).withMonth(month).withYear(year);
			LocalDate lastDay = firstDay.with(TemporalAdjusters.lastDayOfMonth());
			List<TrainingSchedule> listSchedule = trainingScheduleRepository.getTrainingSchedule(firstDay, lastDay);
			responseMessage.setData(listSchedule);
			responseMessage.setMessage("Danh sách lịch tập tháng " + month + " năm " + year);
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

}
