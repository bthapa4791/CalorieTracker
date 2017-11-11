package com.spring.app.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.spring.app.core.entities.Authority;
import com.spring.app.core.repositories.AuthorityRepo;

@Component
public class AuthorityToUserConverter implements Converter<Object, Authority>{

	@Autowired
	AuthorityRepo authorityRepo;
	
	@Override
	public Authority convert(Object element) {
		Integer id = Integer.parseInt((String)element);
		Authority profile= authorityRepo.findById(id);
		System.out.println("Profile : "+profile);
		return profile;
	}

}
