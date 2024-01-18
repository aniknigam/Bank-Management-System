package com.system.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.bank.entities.Account;
import com.system.bank.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {
	
	 List<Transaction> findByAccountOrderByCreatedAtDesc(Account account);
}
