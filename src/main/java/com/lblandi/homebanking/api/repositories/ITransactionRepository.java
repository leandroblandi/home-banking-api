package com.lblandi.homebanking.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lblandi.homebanking.api.entities.Transaction;

@Repository
public interface ITransactionRepository extends JpaRepository<Transaction, String> {
}
