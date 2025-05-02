package com.example.recipeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.recipeapp.model.Ingredients;

public interface IngredientsRepository extends JpaRepository<Ingredients, Long> {
    @Query("SELECT i FROM Ingredients i WHERE i.recipe.id = :recipeId")
    List<Ingredients> findByRecipeId(@Param("recipeId") Long recipeId);
}

