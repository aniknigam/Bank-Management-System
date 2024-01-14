package com.system.bank.entities;

import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Accounts")
@NoArgsConstructor
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Account number cannot be blank")
    private String accountNumber;

    // Other account attributes (e.g., balance, type, etc.)

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // Automatic account number generation
    @PrePersist
    public void generateAccountNumber() {
        this.accountNumber = generateUniqueAccountNumber();
    }

    private String generateUniqueAccountNumber() {
    	String timestamp = String.valueOf(System.currentTimeMillis());
    	System.out.print(timestamp);

        String randomNumber = String.valueOf(Math.abs(new Random().nextLong()));

        return  randomNumber;
    }
    
}
