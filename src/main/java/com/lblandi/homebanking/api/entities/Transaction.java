package com.lblandi.homebanking.api.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.lblandi.homebanking.api.enums.CurrencyTypeEnum;
import com.lblandi.homebanking.api.enums.PaymentMethodEnum;
import com.lblandi.homebanking.api.enums.TransactionStatusEnum;
import com.lblandi.homebanking.api.enums.TransactionTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@CreationTimestamp
	private LocalDateTime createdDate;

	@Builder.Default
	private LocalDateTime successDate = LocalDateTime.now();

	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum type;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CurrencyTypeEnum currency;

	@NotNull
	@Min(1)
	@Builder.Default
	private BigDecimal amount = BigDecimal.valueOf(1L);

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_from_uuid")
	private Account accountFrom;

	@ManyToOne(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_to_uuid")
	private Account accountTo;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentMethodEnum paymentMethod;

	@NotNull
	@Enumerated(EnumType.STRING)
	private TransactionStatusEnum status;
}
