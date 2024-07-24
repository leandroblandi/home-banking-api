package com.lblandi.homebanking.api.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.lblandi.homebanking.api.enums.CurrencyTypeEnum;
import com.lblandi.homebanking.api.enums.PaymentMethodEnum;
import com.lblandi.homebanking.api.enums.TransactionStatusEnum;
import com.lblandi.homebanking.api.enums.TransactionTypeEnum;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionDto {
	private String uuid;
	private LocalDateTime createdDate;
	private LocalDateTime successDate;
	private TransactionTypeEnum type;
	private CurrencyTypeEnum currency;
	private BigDecimal amount;
	private AccountDto accountFrom;
	private AccountDto accountTo;
	private PaymentMethodEnum paymentMethod;
	private TransactionStatusEnum status;
}
