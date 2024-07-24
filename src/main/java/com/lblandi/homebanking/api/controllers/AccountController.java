package com.lblandi.homebanking.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lblandi.homebanking.api.converters.impl.AccountConverter;
import com.lblandi.homebanking.api.dtos.AccountDto;
import com.lblandi.homebanking.api.entities.Account;
import com.lblandi.homebanking.api.services.IAccountService;

@RestController
@RequestMapping("${home.banking.path}")
@CrossOrigin("${home.banking.allowed.origin}")
public class AccountController {

	@Autowired
	private IAccountService accountService;

	@Autowired
	private AccountConverter accountConverter;

	@GetMapping("/v1/accounts/{uuid}")
	public ResponseEntity<AccountDto> getAccountInfo(@PathVariable String uuid) {
		Account account = accountService.find(uuid);
		return ResponseEntity.ok(accountConverter.unconvert(account));
	}
}
