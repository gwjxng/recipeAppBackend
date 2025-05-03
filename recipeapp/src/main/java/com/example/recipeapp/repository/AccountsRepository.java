package com.example.recipeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recipeapp.model.Accounts;

public interface AccountsRepository extends JpaRepository<Accounts, Long> {
    Accounts findByUsernameAndPassword(String username, String password);

}
