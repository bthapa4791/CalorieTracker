package com.spring.app.core.repositories;

import com.spring.app.core.entities.Authority;

public interface AuthorityRepo {
	
	public void saveAuthority(String[] authority);

	public Authority findById(Integer id);
}
