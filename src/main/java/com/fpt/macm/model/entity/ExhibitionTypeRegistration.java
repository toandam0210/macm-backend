package com.fpt.macm.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exhibition_type_registration")
public class ExhibitionTypeRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "exhibition_type_id")
	private ExhibitionType exhibitionType;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "exhibition_team_registration_id", referencedColumnName = "id")
	private ExhibitionTeamRegistration exhibitionTeamRegistration;

	@Column
	private String registerStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExhibitionType getExhibitionType() {
		return exhibitionType;
	}

	public void setExhibitionType(ExhibitionType exhibitionType) {
		this.exhibitionType = exhibitionType;
	}

	public ExhibitionTeamRegistration getExhibitionTeamRegistration() {
		return exhibitionTeamRegistration;
	}

	public void setExhibitionTeamRegistration(ExhibitionTeamRegistration exhibitionTeamRegistration) {
		this.exhibitionTeamRegistration = exhibitionTeamRegistration;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

}
