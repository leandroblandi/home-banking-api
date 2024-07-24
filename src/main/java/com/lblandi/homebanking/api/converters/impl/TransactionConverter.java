package com.lblandi.homebanking.api.converters.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lblandi.homebanking.api.converters.ModelConverter;
import com.lblandi.homebanking.api.dtos.TransactionDto;
import com.lblandi.homebanking.api.entities.Transaction;

@Component
public class TransactionConverter extends ModelConverter<Transaction, TransactionDto> {

	@Autowired
	private AccountConverter accountConverter;

	@Override
	protected Transaction convertToDest(TransactionDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected TransactionDto convertFromDest(Transaction model) {
		return TransactionDto.builder().accountFrom(accountConverter.convertFromDest(model.getAccountFrom()))
				.accountTo(accountConverter.convertFromDest(model.getAccountTo())).amount(model.getAmount())
				.createdDate(model.getCreatedDate()).currency(model.getCurrency())
				.paymentMethod(model.getPaymentMethod()).status(model.getStatus()).successDate(model.getSuccessDate())
				.type(model.getType()).uuid(model.getUuid()).build();
	}

}
