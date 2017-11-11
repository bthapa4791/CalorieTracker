package com.spring.app.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class UserListResource extends ResourceSupport{

	private List<UserResource> user = new ArrayList<UserResource>();

	public List<UserResource> getUser() {
		return user;
	}

	public void setUser(List<UserResource> user) {
		this.user = user;
	}
	
}
