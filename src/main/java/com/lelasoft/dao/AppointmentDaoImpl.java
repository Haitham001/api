package com.lelasoft.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lelasoft.model.Appointment;
import com.lelasoft.model.Doctor;

@Repository
@Transactional
public class AppointmentDaoImpl implements AppointmentDao {
	@Autowired
	private EntityManager em;

	public Appointment findById(Long id) {
		return em.find(Appointment.class, id);
	}

	@Override
	public Appointment remove(Long id) {
		Appointment doctor = findById(id);
		em.remove(doctor);
		return doctor;
	}

	@Override
	public List<Appointment> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Appointment> criteria = cb.createQuery(Appointment.class);
		Root<Appointment> appointment = criteria.from(Appointment.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */
		criteria.select(appointment).orderBy(cb.asc(appointment.get("appointDate")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public Appointment findByDate(String appointDate) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Appointment> criteria = builder.createQuery(Appointment.class);
		Root<Appointment> da = criteria.from(Appointment.class);
		criteria.select(da).where(
				builder.equal(da.get("appointDate"), appointDate));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public void save(Appointment doctor) {
		em.persist(doctor);
	}

	@Override	
	public Appointment update(Appointment doctor) {
		return em.merge(doctor);
	}

	@Override
	public List<Appointment> findAllByDoctor(Doctor doctor) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Appointment> criteria = builder.createQuery(Appointment.class);
		Root<Appointment> da = criteria.from(Appointment.class);
		criteria.select(da).where(
				builder.equal(da.get("doctor"), doctor));
		return em.createQuery(criteria).getResultList();
	}
	
	@Override
	public List<Appointment> findAllByDoctorAndDate(List<Doctor> doctors,String date) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Appointment> criteria = builder.createQuery(Appointment.class);
		Root<Appointment> appointments = criteria.from(Appointment.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(appointments.get("doctor").in(doctors));
		predicates.add(builder.equal(appointments.get("appointDate"), date));
		// query itself
		criteria.select(appointments).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(criteria).getResultList();
	}
}
