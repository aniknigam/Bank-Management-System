package com.system.bank.service;

import java.math.BigDecimal;
import java.util.List;

import com.system.bank.entities.Account;
import com.system.bank.entities.Transaction;
import com.system.bank.entities.TransactionType;
import com.system.bank.payloads.AmountRequest;


public interface TransactionService {
	void createTransaction(Account account, Double amount, TransactionType transactionType);
	void transfer(String fromAccountId, String toAccountId, Double amount);
	void withdraw(String accountId, Double amount);
	void deposit(String accountId, Double amount);
	List<Transaction> transactionHistory(String accountId);
}
