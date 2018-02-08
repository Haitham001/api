package com.lelasoft.dao;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.lelasoft.model.BookingList;
import com.lelasoft.model.Member;

@Repository
@Transactional
public class BookDaoImpl implements BookListDao {
	@Autowired
	private EntityManager em;

	public BookingList findById(Long id) {
		return em.find(BookingList.class, id);
	}

	@Override
	public BookingList remove(Long id) {
		BookingList clinic = findById(id);
		em.remove(clinic);
		return clinic;
	}

	@Override
	public List<BookingList> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<BookingList> criteria = cb.createQuery(BookingList.class);
		Root<BookingList> books = criteria.from(BookingList.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */
		criteria.select(books).orderBy(cb.asc(books.get("appointTime")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void save(List<BookingList> obj) {
		Iterator<BookingList> iter = obj.iterator();
		while (iter.hasNext()) {
			BookingList bookingList = (BookingList) iter.next();
			em.persist(bookingList);
		}
	}

	@Override
	public BookingList update(BookingList clinic) {
		return em.merge(clinic);
	}

	@Override
	public List<BookingList> findByAppointment(Appointment appointment) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BookingList> criteria = builder
				.createQuery(BookingList.class);
		Root<BookingList> da = criteria.from(BookingList.class);
		criteria.select(da).where(
				builder.equal(da.get("appointment"), appointment));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public List<BookingList> findByMember(Member member) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BookingList> criteria = builder
				.createQuery(BookingList.class);
		Root<BookingList> teams = criteria.from(BookingList.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(teams.get("patient"), member));
		// query itself
		criteria.select(teams).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(criteria).getResultList();
	}
}
