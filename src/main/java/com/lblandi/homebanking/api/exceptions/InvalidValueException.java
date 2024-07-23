package com.lblandi.homebanking.api.exceptions;

public class InvalidValueException extends RuntimeException {
	private static final long serialVersionUID = -3967402039460922950L;

	public InvalidValueException() {
	}
	
	public InvalidValueException(String parameterName) {
		super(String.format("El valor de %s es invalido", parameterName));
	}

	public InvalidValueException(String parameterName, String parameterValue) {
		super(String.format("El valor de %s no puede ser %s", parameterName, parameterValue));
	}
}
