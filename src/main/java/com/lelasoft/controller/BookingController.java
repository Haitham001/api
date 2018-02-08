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
import com.lelasoft.dao.MemberDao;
import com.lelasoft.model.BookingList;
import com.lelasoft.model.Member;
import com.lelasoft.tools.BaseResponse;
import com.lelasoft.tools.Util;

@Controller
@RequestMapping("/rest/booking")
public class BookingController {
	@Autowired
	private BookListDao daDao;
	@Autowired
	private AppointmentDao appDao;
	@Autowired
	private MemberDao memberDao;

	@Autowired
	private DoctorDao drDao;

	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<BookingList>> findAll() {
		BaseResponse<List<BookingList>> response = new BaseResponse<List<BookingList>>();
		try {
			List<BookingList> bookList = daDao.findAll();
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of bookList!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(bookList);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load bookList, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<BookingList>> getbookListByAppointment(
			@PathVariable("id") Long appointId) {
		BaseResponse<List<BookingList>> response = new BaseResponse<List<BookingList>>();
		try {
			List<BookingList> bookList = daDao.findByAppointment(appDao
					.findById(appointId));
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of bookList!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(bookList);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load bookList, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	@RequestMapping(method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseResponse<List<BookingList>> addBookingList(
			@RequestBody List<BookingList> da) {
		BaseResponse<List<BookingList>> response = new BaseResponse<List<BookingList>>();
		try {
			BaseResponse.Head head = new BaseResponse.Head();
			daDao.save(da);
			head.setMessage("Appointment times saves successfully!");
			head.setPrint(true);
			head.setStatus(1);
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
	public @ResponseBody BaseResponse<Void> updateBookList(
			@RequestBody BookingList bookList) {
		BaseResponse<Void> response = new BaseResponse<Void>();
		try {
			daDao.update(bookList);		
			if (bookList.getPatient() != null &&  bookList.getTeam() != null) {
				String title ;
				String doctorName = bookList.getAppointment().getDoctor().getName();
				/**
				 * Get Hospital team from Member table by hospital id.
				 */
				Member team = memberDao.findByTeam(bookList.getTeam());
				Member patient = bookList.getPatient();
				String message = "Doctor Appointment : "
						+ bookList.getAppointment().getAppointDate() + " "
						+ bookList.getAppointTime() + " for doctor : " + doctorName;
				
				if(bookList.isBooked() && !bookList.isOpenRate() && bookList.isRated()){
					title = patient.getFname()
							+ "Booked Appointment!";
					Util.sendMulptipleNotifiaction(title, message, team.getToken());
				}else if(bookList.isOpenRate() && !bookList.isRated()){
					title = team.getFname()
							+ " sent you rate request!";
					Util.sendMulptipleNotifiaction(title, message, patient.getToken());
				}else if(bookList.isRated()){
					title = patient.getFname()
							+ " rate doctor " + doctorName;
					Util.sendMulptipleNotifiaction(title, message, team.getToken());
				}
			}	
			
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage(bookList.getAppointTime()
					+ " has been updated successfully");
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
	public @ResponseBody BaseResponse<BookingList> removeAppointmentById(
			@PathVariable("id") Long id) {
		BaseResponse<BookingList> response = new BaseResponse<BookingList>();
		try {
			BookingList da = daDao.remove(id);
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("da" + da.getAppointTime() + " has been deleted!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(da);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't delete appointemnt!, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}

	/**
	 * Patient part !!!...
	 * 
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value = "/patient/{memberId}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody BaseResponse<List<BookingList>> getDoctorAvailableForRateByPatient(
			@PathVariable("memberId") Long memberId) {
		BaseResponse<List<BookingList>> response = new BaseResponse<List<BookingList>>();
		try {
			List<BookingList> bookList = daDao.findByMember(memberDao
					.findById(memberId));
			// for (BookingList bl : bookList) {
			// if (bl.isOpenRate() && !bl.isRated()) {
			// drOpenedForRate.add(drDao.findById(appDao
			// .findById(bl.getAppointment().getId()).getDoctor()
			// .getId()));
			// }
			// }
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("List of bookList!");
			head.setPrint(true);
			head.setStatus(1);
			response.setData(bookList);
			response.setHead(head);
		} catch (Exception e) {
			BaseResponse.Head head = new BaseResponse.Head();
			head.setMessage("Can't load bookList, " + e.getMessage());
			head.setPrint(true);
			head.setStatus(-1);
			response.setHead(head);
		}
		return response;
	}
}
