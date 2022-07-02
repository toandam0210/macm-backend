package com.fpt.macm.dto;

public class AttendanceTrainingReportDto {
	private String studentId;
	private String studentName;
	private String roleName;
	private String totalAbsent;
	private double percentAbsent;
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
	public String getTotalAbsent() {
		return totalAbsent;
	}
	public void setTotalAbsent(String totalAbsent) {
		this.totalAbsent = totalAbsent;
	}
	public double getPercentAbsent() {
		return percentAbsent;
	}
	public void setPercentAbsent(double percentAbsent) {
		this.percentAbsent = percentAbsent;
	}	
}
