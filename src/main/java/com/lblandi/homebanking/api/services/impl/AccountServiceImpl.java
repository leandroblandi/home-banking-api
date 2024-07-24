package com.lblandi.homebanking.api.services.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Transaction;
import com.lblandi.homebanking.api.enums.PaymentMethodEnum;
import com.lblandi.homebanking.api.enums.TransactionStatusEnum;
import com.lblandi.homebanking.api.enums.TransactionTypeEnum;
import com.lblandi.homebanking.api.exceptions.InsufficientFundsException;
import com.lblandi.homebanking.api.exceptions.InvalidOperationException;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.exceptions.NotFoundException;
import com.lblandi.homebanking.api.repositories.IAccountRepository;
import com.lblandi.homebanking.api.services.IAccountService;
import com.lblandi.homebanking.api.services.ITransactionService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private ITransactionService transactionService;

	@Override
	public void checkBalance(Account account, BigDecimal amountToSubstract) {
		// Si el monto a transferir es 0 o negativo
		if (amountToSubstract.equals(BigDecimal.ZERO) || amountToSubstract.compareTo(BigDecimal.ZERO) < 0) {
			throw new InvalidValueException("amount", amountToSubstract.toString());
		}

		BigDecimal newBalance = account.getBalance().subtract(amountToSubstract);

		// Si no tiene dinero suficiente para transferir
		if (account.getBalance().equals(BigDecimal.ZERO) || newBalance.compareTo(BigDecimal.ZERO) <= 0) {
			throw new InsufficientFundsException(amountToSubstract.toString(), account.getBalance().toString());
		}
	}

	@Override
	@Transactional
	public Transaction transfer(BigDecimal amount, Account accountFrom, String aliasTo) {
		Account accountTo = this.resolveAlias(aliasTo);

		// Si se intenta trasnferir a una cuenta con moneda diferente
		if (!accountFrom.getCurrency().equals(accountTo.getCurrency())) {
			throw new InvalidOperationException("Las cuentas son de diferentes monedas");
		}

		this.checkBalance(accountFrom, amount);
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
	@Transactional
	private Transaction generateTransaction(BigDecimal amount, Account accountFrom, Account accountTo) {
		Transaction transaction = Transaction.builder().accountFrom(accountFrom).accountTo(accountTo).amount(amount)
				.currency(accountFrom.getCurrency()).status(TransactionStatusEnum.SUCCESSFUL)
				.paymentMethod(PaymentMethodEnum.BANK_TRANSFER).type(TransactionTypeEnum.TRANSFER)
				.successDate(LocalDateTime.now()).type(TransactionTypeEnum.TRANSFER).build();
		return transactionService.save(transaction);
	}

	@Override
	public Account find(String uuid) {
		Optional<Account> accountOptional = accountRepository.findById(uuid);

		if (accountOptional.isEmpty()) {
			throw new NotFoundException(Account.class.getName(), uuid);
		}

		return accountOptional.get();
	}

	@Override
	public Set<Transaction> getTransactions(String accountUuid) {
		Account account = find(accountUuid);
		return account.getTransactions();
	}

	@Override
	public void validateIban(Account account) {
		log.info("Validando IBAN de Banco con id {} con valor: {}", account.getIban(), account.getIban());

		try {
			IbanUtil.validate(account.getIban());
		} catch (IbanFormatException e) {
			log.error(e.getMessage());
			throw new InvalidValueException("iban");
		}
	}

	@Override
	public Account resolveAlias(String alias) {
		log.info("Buscando Account con alias: {}", alias);
		Optional<Account> accountOptional = accountRepository.findByIbanAlias(alias);

		if (accountOptional.isEmpty()) {
			log.error("No se encontro Account con alias: {}", alias);
			throw new NotFoundException(Account.class.getName(), alias);
		}

		return accountOptional.get();
	}
}
