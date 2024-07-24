package com.lblandi.homebanking.api.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lblandi.homebanking.api.converters.impl.TransactionConverter;
import com.lblandi.homebanking.api.dtos.DoTransactionDto;
import com.lblandi.homebanking.api.dtos.TransactionDto;
import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.entities.Transaction;
import com.lblandi.homebanking.api.services.IAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("${home.banking.path}")
@CrossOrigin("${home.banking.allowed.origin}")
public class TransactionController {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private TransactionConverter transactionConverter;

	@GetMapping("/v1/accounts/{uuid}/transactions")
	public ResponseEntity<Set<TransactionDto>> getTransactions(@PathVariable String uuid) {
		Set<Transaction> transactions = accountService.getTransactions(uuid);
		return ResponseEntity.ok(transactionConverter.unconvertSet(transactions));
	}

	@PatchMapping("/v1/accounts/{uuid}/transactions")
	public ResponseEntity<TransactionDto> doTransaction(@PathVariable String uuid,
			@Valid @RequestBody DoTransactionDto dto) {
		Account accountFrom = accountService.find(uuid);
		Transaction transferTransaction = accountService.transfer(dto.getAmount(), accountFrom,
				dto.getAliasToTransfer());
		return ResponseEntity.ok(transactionConverter.unconvert(transferTransaction));
	}
}
