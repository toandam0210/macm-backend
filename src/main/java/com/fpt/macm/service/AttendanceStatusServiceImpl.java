package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.AttendanceStatusDto;
import com.fpt.macm.model.dto.TrainingScheduleDto;
import com.fpt.macm.model.dto.UserAttendanceStatusDto;
import com.fpt.macm.model.dto.UserAttendanceTrainingReportDto;
import com.fpt.macm.model.entity.AttendanceStatus;
import com.fpt.macm.model.entity.Semester;
import com.fpt.macm.model.entity.TrainingSchedule;
import com.fpt.macm.model.entity.User;
import com.fpt.macm.model.response.ResponseMessage;
import com.fpt.macm.repository.AttendanceStatusRepository;
import com.fpt.macm.repository.SemesterRepository;
import com.fpt.macm.repository.TrainingScheduleRepository;
import com.fpt.macm.repository.UserRepository;
import com.fpt.macm.utils.Utils;

@Service
public class AttendanceStatusServiceImpl implements AttendanceStatusService {

	@Autowired
	TrainingScheduleService trainingScheduleService;

	@Autowired
	AttendanceStatusRepository attendanceStatusRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	SemesterRepository semesterRepository;

	@Autowired
	TrainingScheduleRepository trainingScheduleRepository;

	@Autowired
	SemesterService semesterService;

