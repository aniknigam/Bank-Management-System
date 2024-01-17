package com.system.bank.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.system.bank.payloads.ApiResponse;
import com.system.bank.payloads.UserDTO;
import com.system.bank.payloads.UserResponse;
import com.system.bank.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bank/user")
public class UserController {

	@Autowired
	private UserService userservice;
	
	@PostMapping("/create")
	public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserDTO userdto){
		UserResponse user = this.userservice.createUser(userdto);
		return new ResponseEntity<UserResponse>(user, HttpStatus.CREATED);
	}

	@PostMapping("/createAccount/{emailId}")
	public ResponseEntity<?> createAccountForExistingUser(@Valid @PathVariable String emailId){
	    UserResponse user = this.userservice.createAccountForExistingUser(emailId);
	    return new ResponseEntity<UserResponse>(user, HttpStatus.CREATED);
	}
	
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDTO>> allusers(){
		List<UserDTO> alluser=  this.userservice.getAllUsers();
		return ResponseEntity.ok(alluser);
	}
	

	@GetMapping("/getuser/accountnumber/{accountnumber}")
	public ResponseEntity<UserResponse> getUserByAccount(@PathVariable String accountnumber){
		UserResponse user=  this.userservice.getUserByAccountNumber(accountnumber);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/getuser/aadhaarnumber/{aadhaarnumber}")
	public ResponseEntity<List<UserDTO>> getUserByAadhaar(@PathVariable String aadhaarnumber){
		List<UserDTO> user=  this.userservice.getUserAadhaar(aadhaarnumber);
		return ResponseEntity.ok(user);
	}
	

	@PutMapping("/updateuser/{accountnumber}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userdto ,@PathVariable String accountnumber){
	    UserDTO user = this.userservice.updateUser(userdto ,accountnumber);
	    return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteaccount/{accountnumber}")
	public ApiResponse deleteAccount(@PathVariable String accountnumber) {
		this.userservice.deleteUserAccount(accountnumber);
		return new ApiResponse("Account is Deleted", true);
	}
	

}
