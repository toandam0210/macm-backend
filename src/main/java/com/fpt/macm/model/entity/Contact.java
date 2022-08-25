package com.fpt.macm.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "contact")
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String clubName;

	@Column
	private String clubMail;

	@Column
	private String clubPhoneNumber;

	@Column(columnDefinition = "TEXT")
	private String image;

	@Column
	private LocalDate foundingDate;

	@Column
	private String fanpageUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClubName() {
		return clubName;
	}

	public void setClubName(String clubName) {
		this.clubName = clubName;
	}

	public String getClubMail() {
		return clubMail;
	}

	public void setClubMail(String clubMail) {
		this.clubMail = clubMail;
	}

	public String getClubPhoneNumber() {
		return clubPhoneNumber;
	}

	public void setClubPhoneNumber(String clubPhoneNumber) {
		this.clubPhoneNumber = clubPhoneNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDate getFoundingDate() {
		return foundingDate;
	}

	public void setFoundingDate(LocalDate foundingDate) {
		this.foundingDate = foundingDate;
	}

	public String getFanpageUrl() {
		return fanpageUrl;
	}

	public void setFanpageUrl(String fanpageUrl) {
		this.fanpageUrl = fanpageUrl;
	}

}
