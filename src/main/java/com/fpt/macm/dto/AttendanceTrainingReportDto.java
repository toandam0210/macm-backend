package com.fpt.macm.dto;

public class AttendanceTrainingReportDto {
	private String studentId;
	private String studentName;
	private String roleName;
	private int totalAttend;
	private int totalAbsent;
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public int getTotalAttend() {
		return totalAttend;
	}
	public void setTotalAttend(int totalAttend) {
		this.totalAttend = totalAttend;
	}
	public int getTotalAbsent() {
		return totalAbsent;
	}
	public void setTotalAbsent(int totalAbsent) {
		this.totalAbsent = totalAbsent;
	}
	
	
}
