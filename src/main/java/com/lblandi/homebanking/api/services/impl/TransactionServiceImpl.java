package com.lblandi.homebanking.api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Transaction;
import com.lblandi.homebanking.api.enums.PaymentMethodEnum;
import com.lblandi.homebanking.api.enums.TransactionStatusEnum;
import com.lblandi.homebanking.api.enums.TransactionTypeEnum;
import com.lblandi.homebanking.api.exceptions.InvalidOperationException;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.repositories.ITransactionRepository;
import com.lblandi.homebanking.api.services.IAccountService;
import com.lblandi.homebanking.api.services.ITransactionService;

import jakarta.transaction.Transactional;

@Service
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	private ITransactionRepository transactionRepository;
	
	@Autowired
	private IAccountService accountService;

	@Override
	public Transaction save(Transaction transaction) {

		if (transaction == null) {
			throw new InvalidValueException("transaction", null);
		}

		return transactionRepository.save(transaction);
	}
	
	@Override
	@Transactional
	public Transaction transfer(BigDecimal amount, Account accountFrom, String aliasTo) {
		Account accountTo = this.accountService.resolveAlias(aliasTo);

		// Si se intenta trasnferir a una cuenta con moneda diferente
		if (!accountFrom.getCurrency().equals(accountTo.getCurrency())) {
			throw new InvalidOperationException("Las cuentas son de diferentes monedas");
		}

		this.accountService.checkBalance(accountFrom, amount);
		return this.doTransfer(amount, accountFrom, accountTo);
	}

	/**
	 * Realiza la substraccion del monto en la cuenta de origen y suma el monto al
	 * balance de la cuenta destino
	 * 
	 * @param amount      El monto a transferir
	 * @param accountFrom La cuenta de origen
	 * @param accountTo   La cuenta de destino
	 * @return Un objeto de transaccion
	 * @author lblandi
	 */
	@Transactional
	private Transaction doTransfer(BigDecimal amount, Account accountFrom, Account accountTo) {
		BigDecimal balanceAccountFrom = accountFrom.getBalance().subtract(amount);
		accountFrom.setBalance(balanceAccountFrom);

		BigDecimal balanceAccountTo = accountTo.getBalance().add(amount);
		accountTo.setBalance(balanceAccountTo);

		return this.generateTransaction(amount, accountFrom, accountTo);
	}
	
	@Override
	@Transactional
	public Transaction generateTransaction(BigDecimal amount, Account accountFrom, Account accountTo) {
		Transaction transaction = Transaction.builder().accountFrom(accountFrom).accountTo(accountTo).amount(amount)
				.currency(accountFrom.getCurrency()).status(TransactionStatusEnum.SUCCESSFUL)
				.paymentMethod(PaymentMethodEnum.BANK_TRANSFER).type(TransactionTypeEnum.TRANSFER)
				.successDate(LocalDateTime.now()).type(TransactionTypeEnum.TRANSFER).build();
		return this.save(transaction);
	}
	
	@Override
	@Transactional
	public Set<Transaction> getTransactions(String accountUuid) {
		Account account = this.accountService.find(accountUuid);
		return account.getTransactions();
	}
}
