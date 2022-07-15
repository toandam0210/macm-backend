package com.fpt.macm.model.dto;

public class UserActiveDashboardDto {
	private int totalMemberActive;
	private int totalMemberDeactive;
	private String semester;
	public int getTotalMemberActive() {
		return totalMemberActive;
	}
	public void setTotalMemberActive(int totalMemberActive) {
		this.totalMemberActive = totalMemberActive;
	}
	public int getTotalMemberDeactive() {
		return totalMemberDeactive;
	}
	public void setTotalMemberDeactive(int totalMemberDeactive) {
		this.totalMemberDeactive = totalMemberDeactive;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	
	
}
