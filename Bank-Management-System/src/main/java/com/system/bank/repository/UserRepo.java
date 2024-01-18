package com.system.bank.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.system.bank.entities.User;



public interface UserRepo extends JpaRepository<User,Integer> {
	Optional<User>  findByEmail(String email);
	Optional<User> findByAccounts_AccountNumber(String accountNumber);
	Optional<List<User>> findByAadhaarnumber(String aadharnumber);
    Optional<User> findByUsername(String username);
}
