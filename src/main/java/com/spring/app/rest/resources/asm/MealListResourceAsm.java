package com.spring.app.rest.resources.asm;

import java.util.List;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.spring.app.core.services.util.MealList;
import com.spring.app.rest.controller.MealController;
import com.spring.app.rest.resources.MealListResource;
import com.spring.app.rest.resources.MealResource;
import com.spring.app.rest.resources.UserResource;

public class MealListResourceAsm extends ResourceAssemblerSupport<MealList, MealListResource>{
	
	public MealListResourceAsm() {
		super(MealController.class, MealListResource.class);
	}

	@Override
	public MealListResource toResource(MealList mealList) {
		List<MealResource> resources = new MealResourceAsm().toResources(mealList.getMeals());
		MealListResource listMealRes = new MealListResource();
		listMealRes.setMealResource(resources);
		listMealRes.add(linkTo(MealController.class).slash(listMealRes.getId()).withSelfRel());
		return listMealRes;
	}
	
}
