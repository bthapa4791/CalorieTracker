package com.spring.app.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import com.spring.app.core.entities.User;
import com.spring.app.rest.controller.MealController;
import com.spring.app.rest.controller.UserController;
import com.spring.app.rest.resources.UserResource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource>{

	public UserResourceAsm() {
		super(UserController.class, UserResource.class);
	}

	@Override
	public UserResource toResource(User user) {
		UserResource res = new UserResource();
		res.setUsername(user.getUsername());
		res.setPassword(user.getPassword());
		res.setEmail(user.getEmail());
		res.setAuthorities(user.getAuthorities());
		//res.setRole((user.getAuthorities()));
		if (user.getMeals().size() > 0) {
			res.setMeals(user.getMeals());
			res.add(linkTo(MealController.class).withRel("meals"));
		}
		res.add(linkTo(methodOn(UserController.class).getUserInfo(user.getUsername())).withSelfRel());
		return res;
	}

}
