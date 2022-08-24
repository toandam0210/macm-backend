package com.fpt.macm.model.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "exhibition_type_registration_id")
	private Set<ExhibitionTeamRegistration> exhibitionTeamsRegistration;

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

	public Set<ExhibitionTeamRegistration> getExhibitionTeamsRegistration() {
		return exhibitionTeamsRegistration;
	}

	public void setExhibitionTeamsRegistration(Set<ExhibitionTeamRegistration> exhibitionTeamsRegistration) {
		this.exhibitionTeamsRegistration = exhibitionTeamsRegistration;
	}

}
