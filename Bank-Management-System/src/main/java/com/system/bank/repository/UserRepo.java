package com.system.bank.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.bank.entities.User;



public interface UserRepo extends JpaRepository<User,Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByAccounts_AccountNumber(String accountNumber);
}
