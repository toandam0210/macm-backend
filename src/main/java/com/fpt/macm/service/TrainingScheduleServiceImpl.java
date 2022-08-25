package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.ScheduleDto;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.CommonSchedule;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.CommonScheduleRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class TrainingScheduleServiceImpl implements TrainingScheduleService {

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

	@Autowired
	NotificationService notificationService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Override
	public ResponseMessage createPreviewTrainingSchedule(String startDate, String finishDate, List<String> dayOfWeek,
			String startTime, String finishTime) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate startLocalDate = Utils.ConvertStringToLocalDate(startDate);
			LocalDate finishLocalDate = Utils.ConvertStringToLocalDate(finishDate);

			LocalTime startLocalTime = LocalTime.parse(startTime);
			LocalTime finishLocalTime = LocalTime.parse(finishTime);

			if (startLocalDate.compareTo(finishLocalDate) > 0) {
				responseMessage.setMessage(Constant.MSG_081);
			} else if (startLocalTime.compareTo(finishLocalTime) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else if (finishLocalDate.compareTo(LocalDate.now()) < 0) {
				responseMessage.setMessage(Constant.MSG_039);
			} else {
				Semester lastSemester = semesterRepository.findAll(Sort.by("startDate").descending()).get(0);
				if (finishLocalDate.compareTo(lastSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				} else {
					List<ScheduleDto> listPreview = new ArrayList<ScheduleDto>();
					while (startLocalDate.compareTo(finishLocalDate) <= 0) {
						if (startLocalDate.compareTo(LocalDate.now()) > 0
								&& dayOfWeek.contains(startLocalDate.getDayOfWeek().toString())) {
							ScheduleDto trainingSessionDto = new ScheduleDto();
							trainingSessionDto.setDate(startLocalDate);
							trainingSessionDto.setTitle("Lịch tập");
							trainingSessionDto.setStartTime(startLocalTime);
							trainingSessionDto.setFinishTime(finishLocalTime);
							if (commonScheduleService.getCommonSessionByDate(startLocalDate) == null) {
								trainingSessionDto.setExisted(false);
							} else {
								trainingSessionDto.setTitle("Trùng với "
										+ commonScheduleService.getCommonSessionByDate(startLocalDate).getTitle());
								trainingSessionDto.setExisted(true);
							}
							listPreview.add(trainingSessionDto);
						}
						startLocalDate = startLocalDate.plusDays(1);
					}
					if (listPreview.isEmpty()) {
						responseMessage.setMessage(Constant.MSG_040);
					} else {
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
			if (trainingSchedule.getStartTime().compareTo(trainingSchedule.getFinishTime()) >= 0) {
				responseMessage.setMessage(Constant.MSG_038);
			} else {
				Semester lastSemester = semesterRepository.findAll(Sort.by("startDate").descending()).get(0);
				if (LocalDate.now().compareTo(lastSemester.getEndDate()) > 0) {
					responseMessage.setMessage(Constant.MSG_080);
				}
				else {
					if (trainingSchedule.getDate().compareTo(LocalDate.now()) > 0) {
						CommonSchedule commonSchedule = commonScheduleService
								.getCommonSessionByDate(trainingSchedule.getDate());
						if (commonSchedule == null) {
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
							commonSession.setType(0);
							commonScheduleRepository.save(commonSession);

							// Thêm data điểm danh
							List<AttendanceStatus> listAttendanceStatus = new ArrayList<AttendanceStatus>();
							List<User> users = userRepository.findAllActiveUser();
							if (!users.isEmpty()) {
								Optional<TrainingSchedule> trainingScheduleOp = trainingScheduleRepository
										.findByDate(trainingSchedule.getDate());
								if (trainingScheduleOp.isPresent()) {
									for (User user : users) {
										AttendanceStatus attendanceStatus = new AttendanceStatus();
										attendanceStatus.setUser(user);
										attendanceStatus.setTrainingSchedule(trainingScheduleOp.get());
										attendanceStatus.setCreatedOn(LocalDateTime.now());
										attendanceStatus.setCreatedBy("toandv");
										attendanceStatus.setStatus(2);
										listAttendanceStatus.add(attendanceStatus);
									}
								}
							}

							if (!listAttendanceStatus.isEmpty()) {
								attendanceStatusRepository.saveAll(listAttendanceStatus);
							}

							// Gửi thông báo đến cho user khi tạo 1 buổi tập mới
							notificationService.createTrainingSessionCreateNotification(trainingSchedule.getDate());
						} else {
							switch (commonSchedule.getType()) {
							case 0:
								responseMessage.setMessage(
										"Không thành công. Đã có " + commonSchedule.getTitle() + " trong ngày này.");
								break;
							case 1:
								responseMessage.setMessage("Không thành công. Đã có sự kiện " + commonSchedule.getTitle()
										+ " trong ngày này.");
								break;
							case 2:
								responseMessage.setMessage("Không thành công. Đã có giải đấu " + commonSchedule.getTitle()
										+ " trong ngày này.");
								break;
							default:
								responseMessage.setMessage("Không thành công. Vui lòng thử lại.");
								break;
							}

						}
					} else {
						responseMessage.setMessage(Constant.MSG_039);
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
	public ResponseMessage getListTrainingSchedule() {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<TrainingSchedule> listSchedule = trainingScheduleRepository.findAll();
			if (listSchedule.size() > 0) {
				responseMessage.setData(listSchedule);
				responseMessage.setMessage("Danh sách lịch tập");
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage updateTrainingSessionTime(String date, CommonSchedule updateCommonSession) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			if (getDate.compareTo(LocalDate.now()) > 0) {
				TrainingSchedule getTrainingSession = getTrainingScheduleByDate(getDate);
				if (getTrainingSession != null) {
					CommonSchedule commonSession = commonScheduleService
							.getCommonSessionByDate(getTrainingSession.getDate());
					if (commonSession.getType() == 0) {
						getTrainingSession.setStartTime(updateCommonSession.getStartTime());
						getTrainingSession.setFinishTime(updateCommonSession.getFinishTime());
						getTrainingSession.setUpdatedBy("LinhLHN");
						getTrainingSession.setUpdatedOn(LocalDateTime.now());
						trainingScheduleRepository.save(getTrainingSession);
						commonSession.setStartTime(updateCommonSession.getStartTime());
						commonSession.setFinishTime(updateCommonSession.getFinishTime());
						commonSession.setUpdatedOn(LocalDateTime.now());
						commonScheduleRepository.save(commonSession);
						responseMessage.setData(Arrays.asList(getTrainingSession));
						responseMessage.setMessage(Constant.MSG_042);

						notificationService.createTrainingSessionUpdateNotification(getDate,
								updateCommonSession.getStartTime(), updateCommonSession.getFinishTime());
					}
				}
			} else {
				responseMessage.setMessage(Constant.MSG_043);
			}
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage deleteTrainingSession(String date) {
		// TODO Auto-generated method stub
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			LocalDate getDate = Utils.ConvertStringToLocalDate(date);
			if (getDate.compareTo(LocalDate.now()) > 0) {
				TrainingSchedule getTrainingSession = getTrainingScheduleByDate(getDate);
				if (getTrainingSession != null) {
					CommonSchedule commonSession = commonScheduleService
							.getCommonSessionByDate(getTrainingSession.getDate());
					if (commonSession.getType() == 0) {
						List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository
								.findByTrainingScheduleIdOrderByIdAsc(getTrainingSession.getId());
						if (!listAttendanceStatus.isEmpty()) {
							attendanceStatusRepository.deleteAll(listAttendanceStatus);
						}

						trainingScheduleRepository.delete(getTrainingSession);
						commonScheduleRepository.delete(commonSession);
						responseMessage.setData(Arrays.asList(getTrainingSession));
						responseMessage.setMessage(Constant.MSG_044);

						notificationService.createTrainingSessionDeleteNotification(getDate);
					}
				} else {
					responseMessage.setMessage("Không có buổi tập này");
				}
			} else {
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
			Optional<TrainingSchedule> getTrainingSessionOp = trainingScheduleRepository.findByDate(getDate);
			if (getTrainingSessionOp.isPresent()) {
				TrainingSchedule getTrainingSession = getTrainingSessionOp.get();
				responseMessage.setData(Arrays.asList(getTrainingSession));
			} else {
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
				if (!scheduleDto.getExisted()) {
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
					commonSchedule.setTitle("Lịch tập");
					commonSchedule.setDate(scheduleDto.getDate());
					commonSchedule.setStartTime(scheduleDto.getStartTime());
					commonSchedule.setFinishTime(scheduleDto.getFinishTime());
					commonSchedule.setCreatedOn(LocalDateTime.now());
					commonSchedule.setUpdatedOn(LocalDateTime.now());
					commonSchedule.setType(0);
					listCommon.add(commonSchedule);
				}
			}
			if (listTraining.isEmpty()) {
				responseMessage.setMessage(Constant.MSG_040);
			} else {
				trainingScheduleRepository.saveAll(listTraining);
				commonScheduleRepository.saveAll(listCommon);
				responseMessage.setData(listTraining);
				responseMessage.setMessage(Constant.MSG_036);

				// Thêm data điểm danh
				List<User> users = userRepository.findAllActiveUser();
				List<AttendanceStatus> listAttendanceStatus = new ArrayList<AttendanceStatus>();
				if (!users.isEmpty()) {
					for (TrainingSchedule trainingSchedule : listTraining) {
						Optional<TrainingSchedule> trainingScheduleOp = trainingScheduleRepository
								.findByDate(trainingSchedule.getDate());
						if (trainingScheduleOp.isPresent()) {
							for (User user : users) {
								AttendanceStatus attendanceStatus = new AttendanceStatus();
								attendanceStatus.setUser(user);
								attendanceStatus.setTrainingSchedule(trainingScheduleOp.get());
								attendanceStatus.setCreatedOn(LocalDateTime.now());
								attendanceStatus.setCreatedBy("toandv");
								attendanceStatus.setStatus(2);
								listAttendanceStatus.add(attendanceStatus);
							}
						}
					}
				}

				if (!listAttendanceStatus.isEmpty()) {
					attendanceStatusRepository.saveAll(listAttendanceStatus);
				}
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
			List<TrainingSchedule> getTrainingScheduleBySemester = trainingScheduleRepository
					.listTrainingScheduleByTime(getSemester.getStartDate(), getSemester.getEndDate());
			responseMessage.setData(getTrainingScheduleBySemester);
			responseMessage.setMessage(Constant.MSG_082 + getSemester.getName());
		} catch (Exception e) {
			// TODO: handle exception
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public TrainingSchedule getTrainingScheduleByDate(LocalDate date) {
		// TODO Auto-generated method stub
		try {
			Optional<TrainingSchedule> getTrainingSessionOp = trainingScheduleRepository.findByDate(date);
			if (getTrainingSessionOp.isPresent()) {
				TrainingSchedule getTrainingSession = getTrainingSessionOp.get();
				return getTrainingSession;
			} else {
				return null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
