package com.spring.app.core.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.UserRepo;
import com.spring.app.core.services.UserService;
import com.spring.app.core.services.util.UserList;
import com.spring.app.exception.util.ConflictException;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	public UserList findAllUsers() {
		return userRepo.findAllUsers();
	}
	
	@Override
	public User findUserByUsername(String username) {
		return userRepo.findUserByUsername(username);
	}

	@Override
	public User createUser(User user) {
		User usr = findUserByUsername(user.getUsername());
		if (usr != null) {
			throw new ConflictException("User exists with name: " + usr.getUsername());
		}
		return  userRepo.createUser(user);
	}

	@Override
	public void updateUserMaxCalories(String username, Long maxCaloriesPerDay) {
		// TODO Auto-generated method stub
		
	}
}
