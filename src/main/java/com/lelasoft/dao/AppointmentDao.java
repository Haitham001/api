package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.Appointment;
import com.lelasoft.model.Doctor;

public interface AppointmentDao {

	public Appointment findById(Long id);

	public void save(Appointment doctor);

	public Appointment update(Appointment doctor);

	public Appointment remove(Long id);

	public List<Appointment> findAll();

	public List<Appointment> findAllByDoctor(Doctor doctor);

	public Appointment findByDate(String date);

	List<Appointment> findAllByDoctorAndDate(List<Doctor> doctor, String date);  
}
