package com.spring.app.core.repositories;

import java.util.List;

import com.spring.app.core.entities.Token;

public interface TokenRepo {
	
	void save(Token token);
	
	void update(Token token);
	
	void delete(String string);

	Token findOne(String presentedSeries);

	List<Token> findAll();
	
}
