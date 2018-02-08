package com.lelasoft.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lelasoft.tools.HospitalType;
import com.lelasoft.tools.Hospitals;

/**
 * Created by KhaledLela on 11/27/15.
 */
@Entity
public class HospitalTeam implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
    @Enumerated(EnumType.STRING)
	private HospitalType hospitalType;
    @Enumerated(EnumType.STRING)
	private Hospitals hospital;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "hospitalTeam")
	private Member member;
	@OneToMany(mappedBy = "team")
	private List<BookingList> bookingList;

	@JsonIgnore
	public List<BookingList> getBookingList() {
		return bookingList;
	}

	public void setBookingList(List<BookingList> bookingList) {
		this.bookingList = bookingList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
	
	public Hospitals getHospital() {
		return hospital;
	}

	public void setHospital(Hospitals hospital) {
		this.hospital = hospital;
	}

	public HospitalType getHospitalType() {
		return hospitalType;
	}

	public void setHospitalType(HospitalType hospitalType) {
		this.hospitalType = hospitalType;
	}
}
