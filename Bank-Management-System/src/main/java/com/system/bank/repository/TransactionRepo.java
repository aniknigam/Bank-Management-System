package com.system.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.bank.entities.Transaction;

public interface TransactionRepo extends JpaRepository<Transaction,Integer> {

}
