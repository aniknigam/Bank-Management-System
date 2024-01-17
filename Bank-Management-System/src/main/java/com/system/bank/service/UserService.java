package com.system.bank.service;

import java.util.List;

import com.system.bank.payloads.UserDTO;
import com.system.bank.payloads.UserResponse;

public interface UserService {
	UserResponse createUser(UserDTO userdto);

	UserResponse createAccountForExistingUser(String email);

	UserDTO updateUser(UserDTO userdto, String accountnumber);

	List<UserDTO> getUserAadhaar(String aadhar);

	List<UserDTO> getAllUsers();

	void deleteUserAccount(String accountnumber);

	UserResponse getUserByAccountNumber(String accountnumber);
}
