package com.lblandi.homebanking.api.exceptions;

public class InsufficientFundsException extends RuntimeException {
	private static final long serialVersionUID = -5158667981776389517L;

	public InsufficientFundsException() {
		super("You don't have enough funds to perform this operation");
	}

	public InsufficientFundsException(String amount, String balance) {
		super("You don't have enough funds to perform this operation. Attempted to transfer: " + amount
				+ ", but the current balance is: " + balance);
	}
}
