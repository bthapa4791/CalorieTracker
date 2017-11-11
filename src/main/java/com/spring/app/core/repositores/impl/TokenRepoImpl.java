package com.spring.app.core.repositores.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.core.entities.Token;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.TokenRepo;

@Repository
@Transactional
public class TokenRepoImpl implements TokenRepo{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void save(Token token) {
		em.persist(token);
	}
	
	public void update(Token token) {
		em.merge(token);
	}

	@Override
	public void delete(String string) {
		Token token = new Token();
		token.setSeries(string);
		em.remove(em.contains(token) ? token : em.merge(token));
	}

	@Override
	public Token findOne(String presentedSeries) {
		List<Token> token = em.createNamedQuery(Token.FIND_BY_TOKENSERIES, Token.class)
                .setParameter("series", presentedSeries)
                .getResultList();

        return token.size() == 1 ? token.get(0) : null;
	}

	@Override
	public List<Token> findAll() {
		Query query = em.createQuery("SELECT t FROM Token t");
		return query.getResultList();
	}
}
