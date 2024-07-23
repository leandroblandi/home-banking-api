package com.lblandi.homebanking.api.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = -6452160526660218399L;

	public NotFoundException(String uuid) {
		super(String.format("No se encontro la entidad con el uuid %s", uuid));
	}

	public NotFoundException(String className, String uuid) {
		super(String.format("No se encontro el %s con el uuid %s", className, uuid));
	}
}
