package com.system.bank.service.serviceimpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.system.bank.repository.AccountRepo;
import com.system.bank.service.AccountService;

import com.system.bank.entities.Account;
import com.system.bank.exception.ResourceNotFoundException;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepo accountrepo;
	
	
	@Override
	public Double getAccountBalance(String accountId) {
	    Account acc = this.accountrepo.findByAccountNumber(accountId)
	            .orElseThrow(() -> new ResourceNotFoundException("User", "Id", accountId));
	    Double balanceStr = acc.getBalance();
	    return balanceStr; 
	}

}
