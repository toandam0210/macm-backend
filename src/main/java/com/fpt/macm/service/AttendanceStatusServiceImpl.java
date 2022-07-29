package com.fpt.macm.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpt.macm.constant.Constant;
import com.fpt.macm.model.dto.AttendanceStatusDto;
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

	@Override
	public ResponseMessage takeAttendanceByStudentId(String studentId, int status) {
		ResponseMessage responseMessage = new ResponseMessage();
		try {
			TrainingSchedule trainingSchedule = trainingScheduleService.getTrainingScheduleByDate(LocalDate.now());
			if (trainingSchedule != null) {
				Optional<User> userOp = userRepository.findByStudentId(studentId);
				User user = userOp.get();
				List<AttendanceStatus> attendancesStatus = attendanceStatusRepository
						.findByTrainingScheduleId(trainingSchedule.getId());
				AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
				attendanceStatusDto.setName(user.getName());
				attendanceStatusDto.setStudentId(studentId);
				for (AttendanceStatus attendanceStatus : attendancesStatus) {
					if (attendanceStatus.getUser().getId() == user.getId()) {
						attendanceStatus.setStatus(status);
						attendanceStatusDto.setStatus(status);
						attendanceStatus.setUpdatedOn(LocalDateTime.now());
						attendanceStatus.setUpdatedBy("toandv");
						attendanceStatusRepository.save(attendanceStatus);
					}
				}
				responseMessage.setData(Arrays.asList(attendanceStatusDto));
				responseMessage.setMessage(Constant.MSG_055);
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
					.findByTrainingScheduleId(trainingScheduleId);
			List<AttendanceStatusDto> attendanceStatusDtos = new ArrayList<AttendanceStatusDto>();
			int attend = 0;
			int absent = 0;
			for (AttendanceStatus attendanceStatus : attendancesStatus) {
				AttendanceStatusDto attendanceStatusDto = new AttendanceStatusDto();
				attendanceStatusDto.setName(attendanceStatus.getUser().getName());
				attendanceStatusDto.setStudentId(attendanceStatus.getUser().getStudentId());
				attendanceStatusDto.setStatus(attendanceStatus.getStatus());
				if (attendanceStatus.getStatus() == 1) {
					attend++;
				}
				if(attendanceStatus.getStatus() == 0) {
					absent++;
				}
				attendanceStatusDto.setDate(attendanceStatus.getTrainingSchedule().getDate());
				attendanceStatusDtos.add(attendanceStatusDto);
			}
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
					attendanceTrainingReportDto.setTotalAbsent(totalAbsent + " buổi nghỉ trên tổng " + trainingSchedulesBySemester.size() + " buổi tập");
					double percentAbsent = Math.ceil(((double)totalAbsent / (double)trainingSchedulesBySemester.size())*100) ;
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

}
