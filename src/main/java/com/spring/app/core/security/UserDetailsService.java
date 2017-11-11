package com.spring.app.core.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.app.core.entities.Authority;
import com.spring.app.core.entities.User;
import com.spring.app.core.repositores.impl.UserRepoImpl;


@Service("userDetailsService")
@Transactional
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(UserDetailsService.class);
	
	@Autowired
	private UserRepoImpl userRepo;
	
	@Transactional
	public UserDetails loadUserByUsername(final String login) {
		logger.debug("Authenticating {}", login);

        User user = userRepo.findUserByUsername(login);
        if (user == null) {
            throw new UsernameNotFoundException("User " + login + " was not found in the database");
        }

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (Authority authority : user.getAuthorities()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getName());
            grantedAuthorities.add(grantedAuthority);
        }

        return new org.springframework.security.core.userdetails.User(login, user.getPassword(),
                grantedAuthorities);
    }

}
