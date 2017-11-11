package com.spring.app.rest.resources;


import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.ResourceSupport;

import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.User;

public class MealResource extends ResourceSupport{

	private Long rid;
	
	//@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	private String description;
	private Long calories;
	
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getCalories() {
		return calories;
	}
	public void setCalories(Long calories) {
		this.calories = calories;
	}
	
	public Meal toMeal() {
		Meal meal = new Meal();
		meal.setDate(date);
		meal.setCalories(calories);
		meal.setDescription(description);
		return meal;
	}
	
}
