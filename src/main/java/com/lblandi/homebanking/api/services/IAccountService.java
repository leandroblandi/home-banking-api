package com.lblandi.homebanking.api.services;

import java.math.BigDecimal;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.exceptions.NotFoundException;

public interface IAccountService {

	/**
	 * Verifica que el balance de la cuenta proporcionada sea suficiente para
	 * realizar una operacion de substraccion de fondos
	 * 
	 * @param account           La cuenta a verificar el balance
	 * @param amountToSubstract La cantidad a substraer de la cuenta
	 */
	public void checkBalance(Account account, BigDecimal amountToSubstract);

	/**
	 * Busca una cuenta por UUID
	 * 
	 * @param uuid El UUID correspondiente a la cuenta bancaria a buscar
	 * @return El objeto que representa la cuenta
	 * @throws NotFoundException Si la cuenta no existe
	 */
	public Account find(String uuid);

	/**
	 * Valida el International Bank Account Number en base a la entidad Account
	 * pasada por parametro
	 * 
	 * @param account
	 * @throws InvalidValueException Si el IBAN es invalido
	 * @author lblandi
	 */
	public void validateIban(Account account);

	/**
	 * Busca una cuenta de banco según alias
	 * 
	 * @param alias El alias de la cuenta
	 * @return El IBAN correspondiente a la cuenta
	 * @author lblandi
	 */
	public Account resolveAlias(String alias);
}
