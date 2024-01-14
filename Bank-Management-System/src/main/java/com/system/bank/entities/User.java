package com.system.bank.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
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

   
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Account> accounts = new ArrayList<>();
    
    @NotBlank
    private String role;


}
