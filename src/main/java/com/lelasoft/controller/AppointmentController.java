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
import com.lelasoft.dao.DoctorDao;
import com.lelasoft.model.Appointment;
import com.lelasoft.tools.BaseResponse;
import com.lelasoft.tools.Clinics;
import com.lelasoft.tools.Hospitals;

@Controller
@RequestMapping("/rest/appointment")
public class AppointmentController {
	@Autowired
	private AppointmentDao daDao;	
	@Autowired
	private DoctorDao drDao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Appointment>> getAppointments() {
		BaseResponse<List<Appointment>> response = new BaseResponse<List<Appointment>>();
		try {
			List<Appointment> doctors = daDao.findAll();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of Appointments!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(doctors);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load appointments, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Appointment>> getAppointmentByDocotor(
			@PathVariable("id") Long id) {
		BaseResponse<List<Appointment>> response = new BaseResponse<List<Appointment>>();
		try {
			List<Appointment> doctors = daDao.findAllByDoctor(drDao.findById(id));
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
	

	@RequestMapping(value = "/{appointment}/{hospital}/{clinic}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<Appointment>> getAppointmentsByDoctorAndDates(
			@PathVariable("appointment") String appointment,
			@PathVariable("hospital") Hospitals hospital,
			@PathVariable("clinic")Clinics clinic) {
		BaseResponse<List<Appointment>> response = new BaseResponse<List<Appointment>>();
		try {
			List<Appointment> doctors = daDao.findAllByDoctorAndDate(drDao.findDoctorByHospitalAndClinic(hospital, clinic),appointment);
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
	public @ResponseBody BaseResponse<Appointment> addAppointment(
			@RequestBody Appointment da) {
		BaseResponse<Appointment> response = new BaseResponse<Appointment>();
		try {
			BaseResponse.Head head = new BaseResponse.Head();
			daDao.save(da);
			head.setMessage("Appointemnt " + da.getAppointDate() + " has been saved succesfully!"); 
			head.setPrint(true);
			head.setStatus(1);
			response.setData(da);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't add da,  " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.PUT, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<Appointment> updateAppointment(
			@RequestBody Appointment da) {
		BaseResponse<Appointment> response = new BaseResponse<Appointment>();
		try {
			response.setData(daDao.update(da));
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Appointemnt " + da.getAppointDate() + " has been updated successfully");
			head.setPrint(true);
			head.setStatus(1);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't update da. >>" + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody BaseResponse<Appointment> removeAppointmentById(
			@PathVariable("id") Long id) {
		BaseResponse<Appointment> response = new BaseResponse<Appointment>();
		try {
			Appointment da = daDao.remove(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Appointement " + da.getAppointDate() + " has been deleted!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(da);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't delete da!, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}
