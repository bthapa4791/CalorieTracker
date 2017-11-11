package com.spring.app.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.app.core.entities.User;

public class UserList {
	
	private List<User> user = new ArrayList<User>();

	public UserList(List<User> list) {
		this.user = list;
	}
	
	public List<User> getUserList() {
		return user;
	}

	public void setUserList(List<User> userList) {
		this.user = userList;
	}
	
}
