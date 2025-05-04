package com.example.recipeapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.recipeapp.model.Recipes;

public interface RecipeRepository extends JpaRepository<Recipes, Long> {

    @Query("SELECT r FROM Recipes r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Recipes> searchByTitle(@Param("query") String query);

    List<Recipes> findByDifficulty(String difficulty);

    @Query("SELECT r FROM Recipes r WHERE LOWER(r.title) LIKE LOWER(CONCAT('%', :title, '%')) AND r.difficulty = :difficulty")
    List<Recipes> findByTitleContainingAndDifficulty(@Param("title") String title, @Param("difficulty") String difficulty);

    List<Recipes> findByCreatorId(Long creatorId);

}
