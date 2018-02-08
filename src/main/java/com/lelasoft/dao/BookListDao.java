package com.lelasoft.dao;

import java.util.List;

import com.lelasoft.model.Appointment;
import com.lelasoft.model.BookingList;
import com.lelasoft.model.Member;

public interface BookListDao {

	public BookingList findById(Long id);
	public void save(List<BookingList> bl);
	public BookingList update(BookingList bl);
	public BookingList remove(Long id);
	public List<BookingList> findAll();
	public List<BookingList> findByAppointment(Appointment appointment); 
	public List<BookingList> findByMember(Member member); 
}
