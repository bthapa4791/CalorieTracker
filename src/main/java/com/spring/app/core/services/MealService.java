package com.spring.app.core.services;

import java.util.List;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.services.util.MealList;

public interface MealService {
	
	public MealList getAllMeals(String userName);
	public Meal getMeal(String userName, Long id);
	//public MealList saveMeals(String userName, MealList meal);
	public Meal saveMeal(String userName, Meal meal);
	public Meal updateMeal(String userName, Meal meal, Long id);
	public void deleteMeal(String userName, Long id);
}
