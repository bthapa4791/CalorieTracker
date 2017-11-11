package com.spring.app.core.repositories;

import java.util.List;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.User;

public interface MealRepo {
	
	List<Meal> getAllMeals(String userName);
	Meal getMeal(String userName, Long id);
	Meal saveMeal(User user, Meal meal);
	void updateMeal(Meal meal);
	void deleteMeal(String userName, Long id);
}
