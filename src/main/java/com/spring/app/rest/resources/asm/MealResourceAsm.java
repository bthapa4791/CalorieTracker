package com.spring.app.rest.resources.asm;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.services.util.MealList;
import com.spring.app.rest.controller.MealController;
import com.spring.app.rest.controller.UserController;
import com.spring.app.rest.resources.MealResource;

public class MealResourceAsm extends ResourceAssemblerSupport<Meal, MealResource>{

	public MealResourceAsm() {
		super(MealController.class, MealResource.class);
	}

	@Override
	public MealResource toResource(Meal meal) {
		MealResource res = new MealResource();
		res.setRid(meal.getId());
		res.setDate(meal.getDate());
		res.setDescription(meal.getDescription());
		res.setCalories(meal.getCalories());
		res.add(linkTo(MealController.class).slash(meal.getId()).withSelfRel());
		res.add(linkTo(UserController.class).slash(meal.getUser().getUsername()).withRel("user"));
		return res;
	}
}
