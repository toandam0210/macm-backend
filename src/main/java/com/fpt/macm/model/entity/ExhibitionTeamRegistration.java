package com.fpt.macm.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "exhibition_team_registration")
public class ExhibitionTeamRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "exhibition_team_registration_id")
	private Set<ExhibitionPlayerRegistration> exhibitionPlayersRegistration;

	@Column
	private String teamName;

	@Column
	private String registerStatus;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Set<ExhibitionPlayerRegistration> getExhibitionPlayersRegistration() {
		return exhibitionPlayersRegistration;
	}

	public void setExhibitionPlayersRegistration(Set<ExhibitionPlayerRegistration> exhibitionPlayersRegistration) {
		this.exhibitionPlayersRegistration = exhibitionPlayersRegistration;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getRegisterStatus() {
		return registerStatus;
	}

	public void setRegisterStatus(String registerStatus) {
		this.registerStatus = registerStatus;
	}

}
