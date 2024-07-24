package com.lblandi.homebanking.api.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.lblandi.homebanking.api.enums.CurrencyTypeEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@NotNull
	@Column(unique = true)
	private String iban;

	@Column(unique = true)
	private String ibanAlias;

	@Enumerated(EnumType.STRING)
	private CurrencyTypeEnum currency;

	@Min(0)
	@Builder.Default
	private BigDecimal balance = BigDecimal.valueOf(0L);

	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_card_uuid")
	private Card card;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_owner_uuid")
	private Person owner;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "account_transaction_uuid")
	private Set<Transaction> transactions;
	
	public void addTransaction(Transaction transaction) {
		
		if(transaction == null) {
			return;
		}
		
		if(transactions == null) {
			transactions = new HashSet<>();
		}
		
		transactions.add(transaction);
		transaction.setAccountFrom(this);
	}
}
