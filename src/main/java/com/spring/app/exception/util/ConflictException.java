package com.spring.app.exception.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException{
	
	public ConflictException() {

	}
	
	public ConflictException(String message) {
		super(message);
	}
}
