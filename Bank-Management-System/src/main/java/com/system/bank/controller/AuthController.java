package com.system.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.system.bank.entities.AuthRequest;
import com.system.bank.payloads.ApiResponse;
import com.system.bank.service.serviceimpl.JwtService;
import com.system.bank.service.serviceimpl.SignupServiceImpl;


@RestController
@RequestMapping("/auth")
public class AuthController {
	   @Autowired
	    private SignupServiceImpl userInfoService;
	    @Autowired
	    private AuthenticationManager authenticationManager;
	    @Autowired
	    private JwtService jwtService;

	    @GetMapping("/welcome")
	    public String welcome(){
	        return "Welcome to Bloggin Web Application!!";
	    } 

	    
	    @PostMapping("/login")
	    public ResponseEntity<ApiResponse> addUser(@RequestBody AuthRequest authRequest) {
	        try {
	            Authentication authenticate = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));

	            if (authenticate.isAuthenticated()) {
	                String token = jwtService.generateToken(authRequest.getUserName());
	                
	                ApiResponse response = new ApiResponse();
	                response.setMessage("Login successful, Your Token:  "+token);
	                response.setSuccess(true);

	                return  ResponseEntity.ok(response);
	            } else {
	                throw new UsernameNotFoundException("Invalid user request");
	            }
	        } catch (AuthenticationException e) {
	            // Handle authentication failure
	            ApiResponse response = new ApiResponse();
	            response.setMessage("Authentication failed");
	            response.setSuccess(false);

	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        } catch (Exception e) {
	            // Handle other exceptions
	            ApiResponse response = new ApiResponse();
	            response.setMessage("Internal server error");
	            response.setSuccess(false);
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	        }
	    }
	   
	    
	  
	}
