package com.spring.app.core.entities;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@NamedQueries({
    @NamedQuery(
            name = User.FIND_BY_USERNAME,
            query = "select u from User u where username = :username"
    ),
    @NamedQuery(
            name = User.COUNT_TODAYS_CALORIES,
            query = "select sum(m.calories) from Meal m where m.user.username = :username and m.date = CURRENT_DATE"
    )
})
public class User {
	
	public static final String FIND_BY_USERNAME = "user.findByUserName";
    public static final String COUNT_TODAYS_CALORIES = "user.todaysCalories";
	
    @Id @GeneratedValue
    private Long id;
    
    private String username;
    private String password;
    private String email;
   
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_authority", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "authority_id", table = "Authority", referencedColumnName = "id") })
	private Set<Authority> authorities = new HashSet<Authority>();
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", 
    		cascade = {CascadeType.ALL}, orphanRemoval = true) 
	@JsonManagedReference
    private Set<Meal> meals = new HashSet<Meal>();
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

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
	
}
