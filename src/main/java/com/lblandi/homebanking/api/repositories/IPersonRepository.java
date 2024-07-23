package com.lblandi.homebanking.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lblandi.homebanking.api.entities.Person;

@Repository
public interface IPersonRepository extends JpaRepository<Person, String> {
	public Optional<Person> findByDocument(String document);
}
