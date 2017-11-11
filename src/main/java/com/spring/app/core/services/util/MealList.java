package com.spring.app.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.spring.app.core.entities.Meal;

public class MealList {

	private List<Meal> meals = new ArrayList<Meal>();
	
	public MealList(List<Meal> resultList) {
		this.meals = resultList;
	}

	public List<Meal> getMeals() {
		return meals;
	}

	public void setMeals(List<Meal> meals) {
		this.meals = meals;
	}

}
