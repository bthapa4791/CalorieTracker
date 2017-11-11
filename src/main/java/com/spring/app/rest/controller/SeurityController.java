package com.spring.app.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.app.core.entities.Token;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.TokenRepo;
import com.spring.app.core.repositories.UserRepo;
import com.spring.app.core.security.SecurityUtils;

import io.swagger.annotations.Api;


@RestController
@Api(description = "User management API")
public class SeurityController {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private TokenRepo tokenRepo;
	
	@RequestMapping(value = "/security/account", method = RequestMethod.GET)
	public User getUserAccount() {
		User user = userRepo.findUserByUsername(SecurityUtils.getCurrentLogin());
		user.setPassword(null);
		return user;
	}
	
	@PreAuthorize("hasAuthority('admin')")
	@RequestMapping(value = "/security/tokens", method = RequestMethod.GET)
	public @ResponseBody List<Token> getTokens () {
		List<Token> tokens = tokenRepo.findAll();
		for (Token t : tokens) {
			t.setSeries(null);
			t.setValue(null);
		}
		return tokens;
	}
}
