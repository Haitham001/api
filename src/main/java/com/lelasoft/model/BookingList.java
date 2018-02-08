package com.lelasoft.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by KhaledLela on 11/27/15.
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"appointment_id","patient_id"}) })
public class BookingList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@ManyToOne
    @JoinColumn(name="patient_id")
    private Member patient;
    @ManyToOne
    @JoinColumn(name="appointment_id")
    private Appointment appointment;
    private String appointTime;
    private boolean booked;
    private boolean openRate;
    private boolean rated;	
    
    @ManyToOne
    @JoinColumn(name="team_id")
    private HospitalTeam team;
    
	public HospitalTeam getTeam() {
		return team;
	}
	public void setTeam(HospitalTeam team) {
		this.team = team;
	}

	public Appointment getAppointment() {
		return appointment;
	}
	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Member getPatient() {
		return patient;
	}
	public void setPatient(Member patient) {
		this.patient = patient;
	}
	public String getAppointTime() {
		return appointTime;
	}
	public void setAppointTime(String appointTime) {
		this.appointTime = appointTime;
	}
	public boolean isBooked() {
		return booked;
	}
	public void setBooked(boolean booked) {
		this.booked = booked;
	}
	public boolean isOpenRate() {
		return openRate;
	}
	public void setOpenRate(boolean openRate) {
		this.openRate = openRate;
	}
	public boolean isRated() {
		return rated;
	}
	public void setRated(boolean rated) {
		this.rated = rated;
	}
}
