package com.ecommerce.main.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ecommerce.main.dto.ErrorDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<ErrorDto> handleLoginExceptions(InvalidCredentialsException ex) {
		ErrorDto errDto=new ErrorDto(ex.getMessage());
		return new ResponseEntity<>(errDto, HttpStatus.UNAUTHORIZED);
	}

}
