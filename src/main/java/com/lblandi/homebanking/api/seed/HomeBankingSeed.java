package com.lblandi.homebanking.api.seed;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Address;
import com.lblandi.homebanking.api.entities.Card;
import com.lblandi.homebanking.api.entities.Person;
import com.lblandi.homebanking.api.enums.AddressTypeEnum;
import com.lblandi.homebanking.api.enums.CardIssuerEnum;
import com.lblandi.homebanking.api.enums.CardTypeEnum;
import com.lblandi.homebanking.api.enums.CurrencyTypeEnum;
import com.lblandi.homebanking.api.enums.PersonTypeEnum;
import com.lblandi.homebanking.api.repositories.IAccountRepository;
import com.lblandi.homebanking.api.repositories.ICardRepository;
import com.lblandi.homebanking.api.repositories.IPersonRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HomeBankingSeed implements CommandLineRunner {

	@Autowired
	private IAccountRepository accountRepository;

	@Autowired
	private IPersonRepository personRepository;

	@Autowired
	private ICardRepository cardRepository;

	private static String document1 = "10.392.492";
	private static String document2 = "39.284.294";

	@Override
	public void run(String... args) throws Exception {		
		log.info("Starting initial data load...");
		
		if (personRepository.count() > 0
				|| accountRepository.count() > 0
				|| cardRepository.count() > 0) {
			log.warn("Initial data already loaded!");
			return;
		}
		
		seedPersons();
		seedCards();
		seedAccounts();
		
		log.info("Initial data loaded successfully!");
	}

	@Transactional
	private void seedPersons() {

		if (personRepository.count() > 0) {
			return;
		}

		log.info("HomeBankingSeed.seedPersons: Starting Person's data load...");

		Address address1 = Address.builder().streetName("Las Algas").number(1261).city("Lanus").province("Buenos Aires")
				.type(AddressTypeEnum.REAL)
				.country("Argentina").build();

		Address address2 = Address.builder().streetName("Av. Dardo Rocha").number(4592).city("Capital Federal")
				.type(AddressTypeEnum.REAL)
				.province("Buenos Aires").country("Argentina").build();

		Person person1 = Person.builder().firstName("Leandro Nahuel").lastName("Blandi").active(true).address(address1)
				.document(document1).type(PersonTypeEnum.INDIVIDUAL).accounts(new HashSet<>()).build();

		Person person2 = Person.builder().firstName("Javier Simon").lastName("Ochoa").active(true).address(address2)
				.document(document2).type(PersonTypeEnum.INDIVIDUAL).accounts(new HashSet<>()).build();

		personRepository.saveAll(Arrays.asList(person1, person2));
	}

	@Transactional
	private void seedCards() {

		if (personRepository.count() == 0 || cardRepository.count() > 0) {
			return;
		}
		
		log.info("HomeBankingSeed.seedCards: Starting Card data load...");

		Card card1 = Card.builder().number(5501678534013466L).cvv(169).issuedAt(LocalDate.of(2020, 1, 1))
				.expiresAt(LocalDate.of(2029, 1, 8)).type(CardTypeEnum.DEBIT_CARD)
				.issuer(CardIssuerEnum.VISA).build();

		Card card2 = Card.builder().number(5514039248573220L).cvv(314).issuedAt(LocalDate.of(2018, 3, 10))
				.expiresAt(LocalDate.of(2025, 9, 12)).type(CardTypeEnum.DEBIT_CARD)
				.issuer(CardIssuerEnum.MASTERCARD).build();

		cardRepository.saveAll(Arrays.asList(card1, card2));

	}

	@Transactional
	private void seedAccounts() {
		if (personRepository.count() == 0 || cardRepository.count() == 0
				|| accountRepository.count() > 0) {
			return;
		}

		log.info("HomeBankingSeed.seedAccounts: Starting Bank Accounts data load...");

		Person person1 = personRepository.findByDocument(document1).get();
		Person person2 = personRepository.findByDocument(document2).get();
		
		Card card1 = cardRepository.findByNumber(5501678534013466L).get();
		Card card2 = cardRepository.findByNumber(5514039248573220L).get();
		
		Account account1 = Account.builder()
				.balance(BigDecimal.valueOf(320700))
				.card(card1)
				.currency(CurrencyTypeEnum.ARS)
				.iban("AR23201300050001122345678")
				.ibanAlias("lblandi.bank.account")
				.transactions(new HashSet<>())
				.build();
		
		Account account2 = Account.builder()
				.balance(BigDecimal.valueOf(10700))
				.card(card2)
				.currency(CurrencyTypeEnum.ARS)
				.iban("AR32000101234567890123456")
				.ibanAlias("mesa.celular.bank.account")
				.transactions(new HashSet<>())
				.build();
		
		List<Account> accountsSaved = accountRepository.saveAll(Arrays.asList(account1, account2));
		cardRepository.saveAll(Arrays.asList(card1, card2));
		
		person1.addAccount(accountsSaved.get(0));
		person2.addAccount(accountsSaved.get(1));
		personRepository.saveAll(Arrays.asList(person1, person2));
	}
}
