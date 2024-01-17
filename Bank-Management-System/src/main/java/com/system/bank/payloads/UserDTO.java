package com.system.bank.payloads;

import java.util.ArrayList;
import java.util.List;

import com.system.bank.entities.Account;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class UserDTO {


    @NotBlank(message = "User Name cannot be blank")
    private String username;
    
    @NotBlank(message = "Full Name cannot be blank")
    private String fullname;

    @NotBlank(message = "Pin cannot be blank")
    private String pin;    

    @NotBlank(message = "Email cannot be blank")
    @Email
    private String email;
   
    @NotBlank(message = "Phone Number cannot be blank")
    private String phoneNumber;
    
    @NotBlank(message = "Aadhaar Number cannot be blank")
    private String aadhaarnumber;

   
    private List<Account> accounts = new ArrayList<>();
    
    private String role;
}