	@Override
	public ResponseMessage takeAttendanceByStudentId(String studentId, int status, int trainingScheduleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<TrainingSchedule> trainingScheduleOp = trainingScheduleRepository.findById(trainingScheduleId);
			if (trainingScheduleOp.isPresent()) {
				TrainingSchedule trainingSchedule = trainingScheduleOp.get();
				if (trainingSchedule.getDate().isBefore(LocalDate.now())
						|| trainingSchedule.getDate().isEqual(LocalDate.now())) {
					Optional<User> userOp = userRepository.findByStudentId(studentId);
					User user = userOp.get();
					AttendanceStatus attendanceStatus = attendanceStatusRepository
							.findByUserIdAndTrainingScheduleId(user.getId(), trainingSchedule.getId());
					if (attendanceStatus != null) {
						attendanceStatus.setStatus(status);
						attendanceStatus.setUpdatedOn(LocalDateTime.now());
						attendanceStatus.setUpdatedBy("toandv");
						attendanceStatusRepository.save(attendanceStatus);

						AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
						attendanceStatusDto.setId(attendanceStatus.getId());
						attendanceStatusDto.setName(user.getName());
						attendanceStatusDto.setStudentId(studentId);
						attendanceStatusDto.setStatus(status);
						attendanceStatusDto.setTrainingScheduleId(trainingSchedule.getId());
						attendanceStatusDto.setDate(trainingSchedule.getDate());

						responseMessage.setData(Arrays.asList(attendanceStatusDto));
						responseMessage.setMessage(Constant.MSG_055);
					} else {
						responseMessage.setMessage(
								"Không có thông tin điểm danh của " + user.getName() + " - " + user.getStudentId());
					}
				} else {
					responseMessage.setMessage("Không thành công vì chưa đến thời gian điểm danh");
				}
			} else {
				responseMessage.setMessage(Constant.MSG_056);
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage checkAttendanceStatusByTrainingSchedule(int trainingScheduleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			List<AttendanceStatus> attendancesStatus = attendanceStatusRepository
					.findByTrainingScheduleIdOrderByIdAsc(trainingScheduleId);
			List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<AttendanceStatusDto>();
			int attend = 0;
			int absent = 0;
			for (AttendanceStatus attendanceStatus : attendancesStatus) {
				AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
				attendanceStatusDto.setId(attendanceStatus.getId());
				attendanceStatusDto.setName(attendanceStatus.getUser().getName());
				attendanceStatusDto.setStudentId(attendanceStatus.getUser().getStudentId());
				attendanceStatusDto.setStatus(attendanceStatus.getStatus());
				attendanceStatusDto.setTrainingScheduleId(trainingScheduleId);
				if (attendanceStatus.getStatus() == 1) {
					attend++;
				}
				if (attendanceStatus.getStatus() == 0) {
					absent++;
				}
				attendanceStatusDto.setDate(attendanceStatus.getTrainingSchedule().getDate());
				attendanceStatusDtos.add(attendanceStatusDto);
			}
			Collections.sort(attendanceStatusDtos);
			responseMessage.setData(attendanceStatusDtos);
			responseMessage.setMessage(Constant.MSG_057);
			responseMessage.setTotalActive(attend);
			responseMessage.setTotalDeactive(absent);
			responseMessage.setTotalResult(attendanceStatusDtos.size());
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage attendanceTrainingReport(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			List<AttendanceStatus> listAttendanceStatus = attendanceStatusRepository.findAll();
			List<UserAttendanceTrainingReportDto> attendanceTrainingReportsDto = new ArrayList<UserAttendanceTrainingReportDto>();
			if (semesterOp.isPresent()) {
				Semester semester = semesterOp.get();
				List<User> users = new ArrayList<User>();
				List<TrainingSchedule> trainingSchedulesBySemester = trainingScheduleRepository
						.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
				for (TrainingSchedule trainingSchedule : trainingSchedulesBySemester) {
					for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
						if (trainingSchedule.getId() == attendanceStatus.getTrainingSchedule().getId()) {
							User user = userRepository.findById(attendanceStatus.getUser().getId()).get();
							users.add(user);
						}
					}
				}
				Set<User> usersAttendance = new HashSet<User>();
				usersAttendance.addAll(users);
				for (User user : usersAttendance) {
					int totalAbsent = 0;
					UserAttendanceTrainingReportDto attendanceTrainingReportDto = new UserAttendanceTrainingReportDto();
					attendanceTrainingReportDto.setStudentId(user.getStudentId());
					attendanceTrainingReportDto.setStudentName(user.getName());
					attendanceTrainingReportDto.setRoleName(Utils.convertRoleFromDbToExcel(user.getRole()));
					for (AttendanceStatus attendanceStatus : listAttendanceStatus) {
						if (attendanceStatus.getUser().getId() == user.getId() && attendanceStatus.getStatus() == 0) {
							totalAbsent++;
						}
					}
					attendanceTrainingReportDto.setTotalAbsent(
							totalAbsent + " buổi nghỉ trên tổng " + trainingSchedulesBySemester.size() + " buổi tập");
					double percentAbsent = Math
							.ceil(((double) totalAbsent / (double) trainingSchedulesBySemester.size()) * 100);
					attendanceTrainingReportDto.setPercentAbsent(percentAbsent);
					attendanceTrainingReportsDto.add(attendanceTrainingReportDto);
				}
				responseMessage.setData(attendanceTrainingReportsDto);
				responseMessage.setMessage(Constant.MSG_001);
				responseMessage.setTotalResult(attendanceTrainingReportsDto.size());
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAllAttendanceStatusByStudentIdAndSemester(String studentId, String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			User user = userRepository.findByStudentId(studentId).get();
			Semester semester = semesterRepository.findByName(semesterName).get();

			List<UserAttendanceStatusDto> listUserAttendanceStatusDto = new ArrayList<UserAttendanceStatusDto>();

			List<TrainingSchedule> trainingSchedules = trainingScheduleRepository
					.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
			for (TrainingSchedule trainingSchedule : trainingSchedules) {
				UserAttendanceStatusDto userAttendanceStatusDto = new UserAttendanceStatusDto();
				userAttendanceStatusDto.setUserName(user.getName());
				userAttendanceStatusDto.setStudentId(user.getStudentId());
				userAttendanceStatusDto.setDate(trainingSchedule.getDate());
				userAttendanceStatusDto.setStartTime(trainingSchedule.getStartTime());
				userAttendanceStatusDto.setFinishTime(trainingSchedule.getFinishTime());
				userAttendanceStatusDto.setTitle("Lịch tập");
				userAttendanceStatusDto.setType(0);

				AttendanceStatus attendanceStatus = attendanceStatusRepository
						.findByUserIdAndTrainingScheduleId(user.getId(), trainingSchedule.getId());
				if (attendanceStatus != null) {
					userAttendanceStatusDto.setStatus(attendanceStatus.getStatus());
				} else {
					userAttendanceStatusDto.setStatus(2);
				}
				listUserAttendanceStatusDto.add(userAttendanceStatusDto);
			}

			responseMessage.setData(listUserAttendanceStatusDto);
			responseMessage.setMessage(
					"Lấy báo cáo điểm danh cho " + user.getName() + " - " + user.getStudentId() + " thành công.");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getListOldTrainingScheduleToTakeAttendanceBySemester(String semesterName) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = new Semester();
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			if (semesterOp.isPresent()) {
				semester = semesterOp.get();
			} else {
				semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			}

			List<TrainingScheduleDto> oldTrainingSchedules = new ArrayList<TrainingScheduleDto>();

			List<TrainingSchedule> trainingSchedules = trainingScheduleRepository
					.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
			for (TrainingSchedule trainingSchedule : trainingSchedules) {
				if (trainingSchedule.getDate().isBefore(LocalDate.now())) {
					List<AttendanceStatus> listAttendance = attendanceStatusRepository
							.findByTrainingScheduleIdAndStatus(trainingSchedule.getId(), 1);
					List<AttendanceStatus> attendancesStatus = attendanceStatusRepository
							.findByTrainingScheduleIdOrderByIdAsc(trainingSchedule.getId());

					TrainingScheduleDto trainingScheduleDto = new TrainingScheduleDto();
					trainingScheduleDto.setId(trainingSchedule.getId());
					trainingScheduleDto.setDate(trainingSchedule.getDate());
					trainingScheduleDto.setStartTime(trainingSchedule.getStartTime());
					trainingScheduleDto.setFinishTime(trainingSchedule.getFinishTime());
					trainingScheduleDto.setTotalAttend(listAttendance.size());
					trainingScheduleDto.setTotalSize(attendancesStatus.size());
					oldTrainingSchedules.add(trainingScheduleDto);
				}
			}

			if (!oldTrainingSchedules.isEmpty()) {
				responseMessage.setData(oldTrainingSchedules);
				responseMessage.setMessage("Lấy danh sách các buổi tập đã qua của kỳ " + semester.getName()
						+ " để điểm danh lại thành công");
			} else {
				responseMessage.setMessage("Không có buổi tập nào đã qua để điểm danh lại");
			}
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ResponseMessage getAttendanceTrainingStatistic(String semesterName, int roleId) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			Semester semester = new Semester();
			Optional<Semester> semesterOp = semesterRepository.findByName(semesterName);
			if (semesterOp.isPresent()) {
				semester = semesterOp.get();
			} else {
				semester = (Semester) semesterService.getCurrentSemester().getData().get(0);
			}

			List<Map<String, String>> listAttendanceStatistics = new ArrayList<Map<String, String>>();

			List<User> users = new ArrayList<User>();

			if (roleId == 0) {
				users = userRepository.findAllActiveUser();
			} else {
				users = userRepository.findByRoleIdAndIsActive(roleId, true);
			}
			for (User user : users) {
				Utils.convertNameOfRole(user.getRole());

				Map<String, String> attendanceStatistics = new LinkedHashMap<String, String>();

				attendanceStatistics.put("id", String.valueOf(user.getId()));
				attendanceStatistics.put("name", user.getName());
				attendanceStatistics.put("studentId", user.getStudentId());
				attendanceStatistics.put("roleName", user.getRole().getName());

				int totalAbsent = 0;

				List<TrainingSchedule> trainingSchedules = trainingScheduleRepository
						.listTrainingScheduleByTime(semester.getStartDate(), semester.getEndDate());
				for (TrainingSchedule trainingSchedule : trainingSchedules) {
					AttendanceStatus attendanceStatus = attendanceStatusRepository
							.findByUserIdAndTrainingScheduleId(user.getId(), trainingSchedule.getId());
					if (attendanceStatus != null) {
						if (attendanceStatus.getStatus() == 0) {
							totalAbsent++;
							attendanceStatistics.put(trainingSchedule.getDate().toString(), "X");
						} else if (attendanceStatus.getStatus() == 1) {
							attendanceStatistics.put(trainingSchedule.getDate().toString(), "V");
						} else {
							attendanceStatistics.put(trainingSchedule.getDate().toString(), "-");
						}
					} else {
						attendanceStatistics.put(trainingSchedule.getDate().toString(), "-");
					}
				}

				double percentAbsent = Math.ceil(((double) totalAbsent / (double) trainingSchedules.size()) * 100);

				attendanceStatistics.put("totalAbsent", String.valueOf(totalAbsent));
				attendanceStatistics.put("totalSession", String.valueOf(trainingSchedules.size()));
				attendanceStatistics.put("percentAbsent", String.valueOf(percentAbsent) + "%");

				listAttendanceStatistics.add(attendanceStatistics);
			}

			responseMessage.setData(listAttendanceStatistics);
			responseMessage.setMessage("Lấy thống kê điểm danh thành công");
		} catch (Exception e) {
			responseMessage.setMessage(e.getMessage());
		}
		return responseMessage;
	}

}
