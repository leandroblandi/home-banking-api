package com.lblandi.homebanking.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lblandi.homebanking.api.entities.BankingUser;

@Repository
public interface IBankingUserRepository extends JpaRepository<BankingUser, String> {
	public Optional<BankingUser> findByUsername(String username);
}
