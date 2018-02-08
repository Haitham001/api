package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.Doctor;
import com.lelasoft.tools.Clinics;
import com.lelasoft.tools.Hospitals;

public interface DoctorDao {

	public Doctor findById(Long id);

	public void save(Doctor doctor);

	public Doctor update(Doctor doctor);

	public Doctor remove(Long id);

	public List<Doctor> findAllOrderedByName();

	List<Doctor> findDoctorByHospitalAndClinic(Hospitals hospital, Clinics clinic);

	List<Doctor> findAllByHospital(Hospitals hospital); 
}
