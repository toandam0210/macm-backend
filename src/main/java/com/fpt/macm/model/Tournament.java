package com.fpt.macm.model;

import java.time.LocalDateTime;
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
@Table(name = "tournament")
public class Tournament {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private int maxQuantityComitee;

	@Column
	private double amount_per_register;

	@Column
	private String semester;

	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<CompetitiveType> competitiveTypes;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<ExhibitionType> exhibitionTypes;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<TournamentPlayer> tournamentPlayers;
	
	@Column
	private double totalAmount;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getMaxQuantityComitee() {
		return maxQuantityComitee;
	}

	public void setMaxQuantityComitee(int maxQuantityComitee) {
		this.maxQuantityComitee = maxQuantityComitee;
	}

	public double getAmount_per_register() {
		return amount_per_register;
	}

	public void setAmount_per_register(double amount_per_register) {
		this.amount_per_register = amount_per_register;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Set<CompetitiveType> getCompetitiveTypes() {
		return competitiveTypes;
	}

	public void setCompetitiveTypes(Set<CompetitiveType> competitiveTypes) {
		this.competitiveTypes = competitiveTypes;
	}

	public Set<ExhibitionType> getExhibitionTypes() {
		return exhibitionTypes;
	}

	public void setExhibitionTypes(Set<ExhibitionType> exhibitionTypes) {
		this.exhibitionTypes = exhibitionTypes;
	}

	public Set<TournamentPlayer> getTournamentPlayers() {
		return tournamentPlayers;
	}

	public void setTournamentPlayers(Set<TournamentPlayer> tournamentPlayers) {
		this.tournamentPlayers = tournamentPlayers;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
	

}
