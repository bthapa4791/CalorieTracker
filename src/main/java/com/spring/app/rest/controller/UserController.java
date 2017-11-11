package com.spring.app.rest.controller;

import java.io.IOException;
import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.app.core.entities.HypermediaBook;
import com.spring.app.core.entities.User;
import com.spring.app.core.services.UserService;
import com.spring.app.core.services.util.UserList;
import com.spring.app.rest.resources.UserListResource;
import com.spring.app.rest.resources.UserResource;
import com.spring.app.rest.resources.asm.UserListResourceAsm;
import com.spring.app.rest.resources.asm.UserResourceAsm;

import io.swagger.annotations.Api;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
@Api(description = "Users management API")
public class UserController {
	
	private UserService userService;
	
	@Autowired
	public UserController (UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<UserListResource> getUserInfo(@RequestParam(value="username", required = false) String username) {
		UserList userList = null;
		if (username == null) {
			userList = userService.findAllUsers();
		} else {
			User user = userService.findUserByUsername(username);
			if(user == null) {
				userList = new UserList(new ArrayList<User>());
            } else {
            	userList = new UserList(Arrays.asList(user));
            }
		}
		
		UserListResource res = new UserListResourceAsm().toResource(userList);
		System.out.println(res);
		return new ResponseEntity<UserListResource>(res, HttpStatus.OK);
//		if (user != null) {
//			UserResource res = new UserResourceAsm().toResource(user);
//			
//			return new ResponseEntity<UserResource>(res, HttpStatus.OK);
//		} else {
//			return new ResponseEntity<UserResource>(HttpStatus.NOT_FOUND);
//		}
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<UserResource> registerUser(@RequestBody UserResource userDetails) {
		//try {
			User createdUser = userService.createUser(userDetails.toUser());
			UserResource res = new UserResourceAsm().toResource(createdUser);
			HttpHeaders headers = new HttpHeaders();
			headers.setLocation(URI.create(res.getLink("self").getHref()));
			return new ResponseEntity<UserResource>(res, headers, HttpStatus.CREATED);
		//} catch (UserExistsException e) {
			//throw new ConflictException("conflict", e);
		//}
	}
	
	
		
	//public ResponseEntity<UserResource> updateUserMaxCaloriesPerDay()

}
