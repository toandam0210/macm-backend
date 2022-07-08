package com.fpt.macm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_status_report")
public class UserStatusReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String semester;
	
	@Column
	private int numberActiveInSemester;
	
	@Column
	private int numberDeactiveInSemester;
	
	@Column
	private int totalNumberUserInSemester;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getNumberActiveInSemester() {
		return numberActiveInSemester;
	}

	public void setNumberActiveInSemester(int numberActiveInSemester) {
		this.numberActiveInSemester = numberActiveInSemester;
	}

	public int getNumberDeactiveInSemester() {
		return numberDeactiveInSemester;
	}

	public void setNumberDeactiveInSemester(int numberDeactiveInSemester) {
		this.numberDeactiveInSemester = numberDeactiveInSemester;
	}

	public int getTotalNumberUserInSemester() {
		return totalNumberUserInSemester;
	}

	public void setTotalNumberUserInSemester(int totalNumberUserInSemester) {
		this.totalNumberUserInSemester = totalNumberUserInSemester;
	}
	
	
}
