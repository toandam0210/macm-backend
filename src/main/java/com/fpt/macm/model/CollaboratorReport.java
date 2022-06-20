package com.fpt.macm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "collaborator_report")
public class CollaboratorReport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(mappedBy = "collaboratorReport")
    private Semester semester;
	
	@Column
	private int numberJoin;
	
	@Column
	private int numberPassed;
	
	@Column
	private int numberNotPassed;
	
	@Column
	private int numberMale;
	
	@Column
	private int numberFemale;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public int getNumberJoin() {
		return numberJoin;
	}

	public void setNumberJoin(int numberJoin) {
		this.numberJoin = numberJoin;
	}

	public int getNumberPassed() {
		return numberPassed;
	}

	public void setNumberPassed(int numberPassed) {
		this.numberPassed = numberPassed;
	}

	public int getNumberNotPassed() {
		return numberNotPassed;
	}

	public void setNumberNotPassed(int numberNotPassed) {
		this.numberNotPassed = numberNotPassed;
	}

	public int getNumberMale() {
		return numberMale;
	}

	public void setNumberMale(int numberMale) {
		this.numberMale = numberMale;
	}

	public int getNumberFemale() {
		return numberFemale;
	}

	public void setNumberFemale(int numberFemale) {
		this.numberFemale = numberFemale;
	}
	
	
}
