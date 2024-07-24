package com.lblandi.homebanking.api.entities;

import java.util.HashSet;
import java.util.Set;

import com.lblandi.homebanking.api.enums.PersonTypeEnum;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "persons")
public class Person {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String uuid;

	@NotNull
	@Builder.Default
	private Boolean active = true;

	@NotEmpty
	private String firstName;

	@NotEmpty
	private String lastName;

	@NotEmpty
	private String document;

	@NotNull
	@Enumerated(EnumType.STRING)
	private PersonTypeEnum type;

	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "person_address_uuid")
	private Address address;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REMOVE }, fetch = FetchType.EAGER)
	@JoinColumn(name = "person_account_uuid")
	private Set<Account> accounts;

	public void addAccount(Account account) {
		if (account == null) {
			return;
		}

		if (accounts == null) {
			accounts = new HashSet<>();
		}

		accounts.add(account);
	}

	public String getFullName() {
		return new StringBuilder().append(firstName).append(" ").append(lastName).toString();
	}
}
