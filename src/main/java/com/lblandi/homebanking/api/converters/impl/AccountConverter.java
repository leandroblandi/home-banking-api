package com.lblandi.homebanking.api.converters.impl;

import org.springframework.stereotype.Component;

import com.lblandi.homebanking.api.converters.ModelConverter;
import com.lblandi.homebanking.api.dtos.AccountDto;
import com.lblandi.homebanking.api.entities.Account;

@Component
public class AccountConverter extends ModelConverter<Account, AccountDto> {

	@Override
	protected Account convertToDest(AccountDto model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AccountDto convertFromDest(Account model) {
		return AccountDto.builder().balance(model.getBalance()).currency(model.getCurrency()).iban(model.getIban())
				.ibanAlias(model.getIbanAlias()).uuid(model.getUuid()).ownerFullName(model.getOwner().getFullName())
				.build();
	}

}
