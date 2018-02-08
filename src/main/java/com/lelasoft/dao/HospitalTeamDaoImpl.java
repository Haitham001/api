package com.lelasoft.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lelasoft.model.HospitalTeam;
import com.lelasoft.model.Member;
import com.lelasoft.tools.Hospitals;

@Repository
@Transactional
public class HospitalTeamDaoImpl implements HospitalTeamDao {
	@Autowired
	private EntityManager em;

	public HospitalTeam findById(Long id) {
		return em.find(HospitalTeam.class, id);
	}

	@Override
	public HospitalTeam remove(Long id) {
		HospitalTeam obj = findById(id);
		em.remove(obj);
		return obj;
	}

	@Override
	public List<HospitalTeam> findAll() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<HospitalTeam> criteria = cb.createQuery(HospitalTeam.class);
		Root<HospitalTeam> teams = criteria.from(HospitalTeam.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(teams).orderBy(cb.asc(teams.get(Member_.name)));
		 */
		criteria.select(teams);
		return em.createQuery(criteria).getResultList();
	}

	@Override
	public HospitalTeam save(HospitalTeam team) {
        em.persist(team);
        return team;
	}

	@Override
	public HospitalTeam update(HospitalTeam team) {
        return em.merge(team);
	}

	public HospitalTeam login(Member member){
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<HospitalTeam> criteria = builder.createQuery(HospitalTeam.class);
		Root<HospitalTeam> teams = criteria.from(HospitalTeam.class);
		// Constructing list of parameters
//		List<Predicate> predicates = new ArrayList<Predicate>();
//		predicates.add(builder.equal(teams.get("username"), username));
//		predicates.add(builder.equal(teams.get("password"), password));
//		// query itself
//		criteria.select(teams).where(predicates.toArray(new Predicate[] {}));
		criteria.select(teams).where(
				builder.equal(teams.get("member"), member));
		return em.createQuery(criteria).getSingleResult();
	}

	@Override
	public List<HospitalTeam> findAllByHospital(Hospitals hospital) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<HospitalTeam> criteria = builder.createQuery(HospitalTeam.class);
		Root<HospitalTeam> team = criteria.from(HospitalTeam.class);
		criteria.select(team).where(
				builder.equal(team.get("hospital"), hospital));
		return em.createQuery(criteria).getResultList();
	}
}
