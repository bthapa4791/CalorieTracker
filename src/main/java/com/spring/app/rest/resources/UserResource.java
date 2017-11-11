package com.spring.app.rest.resources;

import java.util.HashSet;
import java.util.Set;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.app.core.entities.Authority;
import com.spring.app.core.entities.Meal;
import com.spring.app.core.entities.User;

public class UserResource extends ResourceSupport{
	
	private String username;
    private String password;
    private String email;
   
    private Set<Authority> authorities = new HashSet<Authority>();
    private Set<Meal> meals = new HashSet<Meal>();
    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Set<Meal> meals) {
		this.meals = meals;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public User toUser() {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
//		Authority authority = new Authority();
//		authority.setName(role);
		user.setAuthorities(authorities);
		return user;
	}
}
