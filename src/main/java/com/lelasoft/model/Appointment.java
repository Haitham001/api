package com.lelasoft.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by KhaledLela on 11/28/15.
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"doctor_id","appointDate"}) })
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@ManyToOne
    @JoinColumn(name="doctor_id")
	private Doctor doctor;
	private String appointDate;
	@OneToMany(mappedBy ="appointment", cascade = CascadeType.ALL)
	private List<BookingList> bookingList;	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Doctor getDoctor() {
		return doctor;
	}
	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
	public String getAppointDate() {
		return appointDate;
	}
	public void setAppointDate(String appointDate) {
		this.appointDate = appointDate;
	}
	
	@JsonIgnore
	public List<BookingList> getBookingList() {
		return bookingList;
	}
	public void setBookingList(List<BookingList> bookingList) {
		this.bookingList = bookingList;
	}
}
