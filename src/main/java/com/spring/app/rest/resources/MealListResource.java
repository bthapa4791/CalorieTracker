package com.spring.app.rest.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.hateoas.ResourceSupport;

public class MealListResource extends ResourceSupport{

	private List<MealResource> mealResource = new ArrayList<MealResource>();

	public List<MealResource> getMealResource() {
		return mealResource;
	}

	public void setMealResource(List<MealResource> mealResource) {
		this.mealResource = mealResource;
	}
	
	
}
