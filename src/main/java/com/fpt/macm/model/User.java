package com.fpt.macm.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "`user`")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String studentId;

	@Column
	private String name;

	@Column
	private boolean gender;

	@Column
	private LocalDateTime dateOfBirth;

	@Column
	private String email;

	@Column
	private String image;

	@Column
	private String phone;

	@Column
	private boolean isActive;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<AttendanceStatus> attendanceStatus = new HashSet<AttendanceStatus>();
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "roleId")
	private Role role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<MemberEvent> membersEvent  = new HashSet<MemberEvent>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<EventFeeStatus>  eventFeeStatus  = new HashSet<EventFeeStatus>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<MembershipStatus> membershipStatus   = new HashSet<MembershipStatus>();

	@Column
	private String createdBy;

	@Column
	private LocalDateTime createdOn;

	@Column
	private String updatedBy;

	@Column
	private LocalDateTime updatedOn;
	
	@Column
	private boolean isAdmin;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Set<AttendanceStatus> getAttendanceStatus() {
		return attendanceStatus;
	}

	public void setAttendanceStatus(Set<AttendanceStatus> attendanceStatus) {
		this.attendanceStatus = attendanceStatus;
	}

	public Set<MemberEvent> getMembersEvent() {
		return membersEvent;
	}

	public void setMembersEvent(Set<MemberEvent> membersEvent) {
		this.membersEvent = membersEvent;
	}

	public Set<EventFeeStatus> getEventFeeStatus() {
		return eventFeeStatus;
	}

	public void setEventFeeStatus(Set<EventFeeStatus> eventFeeStatus) {
		this.eventFeeStatus = eventFeeStatus;
	}

	public Set<MembershipStatus> getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(Set<MembershipStatus> membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
	
	
	

}
