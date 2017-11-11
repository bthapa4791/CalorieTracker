package com.spring.app.exception;

import java.io.IOException;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.spring.app.exception.util.ConflictException;

@EnableWebMvc
@ControllerAdvice
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler{
	
	public GlobalExceptionHandlerController() {
		super();
	}
	
	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<Map<String, Object>> handleException(
			Exception exception, HttpServletRequest request) {
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(exception, request, HttpStatus.CONFLICT);
		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<Map<String, Object>> handleNoResultException(
			NoResultException noResultException, HttpServletRequest request) {
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(noResultException, request, HttpStatus.NOT_FOUND);
		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.NOT_FOUND);
 	}
	
	@ExceptionHandler(IOException.class)
	public ResponseEntity<Map<String, Object>> handleIOException(
			IOException ioException, HttpServletRequest request) {
		ExceptionAttributes exceptionAttributes = new DefaultExceptionAttributes();
		Map<String, Object> responseBody = exceptionAttributes.getExceptionAttributes(ioException, request, HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<Map<String,Object>>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
}
