package com.lblandi.homebanking.api.entities;

import com.lblandi.homebanking.api.enums.AddressTypeEnum;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
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
@Table(name = "addresses")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AddressTypeEnum type;

	@NotEmpty
	private String streetName;

	@Min(0)
	private Integer number;

	@NotEmpty
	private String city;

	@NotEmpty
	private String province;

	@NotEmpty
	private String country;
}
