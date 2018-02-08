package com.lelasoft.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lelasoft.tools.Clinics;
import com.lelasoft.tools.DoctorGrade;
import com.lelasoft.tools.Hospitals;

/**
 * Created by KhaledLela on 10/28/15.
 */
@Entity
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L; 
	@Id
	@GeneratedValue
    private Long id;
    private String name;
    private String address;
    private String avatar;
    private float rate;
    private int totalRates;
    @Lob 
    @Column(length=1024)
    private String cv;
    @Enumerated(EnumType.STRING)
    private DoctorGrade grade;
    @Enumerated(EnumType.STRING)
    private Hospitals hospital;
    @Enumerated(EnumType.STRING)
    private Clinics clinic;
    @OneToMany(mappedBy="doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
 
	@JsonIgnore
    public List<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

	public int getTotalRates() {
		return totalRates;
	}

	public void setTotalRates(int totalRates) {
		this.totalRates = totalRates;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public DoctorGrade getGrade() {
		return grade;
	}

	public void setGrade(DoctorGrade grade) {
		this.grade = grade;
	}

	public Hospitals getHospital() {
		return hospital;
	}

	public void setHospital(Hospitals hospital) {
		this.hospital = hospital;
	}

	public Clinics getClinic() {
		return clinic;
	}

	public void setClinic(Clinics clinic) {
		this.clinic = clinic;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
