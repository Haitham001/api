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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lelasoft.tools.Gender;
import com.lelasoft.tools.Membership;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "email"),
		@UniqueConstraint(columnNames = "username") })
public class Member implements Serializable {
	/** Default value included to remove warning. Remove or modify at will. **/
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	@NotEmpty
	@Size(min = 3, max = 25)
	private String username;
	@NotNull
	private String fname;
	@NotNull
	private String lname;
	@NotNull
	@NotEmpty
	@Email
	private String email;
	@NotNull
	@NotEmpty
	private String password;
	@NotNull
	@Size(min = 8, max = 12)
	@Digits(fraction = 0, integer = 12)
	@Column(name = "phone_number")
	private String phoneNumber;
	private String token;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String birthdate;
	@NotNull
	@Enumerated(EnumType.STRING)
	private Membership membership;
	private boolean canRate;
	@OneToOne(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name = "team_id")
    private HospitalTeam hospitalTeam;
	
	@OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
	private List<BookingList> bookingLists;
	
	public HospitalTeam getHospitalTeam() {
		return hospitalTeam;
	}

	public void setHospitalTeam(HospitalTeam hospitalTeam) {
		this.hospitalTeam = hospitalTeam;
	}

	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public boolean isCanRate() {
		return canRate;
	}

	public void setCanRate(boolean canRate) {
		this.canRate = canRate;
	}

	/**
	 * Ignore json
	 * 
	 * @return
	 */
	@JsonIgnore
	public List<BookingList> getBookingLists() {
		return bookingLists;
	}

	public void setBookingLists(List<BookingList> bookingLists) {
		this.bookingLists = bookingLists;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Member other = (Member) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}