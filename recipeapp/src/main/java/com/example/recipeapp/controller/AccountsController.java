package com.example.recipeapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipeapp.model.Accounts;
import com.example.recipeapp.repository.AccountsRepository;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "https://lta-recipe-app-5517f49e6ce9.herokuapp.com/") 
public class AccountsController {

    @Autowired
    private AccountsRepository accountRepository;

    @PostMapping("/register")
    public Accounts registerAccount(@RequestBody Accounts account) {
        return accountRepository.save(account);
    }

    @PostMapping("/login")
    public String login(@RequestBody Accounts loginAttempt) {
        Accounts account = accountRepository.findByUsername(loginAttempt.getUsername());

        if (account != null){
            if (account.getPassword().equals(loginAttempt.getPassword())) {
                return String.valueOf(account.getId());
            } else {
                return "wrong password";
            }
        }
        return "account not found";
    }
}
