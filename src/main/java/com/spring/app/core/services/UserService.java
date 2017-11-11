package com.spring.app.core.services;

import com.spring.app.core.entities.User;
import com.spring.app.core.services.util.UserList;

public interface UserService {
	
	public User findUserByUsername(String username);
	public User createUser(User user);
	public void updateUserMaxCalories(String username, Long maxCaloriesPerDay);
	public UserList findAllUsers();
}
