package com.fpt.macm.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceStatisticDto {

	private int userId;
	private String userName;
	private String userStudentId;
	private String roleInClub;
	List<AttendanceTrainingDto> attendanceTrainingsDto = new ArrayList<AttendanceTrainingDto>();
	Map<LocalDate, Integer> attendanceTrainings = new HashMap<LocalDate, Integer>();
	private double percentAbsent;
	private int totalAbsent;
	private int totalSession;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserStudentId() {
		return userStudentId;
	}

	public void setUserStudentId(String userStudentId) {
		this.userStudentId = userStudentId;
	}

	public String getRoleInClub() {
		return roleInClub;
	}

	public void setRoleInClub(String roleInClub) {
		this.roleInClub = roleInClub;
	}

	public List<AttendanceTrainingDto> getAttendanceTrainingsDto() {
		return attendanceTrainingsDto;
	}

	public void setAttendanceTrainingsDto(List<AttendanceTrainingDto> attendanceTrainingsDto) {
		this.attendanceTrainingsDto = attendanceTrainingsDto;
	}

	public double getPercentAbsent() {
		return percentAbsent;
	}

	public void setPercentAbsent(double percentAbsent) {
		this.percentAbsent = percentAbsent;
	}

	public int getTotalAbsent() {
		return totalAbsent;
	}

	public void setTotalAbsent(int totalAbsent) {
		this.totalAbsent = totalAbsent;
	}

	public int getTotalSession() {
		return totalSession;
	}

	public void setTotalSession(int totalSession) {
		this.totalSession = totalSession;
	}

	public Map<LocalDate, Integer> getAttendanceTrainings() {
		return attendanceTrainings;
	}

	public void setAttendanceTrainings(Map<LocalDate, Integer> attendanceTrainings) {
		this.attendanceTrainings = attendanceTrainings;
	}

}
