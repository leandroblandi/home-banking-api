package com.lblandi.homebanking.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lblandi.homebanking.api.exceptions.GenericException;
import com.lblandi.homebanking.api.exceptions.InsufficientFundsException;
import com.lblandi.homebanking.api.exceptions.InvalidOperationException;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.exceptions.NotFoundException;
import com.lblandi.homebanking.api.models.ErrorModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {
	@ExceptionHandler(InsufficientFundsException.class)
	public ResponseEntity<ErrorModel> handleException(InsufficientFundsException e) {
		log.error(e.getMessage());
		ErrorModel model = ErrorModel.builder().status(HttpStatus.BAD_REQUEST).details(e.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ErrorModel> handleException(InvalidOperationException e) {
		log.error(e.getMessage());
		ErrorModel model = ErrorModel.builder().status(HttpStatus.UNPROCESSABLE_ENTITY).details(e.getMessage()).build();
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(model);
	}
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorModel> handleException(NotFoundException e) {
		log.error(e.getMessage());
		ErrorModel model = ErrorModel.builder().status(HttpStatus.NOT_FOUND).details(e.getMessage()).build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(model);
	}
	
	@ExceptionHandler(InvalidValueException.class)
	public ResponseEntity<ErrorModel> handleException(InvalidValueException e) {
		log.error(e.getMessage());
		ErrorModel model = ErrorModel.builder().status(HttpStatus.BAD_REQUEST).details(e.getMessage()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(model);
	}
	
	@ExceptionHandler(GenericException.class)
	public ResponseEntity<ErrorModel> handleException(GenericException e) {
		log.error(e.getMessage());
		ErrorModel model = ErrorModel.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).details(e.getMessage()).build();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(model);
	}
}
