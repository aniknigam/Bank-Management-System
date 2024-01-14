package com.system.bank.service.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.stream.Collectors;

import com.system.bank.entities.Account;
import com.system.bank.entities.User;
import com.system.bank.exception.ResourceNotFoundException;
import com.system.bank.payloads.UserDTO;
import com.system.bank.payloads.UserResponse;
import com.system.bank.repository.AccountRepo;
import com.system.bank.repository.UserRepo;
import com.system.bank.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private AccountRepo accountrepo;

	@Autowired
	private UserRepo userrepo;

	@Autowired
	private ModelMapper modelmapper;

	// new user created and its new account
	@Override
	public UserResponse createUser(UserDTO userdto) {
		User user = this.modelmapper.map(userdto, User.class);
		User saveduser = this.userrepo.save(user);

		Account account = new Account();
		account.setUser(saveduser);
		Account savedAccount = this.accountrepo.save(account);

		UserDTO saveddto = this.modelmapper.map(saveduser, UserDTO.class);

		UserResponse userresponse = new UserResponse();
		userresponse.setFullname(saveddto.getFullname());
		userresponse.setUsername(saveddto.getUsername());
		userresponse.setRole(saveddto.getRole());
		userresponse.setEmail(saveddto.getEmail());
		userresponse.setPhoneNumber(saveddto.getPhoneNumber());
		userresponse.setAccountnumber(savedAccount.getAccountNumber());

		return userresponse;
	}

	@Override
	public UserResponse createAccountForExistingUser(String email) {
		User user = this.userrepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", email));

		User existinguser = user;
		Account newAccount = new Account();
		newAccount.generateAccountNumber(); 
		newAccount.setUser(user);
		
		existinguser.getAccounts().add(newAccount);
		User saveduser = this.userrepo.save(existinguser);
			
		
		Account savedAccount = accountrepo.save(newAccount);
		UserDTO userdto = this.modelmapper.map(saveduser, UserDTO.class);
		
		UserResponse userresponse = new UserResponse();
		userresponse.setFullname(userdto.getFullname());
		userresponse.setUsername(userdto.getUsername());
		userresponse.setRole(userdto.getRole());
		userresponse.setEmail(userdto.getEmail());
		userresponse.setPhoneNumber(userdto.getPhoneNumber());
		userresponse.setAccountnumber(savedAccount.getAccountNumber());


		return userresponse;
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> allusers = this.userrepo.findAll();
		List<UserDTO> alluserdto = allusers
				.stream().map((user) -> this.modelmapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		return alluserdto;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public UserDTO getUserByEmail(String email) {
		User user = this.userrepo.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", email));
		UserDTO userdto = this.modelmapper.map(user, UserDTO.class);
		return userdto;
	}

	@Override
	public UserDTO getUserByAccountNumber(String accountnumber) {
		User user = this.userrepo.findByAccounts_AccountNumber(accountnumber)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", accountnumber));
		UserDTO userdto = this.modelmapper.map(user, UserDTO.class);
		return userdto;
	}

}
