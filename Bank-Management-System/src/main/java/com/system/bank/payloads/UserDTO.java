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


    @NotBlank(message = "username cannot be blank")
    private String username;
    
    @NotBlank(message = "username cannot be blank")
    private String fullname;

    @NotBlank(message = "username cannot be blank")
    private String pin;    

    @NotBlank(message = "email cannot be blank")
    @Email
    private String email;
   
    @NotBlank(message = "phone number cannot be blank")
    private String phoneNumber;

   
    private List<Account> accounts = new ArrayList<>();
    
    private String role;
}
