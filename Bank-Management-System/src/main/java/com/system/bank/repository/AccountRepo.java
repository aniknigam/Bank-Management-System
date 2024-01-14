package com.system.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.bank.entities.Account;


public interface AccountRepo extends JpaRepository<Account,Integer> {
	
}

