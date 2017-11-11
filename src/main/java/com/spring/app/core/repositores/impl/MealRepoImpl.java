package com.spring.app.core.repositores.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.AbstractRepo;
import com.spring.app.core.repositories.MealRepo;
import com.spring.app.core.repositories.UserRepo;

@Repository
@Transactional
public class MealRepoImpl extends AbstractRepo<Integer, Meal> implements MealRepo{
	
	@Autowired
	private UserRepo userRepo;
	
//	@PersistenceContext
//	private EntityManager em;
//	
	@Override
	public Meal saveMeal(User user, Meal meal) {
		Meal savingMeal = new Meal();
		savingMeal.setUser(user);
		savingMeal.setDate(meal.getDate());
		savingMeal.setDescription(meal.getDescription());
		savingMeal.setCalories(meal.getCalories());
		persist(savingMeal);
		return savingMeal;
	}

	@Override
	public List<Meal> getAllMeals(String userName) {
		List<Meal> mealList = getEntityManager()
				.createQuery("Select m from Meal m where m.user.username = :username", Meal.class)
				.setParameter("username", userName)
				.getResultList();
		return mealList;
	}

	@Override
	public Meal getMeal(String userName, Long id) {
		Meal meal = getEntityManager()
				.createQuery("Select m from Meal m where m.user.username like :username and m.id like :id", Meal.class)
				.setParameter("username", userName)
				.setParameter("id", id)
				.getSingleResult();
		return meal;
	}

	@Override
	public void updateMeal(Meal meal) {
		//Meal updateMeal = new Meal();
		//updateMeal.setUser(user);
		//updateMeal.setDate(meal.getDate());
		//updateMeal.setDescription(meal.getDescription());
		//updateMeal.setCalories(meal.getCalories());
		//update(updateMeal);
		update(meal);
	}

	@Override
	public void deleteMeal(String userName, Long id) {
		//Meal deletingMeal = getMeal(userName, id);
		//if (deletingMeal != null) {
		//	delete(deletingMeal);
		//}
		User user = userRepo.findUserByUsername(userName);
		Meal deletingMeal = getMeal(userName, id);
		deletingMeal.setUser(null);
		user.getMeals().remove(deletingMeal);
	}

}
