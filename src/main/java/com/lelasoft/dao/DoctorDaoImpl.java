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

import com.lelasoft.model.Doctor;
import com.lelasoft.tools.Clinics;
import com.lelasoft.tools.Hospitals;

@Repository
@Transactional
public class DoctorDaoImpl implements DoctorDao {
	@Autowired
	private EntityManager em;

	public Doctor findById(Long id) {
		return em.find(Doctor.class, id);
	}

	@Override
	public Doctor remove(Long id) {
		Doctor doctor = findById(id);
		em.remove(doctor);
		return doctor;
	}

	@Override
	public List<Doctor> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = cb.createQuery(Doctor.class);
		Root<Doctor> member = criteria.from(Doctor.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */
		criteria.select(member).orderBy(cb.asc(member.get("name")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void save(Doctor doctor) {
		em.persist(doctor);
	}

	@Override
	public Doctor update(Doctor doctor) {
		return em.merge(doctor);
	}

	@Override
	public List<Doctor> findAllByHospital(Hospitals hospital) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = builder.createQuery(Doctor.class);
		Root<Doctor> doctor = criteria.from(Doctor.class);
		criteria.select(doctor).where(
				builder.equal(doctor.get("hospital"), hospital)); 
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<Doctor> findDoctorByHospitalAndClinic(Hospitals hospital,Clinics clinic) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Doctor> criteria = builder.createQuery(Doctor.class);
		Root<Doctor> doctor = criteria.from(Doctor.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(doctor.get("hospital"), hospital));
		predicates.add(builder.equal(doctor.get("clinic"), clinic)); 
		// query itself
		criteria.select(doctor).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(criteria).getResultList();
	}
}
