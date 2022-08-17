package com.fpt.macm.model.entity;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	private String semester;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<CompetitiveType> competitiveTypes;

	@OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<ExhibitionType> exhibitionTypes;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "tournament_id")
	private Set<TournamentPlayer> tournamentPlayers;

	@Column
	private double totalAmount;

	@Column
	private double feePlayerPay;
	
	@Column
	private double feeOrganizingCommiteePay;

	@Column
	private double totalAmountEstimate;

	@Column
	private double totalAmountFromClubEstimate;

	@Column
	private double totalAmountFromClubActual;

	@Column
	private LocalDateTime registrationPlayerDeadline;

	@Column
	private LocalDateTime registrationOrganizingCommitteeDeadline;
	
	@Column
	private boolean status;
	
	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;

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

	public double getFeeOrganizingCommiteePay() {
		return feeOrganizingCommiteePay;
	}

	public void setFeeOrganizingCommiteePay(double feeOrganizingCommiteePay) {
		this.feeOrganizingCommiteePay = feeOrganizingCommiteePay;
	}

	public double getFeePlayerPay() {
		return feePlayerPay;
	}

	public void setFeePlayerPay(double feePlayerPay) {
		this.feePlayerPay = feePlayerPay;
	}

	public double getTotalAmountEstimate() {
		return totalAmountEstimate;
	}

	public void setTotalAmountEstimate(double totalAmountEstimate) {
		this.totalAmountEstimate = totalAmountEstimate;
	}

	public double getTotalAmountFromClubEstimate() {
		return totalAmountFromClubEstimate;
	}

	public void setTotalAmountFromClubEstimate(double totalAmountFromClubEstimate) {
		this.totalAmountFromClubEstimate = totalAmountFromClubEstimate;
	}

	public double getTotalAmountFromClubActual() {
		return totalAmountFromClubActual;
	}

	public void setTotalAmountFromClubActual(double totalAmountFromClubActual) {
		this.totalAmountFromClubActual = totalAmountFromClubActual;
	}

	public LocalDateTime getRegistrationPlayerDeadline() {
		return registrationPlayerDeadline;
	}

	public void setRegistrationPlayerDeadline(LocalDateTime registrationPlayerDeadline) {
		this.registrationPlayerDeadline = registrationPlayerDeadline;
	}

	public LocalDateTime getRegistrationOrganizingCommitteeDeadline() {
		return registrationOrganizingCommitteeDeadline;
	}

	public void setRegistrationOrganizingCommitteeDeadline(LocalDateTime registrationOrganizingCommitteeDeadline) {
		this.registrationOrganizingCommitteeDeadline = registrationOrganizingCommitteeDeadline;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
