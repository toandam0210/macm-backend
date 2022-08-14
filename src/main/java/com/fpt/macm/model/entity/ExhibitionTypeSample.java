package com.fpt.macm.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "exhibition_type_sample")
public class ExhibitionTypeSample {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
