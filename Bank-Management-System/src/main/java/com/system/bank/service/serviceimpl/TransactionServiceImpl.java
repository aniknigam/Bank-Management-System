package com.system.bank.service.serviceimpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.system.bank.entities.Account;
import com.system.bank.entities.Transaction;
import com.system.bank.entities.TransactionType;
import com.system.bank.exception.InsufficientBalanceException;
import com.system.bank.exception.ResourceNotFoundException;
import com.system.bank.payloads.AmountRequest;

import com.system.bank.repository.AccountRepo;
import com.system.bank.repository.TransactionRepo;
import com.system.bank.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private AccountRepo accountrepo;

	@Autowired
	private TransactionRepo transactionrepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	@JsonProperty("amount")
	public void createTransaction(Account account, Double amount, TransactionType transactionType) {
		Transaction transaction = new Transaction();
		transaction.setAccount(account);
		transaction.setAmount(amount); // Set amount directly as BigDecimal
		transaction.setTransactionType(transactionType);
		transactionrepo.save(transaction);
	}

	@Override
	public void transfer(String fromAccountId, String toAccountId, Double amount) {
		Account fromaccount = this.accountrepo.findByAccountNumber(fromAccountId)
				.orElseThrow(() -> new ResourceNotFoundException("Source Account", "account number", fromAccountId));

		Account toaccount = this.accountrepo.findByAccountNumber(toAccountId)
				.orElseThrow(() -> new ResourceNotFoundException("Destination Account", "account number", toAccountId));
		if (fromaccount.getBalance() >= amount) {
			// Perform withdrawal from source account
			Double updatedFromBalance = fromaccount.getBalance() - amount;
			fromaccount.setBalance(updatedFromBalance);
			this.accountrepo.save(fromaccount);

			// Perform deposit to destination account
			Double updatedToBalance = toaccount.getBalance() + amount;
			toaccount.setBalance(updatedToBalance);
			this.accountrepo.save(toaccount);

			// Create transactions for both accounts
			createTransaction(fromaccount, amount, TransactionType.TRANSFER);
			createTransaction(toaccount, amount, TransactionType.DEPOSIT);
		} else {
			// Insufficient balance in the source account for the transfer
			throw new InsufficientBalanceException("Insufficient balance for the transfer");
		}
	}

	@Override
	public void withdraw(String accountId, Double amount) {
		Account account = this.accountrepo.findByAccountNumber(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountId));

		Double currentBalance = account.getBalance();

		if (currentBalance >= amount) {
			Double updatedBalance = currentBalance - amount;
			account.setBalance(updatedBalance);
			this.accountrepo.save(account);
			createTransaction(account, amount, TransactionType.WITHDRAW);
		} else {
			throw new InsufficientBalanceException("Insufficient balance for withdrawal");
		}
	}

	@Override
	@JsonProperty("amount")
	public void deposit(String accountId, Double amount) {
		Account account = this.accountrepo.findByAccountNumber(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountId));

		// Retrieve the current balance as a String
		Double currentBalance = account.getBalance();

		// Update the balance with the new amount
		Double updatedBalance = currentBalance + amount;

		// Set the updated balance back to the account as String
		account.setBalance(updatedBalance);
		this.accountrepo.save(account);

		createTransaction(account, amount, TransactionType.DEPOSIT);
	}

	@Override
	public List<Transaction> transactionHistory(String accountId) {
		 Account account = accountrepo.findByAccountNumber(accountId)
	                .orElseThrow(() -> new ResourceNotFoundException("Account", "account number", accountId));

	     List<Transaction> transactions = transactionrepo.findByAccountOrderByCreatedAtDesc(account);
	     
	    
		return transactions;
	}

}
