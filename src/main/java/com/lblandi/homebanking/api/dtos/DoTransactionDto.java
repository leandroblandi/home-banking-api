package com.lblandi.homebanking.api.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DoTransactionDto {
	@Min(1)
	private BigDecimal amount;
	
	@NotEmpty
	private String aliasToTransfer;
}
