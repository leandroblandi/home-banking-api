package com.lblandi.homebanking.api.services;

import com.lblandi.homebanking.api.entities.Transaction;

public interface ITransactionService {
	public Transaction save(Transaction transaction);
}
