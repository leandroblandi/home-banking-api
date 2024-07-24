package com.lblandi.homebanking.api.exceptions;

public class GenericException extends RuntimeException {
	private static final long serialVersionUID = 8456704168514472684L;

	public GenericException() {
		super("There was an unexpected error, please try again later");
	}
	
	public GenericException(String details) {
		super("There was an unexpected error, please try again later. Details: " + details);
	}
}
