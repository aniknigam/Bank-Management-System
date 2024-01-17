package com.system.bank.service.serviceimpl;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
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
		String hashedPassword = BCrypt.hashpw(user.getPin(), BCrypt.gensalt());
		user.setPin(hashedPassword);
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
		userresponse.setAadharNumber(saveddto.getAadhaarnumber());
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
		userresponse.setAadharNumber(userdto.getAadhaarnumber());
		userresponse.setAccountnumber(savedAccount.getAccountNumber());

		return userresponse;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		List<User> allusers = this.userrepo.findAll();
		List<UserDTO> alluserdto = allusers.stream().map((user) -> this.modelmapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		return alluserdto;
	}

	@Override
	public UserResponse getUserByAccountNumber(String accountnumber) {
		User user = this.userrepo.findByAccounts_AccountNumber(accountnumber)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", accountnumber));
		
		
		UserResponse userresponse = new UserResponse();
		userresponse.setFullname(user.getFullname());
		userresponse.setUsername(user.getUsername());
		userresponse.setRole(user.getRole());
		userresponse.setEmail(user.getEmail());
		userresponse.setPhoneNumber(user.getPhoneNumber());
		userresponse.setAadharNumber(user.getAadhaarnumber());
		userresponse.setAccountnumber(accountnumber);

		return userresponse;
	
	}

	@Override
	public List<UserDTO> getUserAadhaar(String aadhar) {
		List<User> users = this.userrepo.findByAadhaarnumber(aadhar)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", aadhar));
		List<UserDTO> alluserdto = users.stream().map((user) -> this.modelmapper.map(user, UserDTO.class))
				.collect(Collectors.toList());
		return alluserdto;
	}

	@Override
	public UserDTO updateUser(UserDTO userdto, String accountnumber) {
		User user = this.userrepo.findByAccounts_AccountNumber(accountnumber)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", accountnumber));

		user.setUsername(userdto.getUsername());
		user.setEmail(userdto.getEmail());
		user.setFullname(userdto.getFullname());
		user.setPhoneNumber(userdto.getPhoneNumber());
		user.setAadhaarnumber(userdto.getAadhaarnumber());

		User saveuser = this.userrepo.save(user);
		UserDTO savedto = this.modelmapper.map(saveuser, UserDTO.class);
		return savedto;
	}

	@Override
	public void deleteUserAccount(String accountnumber) {
		Account acc = this.accountrepo.findByAccountNumber(accountnumber)
				.orElseThrow(() -> new ResourceNotFoundException("Account", "Account Number", accountnumber));

		this.accountrepo.delete(acc);
	}

}
