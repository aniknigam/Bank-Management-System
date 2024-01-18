package com.system.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.bank.payloads.ApiResponse;
import com.system.bank.service.AccountService;
import java.math.BigDecimal;

@RestController
@RequestMapping("/bank/account")
public class AccountController {

	@Autowired
	private AccountService accountservice;
	

	@GetMapping("/seebalance/{accountnumber}")
	@PreAuthorize("hasAuthority('USER_ROLES') or hasAuthority('ADMIN_ROLES')")
	private ApiResponse getBalance(@PathVariable String accountnumber) {
		Double balance = this.accountservice.getAccountBalance(accountnumber);
		return new ApiResponse("Total balance in your account is: "+balance,true);
	}
	
}
