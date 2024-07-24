package com.lblandi.homebanking.api.services.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.iban4j.IbanFormatException;
import org.iban4j.IbanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.exceptions.InsufficientFundsException;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.exceptions.NotFoundException;
import com.lblandi.homebanking.api.repositories.IAccountRepository;
import com.lblandi.homebanking.api.services.IAccountService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired
	private IAccountRepository accountRepository;

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
	public Account find(String uuid) {
		Optional<Account> accountOptional = accountRepository.findById(uuid);

		if (accountOptional.isEmpty()) {
			throw new NotFoundException(Account.class.getName(), uuid);
		}

		return accountOptional.get();
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
