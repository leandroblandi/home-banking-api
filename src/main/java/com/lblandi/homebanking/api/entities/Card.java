package com.lblandi.homebanking.api.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.lblandi.homebanking.api.enums.CardIssuerEnum;
import com.lblandi.homebanking.api.enums.CardTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
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
@Table(name = "cards")
public class Card {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@NotNull
	@Column(unique = true)
	private Long number;

	@CreationTimestamp
	private LocalDate issuedAt;
	private LocalDate expiresAt;

	@NotNull
	@Min(100)
	@Max(999)
	private Integer cvv;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CardTypeEnum type;

	@NotNull
	@Enumerated(EnumType.STRING)
	private CardIssuerEnum issuer;

	private BigDecimal transferLimit;
}
