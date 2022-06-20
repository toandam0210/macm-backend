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
import com.fpt.macm.model.ResponseMessage;
import com.fpt.macm.model.Semester;
import com.fpt.macm.model.TrainingSchedule;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.utils.Utils;


@Service
public class TrainingScheduleServiceImpl implements TrainingScheduleService{

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;
	
	@Autowired
	CommonScheduleRepository commonScheduleRepository;
	
	@Autowired
	SemesterRepository semesterRepository;
	
	@Autowired
	CommonScheduleService commonScheduleService;
	
	@Autowired
	SemesterService semesterService;
	
	@Override
	public ResponseMessage createPreviewTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek, String startTime, String finishTime) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(startDate.compareTo(finishDate) > 0) {
				responseMessage.setMessage(Constant.MSG_081);
			}
			else if(startTime.compareTo(finishTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} 
			else if(finishDate.compareTo(LocalDate.now().toString()) < 0) {
				responseMessage.setMessage(Constant.MSG_039);
			} else {
				LocalDate startLocalDate = Utils.ConvertStringToLocalDate(startDate);
				LocalDate finishLocalDate = Utils.ConvertStringToLocalDate(finishDate);
				
				LocalTime startLocalTime = LocalTime.parse(startTime);
				LocalTime finishLocalTime = LocalTime.parse(finishTime);
				
				Semester currentSemester = (Semester) semesterService.getCurrentSemester().getData().get(0);
				if(finishLocalDate.compareTo(currentSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				}
				else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					while(startLocalDate.compareTo(finishLocalDate) <= 0) {
						if(startLocalDate.compareTo(LocalDate.now()) > 0 && dayOfWeek.contains(startLocalDate.getDayOfWeek().toString())) {
							ScheduleDto trainingSessionDto = new ScheduleDto();
							trainingSessionDto.setDate(startLocalDate);
							trainingSessionDto.setTitle("Lịch tập");
							trainingSessionDto.setStartTime(startLocalTime);
							trainingSessionDto.setFinishTime(finishLocalTime);
							if(commonScheduleService.getCommonSessionByDate(startLocalDate) == null) {
								trainingSessionDto.setExisted(false);
							}
							else {
								trainingSessionDto.setExisted(true);
							}
							listPreview.add(trainingSessionDto);
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
	public ResponseMessage createTrainingSession(TrainingSchedule trainingSchedule) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			if(trainingSchedule.getStartTime().compareTo(trainingSchedule.getFinishTime()) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				if(trainingSchedule.getDate().compareTo(LocalDate.now()) > 0) {
					if (commonScheduleService.getCommonSessionByDate(trainingSchedule.getDate()) == null) {
						trainingSchedule.setCreatedBy("LinhLHN");
						trainingSchedule.setCreatedOn(LocalDateTime.now());
						trainingSchedule.setUpdatedBy("LinhLHN");
						trainingSchedule.setUpdatedOn(LocalDateTime.now());
						trainingScheduleRepository.save(trainingSchedule);
						responseMessage.setData(Arrays.asList(trainingSchedule));
						responseMessage.setMessage(Constant.MSG_037);
						CommonSchedule commonSession = new CommonSchedule();
						commonSession.setTitle("Lịch tập");
						commonSession.setDate(trainingSchedule.getDate());
						commonSession.setStartTime(trainingSchedule.getStartTime());
						commonSession.setFinishTime(trainingSchedule.getFinishTime());
						commonSession.setCreatedOn(LocalDateTime.now());
						commonSession.setUpdatedOn(LocalDateTime.now());
						commonScheduleRepository.save(commonSession);
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
	public ResponseMessage updateTrainingSessionTime(int trainingScheduleId, CommonSchedule updateCommonSession) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> currentSession = trainingScheduleRepository.findById(trainingScheduleId);
			TrainingSchedule getTrainingSession = currentSession.get();
			if(getTrainingSession.getDate().compareTo(LocalDate.now()) > 0) {
				getTrainingSession.setStartTime(updateCommonSession.getStartTime());
				getTrainingSession.setFinishTime(updateCommonSession.getFinishTime());
				getTrainingSession.setUpdatedBy("LinhLHN");
				getTrainingSession.setUpdatedOn(LocalDateTime.now());
				trainingScheduleRepository.save(getTrainingSession);
				responseMessage.setData(Arrays.asList(getTrainingSession));
				responseMessage.setMessage(Constant.MSG_042);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getTrainingSession.getDate());
				commonSession.setStartTime(updateCommonSession.getStartTime());
				commonSession.setFinishTime(updateCommonSession.getFinishTime());
				commonSession.setUpdatedOn(LocalDateTime.now());
				commonScheduleRepository.save(commonSession);
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
	public ResponseMessage deleteTrainingSession(int trainingScheduleId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> currentSession = trainingScheduleRepository.findById(trainingScheduleId);
			TrainingSchedule getTrainingSession = currentSession.get();
			if(getTrainingSession.getDate().compareTo(LocalDate.now()) > 0) {
				trainingScheduleRepository.delete(getTrainingSession);
				responseMessage.setData(Arrays.asList(getTrainingSession));
				responseMessage.setMessage(Constant.MSG_044);
				CommonSchedule commonSession = commonScheduleService.getCommonSessionByDate(getTrainingSession.getDate());
				commonScheduleRepository.delete(commonSession);
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
	public ResponseMessage getTrainingSessionByDate(LocalDate date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> getTrainingSessionOp = trainingScheduleRepository.findByDate(date);
			TrainingSchedule getTrainingSession = getTrainingSessionOp.get();
			if(getTrainingSession != null) {
				responseMessage.setData(Arrays.asList(getTrainingSession));
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

	@Override
	public ResponseMessage createTrainingSchedule(List<ScheduleDto> listPreview) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TrainingSchedule> listTraining = new ArrayList<TrainingSchedule>();
			List<CommonSchedule> listCommon = new ArrayList<CommonSchedule>();
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
					CommonSchedule commonSchedule = new CommonSchedule();
					commonSchedule.setDate(scheduleDto.getDate());
					commonSchedule.setStartTime(scheduleDto.getStartTime());
					commonSchedule.setFinishTime(scheduleDto.getFinishTime());
					commonSchedule.setCreatedOn(LocalDateTime.now());
					commonSchedule.setUpdatedOn(LocalDateTime.now());
					listCommon.add(commonSchedule);
				}
			}
			if(listTraining.isEmpty()) {
				responseMessage.setMessage(Constant.MSG_040);
			} else {
				trainingScheduleRepository.saveAll(listTraining);
				commonScheduleRepository.saveAll(listCommon);
				responseMessage.setData(listTraining);
				responseMessage.setMessage(Constant.MSG_036);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getTraningScheduleBySemester(int semesterId) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester getSemester = semesterRepository.findById(semesterId).get();
			List<TrainingSchedule> getTrainingScheduleBySemester = trainingScheduleRepository.listTrainingScheduleByTime(getSemester.getStartDate(), getSemester.getEndDate());
			responseMessage.setData(getTrainingScheduleBySemester);
			responseMessage.setMessage(Constant.MSG_082 + getSemester.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}
}
