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

import com.lelasoft.model.HospitalTeam;
import com.lelasoft.model.Member;

@Repository
@Transactional
public class MemberDaoImpl implements MemberDao {
	@Autowired
	private EntityManager em;

	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

	public Member findByTeam(HospitalTeam team) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		criteria.select(member)
				.where(builder.equal(member.get("hospitalTeam"), team));
		return em.createQuery(criteria).getSingleResult();
	}
	
	public Member findByEmail(String email) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);

		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */

		criteria.select(member)
				.where(builder.equal(member.get("email"), email));
		return em.createQuery(criteria).getSingleResult();
	}
	public Member login(String username, String password) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = builder.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		// Constructing list of parameters
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(builder.equal(member.get("username"), username));
		predicates.add(builder.equal(member.get("password"), password));
		// query itself
		criteria.select(member).where(predicates.toArray(new Predicate[] {}));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<Member> findAllOrderedByName() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
		Root<Member> member = criteria.from(Member.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */
		criteria.select(member).orderBy(cb.asc(member.get("username")));
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public void register(Member member) {
		em.persist(member);
	}

	@Override
	public Member remove(Long id) {
		Member member = findById(id);
		em.remove(member);
		return member;
	}

	@Override
	public void updateMember(Member member) {
		em.merge(member);
	}
}
