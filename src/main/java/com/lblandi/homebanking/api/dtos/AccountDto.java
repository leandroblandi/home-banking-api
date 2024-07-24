package com.lblandi.homebanking.api.dtos;

import java.math.BigDecimal;

import com.lblandi.homebanking.api.enums.CurrencyTypeEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AccountDto {
	private String uuid;
	private String iban;
	private String ibanAlias;
	private String ownerFullName;
	private CurrencyTypeEnum currency;
	private BigDecimal balance;
}
