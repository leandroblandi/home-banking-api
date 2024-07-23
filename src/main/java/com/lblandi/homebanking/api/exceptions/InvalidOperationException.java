package com.lblandi.homebanking.api.exceptions;

public class InvalidOperationException extends RuntimeException {
	private static final long serialVersionUID = -8835517153451591761L;

	public InvalidOperationException(String message) {
		super(message);
	}
}
