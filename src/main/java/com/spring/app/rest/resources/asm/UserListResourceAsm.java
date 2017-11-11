package com.spring.app.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.spring.app.core.services.util.UserList;
import com.spring.app.rest.controller.UserController;
import com.spring.app.rest.resources.UserListResource;
import com.spring.app.rest.resources.UserResource;

public class UserListResourceAsm extends ResourceAssemblerSupport<UserList, UserListResource>{

	public UserListResourceAsm() {
		super(UserController.class, UserListResource.class);
	}

	@Override
	public UserListResource toResource(UserList userList) {
		List<UserResource> userRes = new UserResourceAsm().toResources(userList.getUserList());
		UserListResource userResList = new UserListResource();
		userResList.setUser(userRes);
		return userResList;
	}
}
