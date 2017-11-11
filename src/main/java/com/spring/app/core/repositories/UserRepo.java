package com.spring.app.core.repositories;

import com.spring.app.core.entities.User;
import com.spring.app.core.services.util.UserList;

public interface UserRepo {
	
	public User findUserByUsername(String username);
	public User createUser(User user);
	public UserList findAllUsers();
}
