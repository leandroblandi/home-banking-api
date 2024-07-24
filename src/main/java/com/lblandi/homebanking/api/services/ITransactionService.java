package com.lblandi.homebanking.api.services;

import java.math.BigDecimal;
import java.util.Set;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Transaction;

public interface ITransactionService {

	/**
	 * Guarda un objeto de transaccion que aun no haya sido persistido
	 * 
	 * @param transaction El objeto de transaccion a persistir
	 * @return El objeto persistido con su UUID
	 */
	public Transaction save(Transaction transaction);

	/**
	 * Obtiene todas las transacciones de una cuenta
	 * 
	 * @param accountUuid
	 * @return Un set de transacciones
	 */
	public Set<Transaction> getTransactions(String accountUuid);
	
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
	 * Genera el objeto de transaccion a partir del monto, cuenta de origen y
	 * destino
	 * 
	 * @param amount      El monto de transferencia
	 * @param accountFrom La cuenta de origen
	 * @param accountTo   La cuenta de destino
	 * @return El objeto de transferencia, guardado en la base de datos
	 * @author lblandi
	 */
	public Transaction generateTransaction(BigDecimal amount, Account accountFrom, Account accountTo);
}
