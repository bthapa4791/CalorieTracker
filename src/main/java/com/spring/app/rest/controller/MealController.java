package com.spring.app.rest.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.SearchResult;
import com.spring.app.core.entities.User;
import com.spring.app.core.services.MealService;
import com.spring.app.core.services.util.MealList;
import com.spring.app.rest.resources.MealListResource;
import com.spring.app.rest.resources.MealResource;
import com.spring.app.rest.resources.UserResource;
import com.spring.app.rest.resources.asm.MealListResourceAsm;
import com.spring.app.rest.resources.asm.MealResourceAsm;
import com.spring.app.rest.resources.asm.UserResourceAsm;

@RestController
@RequestMapping("/meal")
public class MealController {

	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;
	
	@Autowired
	private MealService mealService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<MealListResource> getAllMeals(Principal principal) {
		
		MealList mealList = mealService.getAllMeals(principal.getName());
		MealListResource mealListRes = new MealListResourceAsm().toResource(mealList);
		return new ResponseEntity<MealListResource>(mealListRes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MealResource> getMealWithId(@PathVariable("id") long id, Principal principal) {
		Meal meal = mealService.getMeal(principal.getName(), id);
		MealResource mealRes = new MealResourceAsm().toResource(meal);
		return new ResponseEntity<MealResource>(mealRes, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<MealResource> saveMeal(@RequestBody MealResource mealResource, Principal principal) {
		//List<Meal> meals = new ArrayList<Meal>();
//		for (MealResource mealResource : mealResources) {
//			meals.add(mealResource.toMeal());
//		}
		
		Meal createdMeal = mealService.saveMeal(principal.getName(), mealResource.toMeal());
			
		MealResource mealRes = new MealResourceAsm().toResource(createdMeal);
		return new ResponseEntity<MealResource>(mealRes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<MealResource> updateMeal(@PathVariable("id") long id, @RequestBody MealResource mealResource, Principal principal) {
		
		Meal updatedMeal = mealService.updateMeal(principal.getName(), mealResource.toMeal(), id);
		
		MealResource mealRes = new MealResourceAsm().toResource(updatedMeal);
		return new ResponseEntity<MealResource>(mealRes, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void deleteMeal(@PathVariable("id") long id, Principal principal) {
		//long rid = mealResource.getRid();
		mealService.deleteMeal(principal.getName(), id);
	}
	
	
	public ResponseEntity<List<MealResource>> searchMeals(
			@RequestParam(value = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
			@RequestParam(value = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
			@RequestParam(value = "fromTime", required = false) @DateTimeFormat(pattern = "yyyy-MM=dd HH:mm") Date fromTime,
			@RequestParam(value = "toTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") Date toTime,
			@RequestParam(value = "pageNumber") Integer pageNumber) {
		if (fromDate == null && toDate == null) {
			fromDate = new Date(System.currentTimeMillis() - (3 * DAY_IN_MS));
			toDate = new Date();
		}
		
		return null;
		//SearchResult<Meal> result = mealService.fin
	}
	
	
}
