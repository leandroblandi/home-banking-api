package com.lblandi.homebanking.api.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lblandi.homebanking.api.entities.Transaction;
import com.lblandi.homebanking.api.exceptions.InvalidValueException;
import com.lblandi.homebanking.api.repositories.ITransactionRepository;
import com.lblandi.homebanking.api.services.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {

	@Autowired
	private ITransactionRepository transactionRepository;

	@Override
	public Transaction save(Transaction transaction) {

		if (transaction == null) {
			throw new InvalidValueException("transaction", null);
		}

		return transactionRepository.save(transaction);
	}
}
