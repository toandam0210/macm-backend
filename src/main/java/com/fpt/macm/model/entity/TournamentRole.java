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
@Table(name = "tournament_role")
public class TournamentRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "role_event_id")
	private RoleEvent roleEvent;

	@ManyToOne(optional = false)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;

	@Column
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public RoleEvent getRoleEvent() {
		return roleEvent;
	}

	public void setRoleEvent(RoleEvent roleEvent) {
		this.roleEvent = roleEvent;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public void setTournament(Tournament tournament) {
		this.tournament = tournament;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

}
