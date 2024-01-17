package com.system.bank.payloads;

import java.util.ArrayList;
import java.util.List;

import com.system.bank.entities.Account;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserResponse {
    private String username;
    private String fullname;
    private String email;
    private String phoneNumber;
    private String aadharNumber;
    private String accountnumber;
    private String role;
}
