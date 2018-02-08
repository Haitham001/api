package com.lelasoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lelasoft.dao.AppointmentDao;
import com.lelasoft.dao.BookListDao;
import com.lelasoft.dao.DoctorDao;
import com.lelasoft.model.Doctor;
import com.lelasoft.tools.BaseResponse;
import com.lelasoft.tools.Clinics;
import com.lelasoft.tools.Hospitals;
import com.lelasoft.tools.Util;

@Controller
@RequestMapping("/rest/doctor")
public class DoctorRestController {
	@Autowired
	private DoctorDao doctorDao;	
	@Autowired
	private AppointmentDao appDao;
	@Autowired
	private BookListDao bookDao;
	
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Doctor>> getDoctors() {
		BaseResponse<List<Doctor>> response = new BaseResponse<List<Doctor>>();
		try {
			List<Doctor> doctors = doctorDao.findAllOrderedByName();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of doctors!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(doctors);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load doctors, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{hospital}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Doctor>> getDoctorsByClinicId(
			@PathVariable("hospital") Hospitals hospital) {
		BaseResponse<List<Doctor>> response = new BaseResponse<List<Doctor>>();
		try {
			List<Doctor> doctors = doctorDao.findAllByHospital(hospital);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of doctors!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(doctors);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load doctors, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{hospital}/{clinic}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Doctor>> getDoctorsByClinicIdAndSpecilty(
			@PathVariable("hospital") Hospitals hospital,
			@PathVariable("clinic") Clinics clinic) {
		BaseResponse<List<Doctor>> response = new BaseResponse<List<Doctor>>();
		try {
			List<Doctor> doctors = doctorDao.findDoctorByHospitalAndClinic(hospital, clinic);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of doctors!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(doctors);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load doctors, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
	
	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Doctor> addDoctor(
			@RequestBody Doctor doctor) {
		BaseResponse<Doctor> response = new BaseResponse<Doctor>();
		try {
			BaseResponse.Head head = new BaseResponse.Head();
			if (doctor.getAvatar() != null) {
				String avatar = Util.updateAvatar(doctor.getAvatar());
				doctor.setAvatar(avatar);
			} 
			doctorDao.save(doctor);
			head.setPrint(true);
			head.setStatus(1);
			head.setMessage(doctor.getName() + " has been added successfully");
			response.setData(doctor);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add doctor,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Doctor> updateDoctor(
			@RequestBody Doctor doctor) {
		BaseResponse<Doctor> response = new BaseResponse<Doctor>();
		try {
			response.setData(doctorDao.update(doctor));
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Doctor " + doctor.getName() + " has been updated successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't update doctor. >>" + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody BaseResponse<Doctor> removedoctorById(
			@PathVariable("id") Long id) {
		BaseResponse<Doctor> response = new BaseResponse<Doctor>();
		try {
			Doctor doctor = doctorDao.remove(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Doctor " + doctor.getName() + " has been deleted!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(doctor);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't delete doctor!, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}
