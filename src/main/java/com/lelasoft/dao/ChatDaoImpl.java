package com.lelasoft.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lelasoft.model.ChatMessage;

@Repository
@Transactional
public class ChatDaoImpl implements ChatDao {
	@Autowired
	private EntityManager em;

	@Override
	public void save(ChatMessage chat) {
		em.persist(chat);
	}

	@Override
	public List<ChatMessage> findChats(Long autor,Long opponent) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
		Root<ChatMessage> from = cq.from(ChatMessage.class);
		cq.select(from);
		cq.where(
				cb.or(
				cb.and(cb.equal(from.get("autor"), autor),
						cb.equal(from.get("opponent"), opponent)),
				cb.and(cb.equal(from.get("autor"), opponent),
						cb.equal(from.get("opponent"), autor))));

		cq.orderBy(cb.desc(from.get("id")));
		return em.createQuery(cq).setMaxResults(10) // limit
				.getResultList();
	}
	
	@Override
	public List<ChatMessage> findChats(Long id) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
		Root<ChatMessage> from = cq.from(ChatMessage.class);
		cq.select(from);
		cq.where(cb.or(cb.equal(from.get("autor"), id),
						cb.equal(from.get("opponent"), id)));

		// Here is the trick!
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(cb.or(cb.equal(from.get("autor"), id),
				cb.equal(from.get("opponent"), id)));
		cq.where(predicates.toArray(new Predicate[] {}));
		cq.groupBy(predicates.toArray(new Predicate[] {}));
		cq.orderBy(cb.desc(from.get("id")));
		return em.createQuery(cq)
				.getResultList();
	}

	@Override
	public List<ChatMessage> findChats(Long user, Long opp, Long msgid,boolean top) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChatMessage> cq = cb.createQuery(ChatMessage.class);
		Root<ChatMessage> from = cq.from(ChatMessage.class);
		ParameterExpression<Long> balance = cb.parameter(Long.class);
		cq.select(from);

		// Here is the trick!
		List<Predicate> predicates = new ArrayList<Predicate>();
		if (top)
			predicates.add(cb.lt(from.<Integer> get("id"), balance));
		else
			predicates.add(cb.gt(from.<Integer> get("id"), balance));

		predicates.add(cb.or(
				cb.and(cb.equal(from.get("autor"), user),
						cb.equal(from.get("opponent"), opp)),
				cb.and(cb.equal(from.get("autor"), opp),
						cb.equal(from.get("opponent"), user))));
		cq.where(predicates.toArray(new Predicate[] {}));

		// cq.orderBy(cb.asc(from.get("createdDate")));

		TypedQuery<ChatMessage> query = em.createQuery(cq);

		query.setParameter(balance, msgid);
		query.setMaxResults(10);
		return query.getResultList();
	}

	@Override
	public List<ChatMessage> findAllChats() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ChatMessage> criteria = cb.createQuery(ChatMessage.class);
		Root<ChatMessage> chats = criteria.from(ChatMessage.class);
		/*
		 * Swap criteria statements if you would like to try out type-safe
		 * criteria queries, a new feature in JPA 2.0
		 * criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
		 */
		criteria.select(chats).orderBy(cb.asc(chats.get("createdDate")));
		return em.createQuery(criteria).getResultList();
	}
}
