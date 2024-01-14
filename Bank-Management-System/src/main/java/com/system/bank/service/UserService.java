package com.system.bank.service;

import java.util.List;

import com.system.bank.payloads.UserDTO;
import com.system.bank.payloads.UserResponse;



public interface UserService {
    UserResponse createUser(UserDTO userdto);
    
    UserResponse createAccountForExistingUser(String email);
	
	UserDTO updateUser(UserDTO userdto, Integer userId);
	
	UserDTO getUserById(Integer userId);
	
	List<UserDTO> getAllUsers();
	
	void deleteUser(Integer userId);
	
	UserDTO getUserByEmail(String email);
	UserDTO getUserByAccountNumber(String accountnumber);
}
