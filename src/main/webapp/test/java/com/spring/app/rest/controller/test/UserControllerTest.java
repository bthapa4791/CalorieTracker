package com.spring.app.rest.controller.test;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import com.spring.app.config.AppConfig;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositories.UserRepo;
import com.spring.app.core.services.UserService;
import com.spring.app.exception.GlobalExceptionHandlerController;
import com.spring.app.exception.util.ConflictException;
import com.spring.app.rest.controller.UserController;

import junit.framework.Assert;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@EnableWebMvc
@ActiveProfiles("Test")
@ContextConfiguration(classes={AppConfig.class})
public class UserControllerTest {
	
	public static final String USERNAME = "test123";

	
	@InjectMocks
	private UserController userController;
	
	@Mock
	 private UserService service;
	
	@Autowired
	private UserRepo userRepo;
	
	private MockMvc mockMvc;
	
	private ArgumentCaptor<User> userCaptor;
	
	
	@Before
	 public void setup() {
		 MockitoAnnotations.initMocks(this);
		 
		 final ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver = new ExceptionHandlerExceptionResolver();

	        //here we need to setup a dummy application context that only registers the GlobalControllerExceptionHandler
	        final StaticApplicationContext applicationContext = new StaticApplicationContext();
	        applicationContext.registerBeanDefinition("advice", new RootBeanDefinition(GlobalExceptionHandlerController.class, null, null));

	        //set the application context of the resolver to the dummy application context we just created
	        exceptionHandlerExceptionResolver.setApplicationContext(applicationContext);

	        //needed in order to force the exception resolver to update it's internal caches
	        exceptionHandlerExceptionResolver.afterPropertiesSet();

		 
		 mockMvc = MockMvcBuilders.standaloneSetup(userController).setHandlerExceptionResolvers(exceptionHandlerExceptionResolver).build();
		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		 userCaptor = ArgumentCaptor.forClass(User.class);
		 
	 }
	
	@Test
	public void getUserInfoTest() throws Exception {
		User user = getUser();
			
		when(service.findUserByUsername("bimal")).thenReturn(user);
		
		mockMvc.perform(get("/user/bimal"))
		.andDo(print())
        .andExpect(jsonPath("$.username",is(user.getUsername())))
        .andExpect(jsonPath("$.password").doesNotExist())
        .andExpect(jsonPath("$.links[*].href", hasItem(endsWith("/user/bimal"))))
        .andExpect(status().isOk());
		
	}
	
	@Test
	public void getNonUserInfoTest() throws Exception {
		when(service.findUserByUsername("bimal")).thenReturn(null);
		
		mockMvc.perform(get("/user/bimal"))
			   .andDo(print())
			   .andExpect(status().isNotFound());
	}
	
	@Test
	public void createUserTest() throws Exception {
		User user = getUser();

		when(service.createUser(any(User.class))).thenReturn(user);
		
		mockMvc.perform(post("/user")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"username\": \"bimal\", \"password\": \"check\", \"email\": \"test@gmail.com\"}"))
		 			.andExpect(header().string("Location", endsWith("/user/bimal")))
		 			.andExpect(jsonPath("$.username", is(user.getUsername())))
	                .andDo(print())
	                .andExpect(status().isCreated());
				
		verify(service).createUser(userCaptor.capture());
		
		String password = userCaptor.getValue().getPassword();
		assertEquals("check", password);
		
		//User usr = userRepo.findUserByUsername("test123");
		//assertEquals("test@gmail.com", usr.getEmail());
		
	}
	
	@Test
	public void createUserExistsTest() throws Exception {
		
		when(service.createUser(any(User.class))).thenThrow(new ConflictException("User exists."));
		
		 mockMvc.perform(post("/user")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"username\": \"bimal\", \"password\": \"check\", \"email\": \"test@gmail.com\"}")
				//.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				
				.andDo(print())
			
				.andExpect(status().isConflict());	
	}
	
	private User getUser() {
		User user = new User();
		user.setUsername("bimal");
		user.setPassword("check");
		user.setEmail("jcs@gamil.com");
		return user;
	}
}
