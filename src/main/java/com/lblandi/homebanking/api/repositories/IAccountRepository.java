package com.lblandi.homebanking.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lblandi.homebanking.api.entities.Account;

@Repository
public interface IAccountRepository extends JpaRepository<Account, String> {
	public Optional<Account> findByIban(String iban);

	public Optional<Account> findByIbanAlias(String ibanAlias);
}
