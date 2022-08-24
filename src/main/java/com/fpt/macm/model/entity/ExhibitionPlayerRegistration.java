package com.fpt.macm.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "exhibition_player_registration")
public class ExhibitionPlayerRegistration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "player_id")
	private TournamentPlayer tournamentPlayer;

	@Column
	private boolean roleInTeam;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TournamentPlayer getTournamentPlayer() {
		return tournamentPlayer;
	}

	public void setTournamentPlayer(TournamentPlayer tournamentPlayer) {
		this.tournamentPlayer = tournamentPlayer;
	}

	public boolean isRoleInTeam() {
		return roleInTeam;
	}

	public void setRoleInTeam(boolean roleInTeam) {
		this.roleInTeam = roleInTeam;
	}

}
