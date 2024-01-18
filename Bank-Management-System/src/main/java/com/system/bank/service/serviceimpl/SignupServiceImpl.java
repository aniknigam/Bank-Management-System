package com.system.bank.service.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.system.bank.entities.Account;

import com.system.bank.entities.User;

import com.system.bank.payloads.UserDTO;
import com.system.bank.payloads.UserResponse;
import com.system.bank.repository.AccountRepo;

import com.system.bank.repository.UserRepo;


@Service
public class SignupServiceImpl implements UserDetailsService {
	
	@Autowired
    private UserRepo userRepo;
    
	
	
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepo accountrepo;
	
	
	@Autowired
	private ModelMapper modelmapper;

	 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userInfo = userRepo.findByUsername(username);
        return userInfo.map(UserDetail::new)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"+username));
    }
    
    public UserResponse createUser(UserDTO userdto) {
		User user = this.modelmapper.map(userdto, User.class);
		
		user.setPin(passwordEncoder.encode(user.getPin()));
		User saveduser = this.userRepo.save(user);

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
   



	

}
