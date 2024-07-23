package com.lblandi.homebanking.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lblandi.homebanking.api.entities.Card;

@Repository
public interface ICardRepository extends JpaRepository<Card, String> {
	public Optional<Card> findByNumber(Long number);
}
