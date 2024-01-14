package com.system.bank.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.system.bank.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();
		ApiResponse apiresponse = new ApiResponse(message, false);
		return new ResponseEntity<ApiResponse>(apiresponse, HttpStatus.NOT_FOUND);
	}

	//this type of exception occur when we validate the dto and use @Valid in the controller so MethodArgumentNotValidException class is not created manually is comes in the console 
		@ExceptionHandler(MethodArgumentNotValidException.class)
		public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex) {
	        Map<String, String> errorResponse = new HashMap<>();
	        ex.getBindingResult().getAllErrors().forEach((error)-> {
	        	String fieldName = ((FieldError)error).getField();
	        	String message = error.getDefaultMessage(); 
	        	errorResponse.put(fieldName, message);     	
	        });
	        return new ResponseEntity<Map<String, String>>(errorResponse,HttpStatus.BAD_REQUEST);
		}
}
