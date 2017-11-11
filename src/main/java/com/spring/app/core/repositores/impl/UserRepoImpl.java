package com.spring.app.core.repositores.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.UserRepo;
import com.spring.app.core.services.util.UserList;

@Repository
public class UserRepoImpl implements UserRepo{

	@PersistenceContext
	private EntityManager em;
	
	public User createUser(User user) {
//		User usr = new User();
//		usr.setUsername(user.getUsername());
//		usr.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//		usr.setEmail(user.getEmail());
//		usr.setAuthorities(user.getAuthorities());
		em.persist(user);
		return user;
	}

	public UserList findAllUsers() {
		Query query = em.createQuery("SELECT a FROM User a");
		return new UserList(query.getResultList());
	}
	
	@Override
	public User findUserByUsername(String username) {
		 List<User> users = em.createNamedQuery(User.FIND_BY_USERNAME, User.class)
	                .setParameter("username", username)
	                .getResultList();

	        return users.size() == 1 ? users.get(0) : null;
	}

}
