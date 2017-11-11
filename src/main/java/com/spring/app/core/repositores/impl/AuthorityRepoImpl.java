package com.spring.app.core.repositores.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.core.entities.Authority;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.AuthorityRepo;

@Repository
@Transactional
public class AuthorityRepoImpl implements AuthorityRepo{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void saveAuthority(String[] authority) {
		for (String string : authority) {
			Authority autho = new Authority();
			autho.setName(string);
			em.persist(autho);
		}
	}

	@Override
	public Authority findById(Integer id) {
		 List<Authority> authorities = em.createNamedQuery(Authority.FIND_BY_AUTHORITY_ID, Authority.class)
	                .setParameter("id", id)
	                .getResultList();

	        return authorities.size() == 1 ? authorities.get(0) : null;

	}

}
