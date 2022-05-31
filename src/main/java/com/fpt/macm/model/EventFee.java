package com.fpt.macm.model;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event_fee")
public class EventFee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "eventId")
	private Event event;
	
	@Column
	private double totalAmount;
	
	@OneToMany(mappedBy = "eventFee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<EventFeeStatus> eventFeeStatus = new HashSet<EventFeeStatus>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Set<EventFeeStatus> getEventFeeStatus() {
		return eventFeeStatus;
	}

	public void setEventFeeStatus(Set<EventFeeStatus> eventFeeStatus) {
		this.eventFeeStatus = eventFeeStatus;
	}
	
	
}
