package com.spring.app.core.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.MealRepo;
import com.spring.app.core.repositories.UserRepo;
import com.spring.app.core.services.MealService;
import com.spring.app.core.services.util.MealList;

@Service
public class MealServiceImpl implements MealService{

	@Autowired
	private MealRepo mealRepo;
	
	@Autowired
	private UserRepo userRepo;
	
//	@Override
//	public MealList saveMeals(String userName, MealList meals) {
//		List<Meal> mealList = meals.getMeals();
//		return new MealList(mealList.stream()
//                .map((meal) -> saveMeal(userName, meal)).collect(Collectors.toList()));
//	}

	@Override
	public Meal saveMeal(String userName, Meal meal) {
		User user = userRepo.findUserByUsername(userName);
		Meal savedMeal = mealRepo.saveMeal(user, meal);
		return savedMeal;
	}
	
	@Override
	public Meal updateMeal(String userName, Meal meal, Long id) {
		User user = userRepo.findUserByUsername(userName);
		Meal oldMeal = mealRepo.getMeal(userName, id);
		if (oldMeal != null) {
			oldMeal.setUser(user);
			oldMeal.setDate(meal.getDate());
			oldMeal.setDescription(meal.getDescription());
			oldMeal.setCalories(meal.getCalories());
			 mealRepo.updateMeal(oldMeal);
			return oldMeal;
		}
		return null;
	}

	@Override
	public MealList getAllMeals(String userName) {
		return new MealList(mealRepo.getAllMeals(userName));
	}

	@Override
	public Meal getMeal(String userName, Long id) {
		return mealRepo.getMeal(userName, id);
	}

	@Override
	public void deleteMeal(String userName, Long id) {
		// TODO Auto-generated method stub
		mealRepo.deleteMeal(userName, id);
	}	
	
	
}
