package com.spring.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import com.spring.app.core.repositories.AuthorityRepo;
import com.spring.app.core.security.RestUnauthorizedEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan(basePackages = {"com.spring.app.core.security"})
public class AppSecurity extends WebSecurityConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AppSecurity.class);
	
	public static final String REMEMBER_ME_KEY = "rememberme_key";
	
	public AppSecurity() {
		super();
		logger.info("loading SecurityConfig .......................");
	}
	
	@Autowired
	UserDetailsService 	userDetailsService;
	
	@Autowired
	private RestUnauthorizedEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private AccessDeniedHandler restAccessDeniedHandler;
	
	@Autowired
	private AuthenticationSuccessHandler restAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler restAuthenticationFailureHandler;
	
	@Autowired
	private AuthorityRepo authorityRepo;
	
	 @Autowired
	 private RememberMeServices rememberMeServices;
	
	@Autowired
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("bimal").password("123").roles("USER");
		auth.userDetailsService(userDetailsService);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/index.html", "/login.html", "/register.html", "/partials/**", "/template/**", "/",
				"/error/**");
	}
	
	
	public void saveAuthority(){
		 String[] authorityArray = {"user", "admin"};
		authorityRepo.saveAuthority(authorityArray);
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		saveAuthority();
		http
		.headers().disable()
        .csrf().disable()
        .authorizeRequests()
        	.antMatchers("/user").permitAll()
        	.antMatchers("/meal/**").permitAll()
            .antMatchers("/failure").permitAll()
            .antMatchers("/v2/api-docs").hasAnyAuthority("admin")
            .antMatchers("/users/**").hasAnyAuthority("admin")
            .anyRequest().authenticated()
            .and()
        .exceptionHandling()
            .authenticationEntryPoint(restAuthenticationEntryPoint)
            .accessDeniedHandler(restAccessDeniedHandler)
            .and()
        .formLogin()
            .loginProcessingUrl("/authenticate")
            .successHandler(restAuthenticationSuccessHandler)
            .failureHandler(restAuthenticationFailureHandler)
            .usernameParameter("username")
            .passwordParameter("password")
            .permitAll()
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
            .deleteCookies("JSESSIONID")
            .permitAll()
            .and()
        .rememberMe()
            .rememberMeServices(rememberMeServices)
            .key(REMEMBER_ME_KEY)
            .and();
							
	}
}
