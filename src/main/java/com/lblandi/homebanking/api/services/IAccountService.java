package com.lblandi.homebanking.api.services;

import java.math.BigDecimal;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Transaction;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;

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
	 * 
	 * Realiza una transferencia bancaria entre dos cuentas
	 * 
	 * @param amount
	 * @param accountFrom
	 * @param accountTo
	 * @return
	 */
	public Transaction transfer(BigDecimal amount, Account accountFrom, String aliasTo);

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