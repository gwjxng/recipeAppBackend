package com.example.recipeapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.recipeapp.model.Recipes;

public interface RecipeRepository extends JpaRepository<Recipes, Long> {
}
