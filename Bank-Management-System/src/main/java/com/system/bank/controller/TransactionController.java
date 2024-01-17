package com.system.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.system.bank.exception.InsufficientBalanceException;
import com.system.bank.exception.ResourceNotFoundException;
import com.system.bank.payloads.ApiResponse;
import com.system.bank.service.TransactionService;

@RestController
@RequestMapping("/bank/transaction")
public class TransactionController {

	@Autowired
	private TransactionService transactionservice;

	@PostMapping("/deposit/{accountnumber}")
	public ResponseEntity<ApiResponse> deposit(@PathVariable String accountnumber,
			@RequestParam("amount") Double amount) {
		transactionservice.deposit(accountnumber, amount);
		String message = "Amount of rupees " + amount.toString() + " is deposited";
		ApiResponse response = new ApiResponse(message, true);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/withdraw/{accountnumber}")
	public ResponseEntity<ApiResponse> withdraw(@PathVariable String accountnumber,
			@RequestParam("amount") Double amount) {
		try {
			this.transactionservice.withdraw(accountnumber, amount);
			String message = "Amount of rupees " + amount.toString() + " has been withdrawn";
			ApiResponse response = new ApiResponse(message, true);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (InsufficientBalanceException e) {
			// Handle insufficient balance exception
			ApiResponse response = new ApiResponse(e.getMessage(), false);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		} catch (ResourceNotFoundException e) {
			// Handle resource not found exception
			ApiResponse response = new ApiResponse(e.getMessage(), false);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Handle other exceptions
			ApiResponse response = new ApiResponse("An error occurred", false);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/transfer")
    public ResponseEntity<ApiResponse> transfer(@RequestParam("fromAccountNumber") String fromAccountNumber,
                                               @RequestParam("toAccountNumber") String toAccountNumber,
                                               @RequestParam("amount") Double amount) {
        try {
            this.transactionservice.transfer(fromAccountNumber, toAccountNumber, amount);
            String message = "Amount of rupees " + amount.toString() + " has been transferred from account " +
                    fromAccountNumber + " to account " + toAccountNumber;
            ApiResponse response = new ApiResponse(message, true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InsufficientBalanceException e) {
            // Handle insufficient balance exception
            ApiResponse response = new ApiResponse(e.getMessage(), false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            // Handle resource not found exception
            ApiResponse response = new ApiResponse(e.getMessage(), false);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Handle other exceptions
            ApiResponse response = new ApiResponse("An error occurred", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
