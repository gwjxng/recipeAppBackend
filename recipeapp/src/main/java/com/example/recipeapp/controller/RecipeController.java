package com.example.recipeapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipeapp.model.Recipes;
import com.example.recipeapp.repository.RecipeRepository;

@RestController
@RequestMapping("/recipes")
@CrossOrigin(origins = "https://lta-recipe-app-5517f49e6ce9.herokuapp.com") 
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public List<Recipes> getRecipes(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) String difficulty
    ) {
        if ((query != null && !query.isEmpty()) && (difficulty != null && !difficulty.isEmpty())) {
            return recipeRepository.findByTitleContainingAndDifficulty(query, difficulty);
        } else if (query != null && !query.isEmpty()) {
            return recipeRepository.searchByTitle(query);
        } else if (difficulty != null && !difficulty.isEmpty()) {
            return recipeRepository.findByDifficulty(difficulty);
        } else {
            return recipeRepository.findAll();
        }
    }
    
    @GetMapping("/{id}")
    public Recipes getRecipeById(@PathVariable Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @GetMapping("/your-recipes/{creatorId}")
    public List<Recipes> getRecipesByCreator(@PathVariable Long creatorId) {
        return recipeRepository.findByCreatorId(creatorId);
    }

    @PostMapping
    public Recipes saveRecipe(@RequestBody Recipes recipe) {
        return recipeRepository.save(recipe);
    }

    @PutMapping("/{recipeId}")
    public Recipes updateRecipe(@PathVariable Long recipeId, @RequestBody Recipes updatedRecipe) {
        return recipeRepository.findById(recipeId)
            .map(existingRecipe -> {
                existingRecipe.setTitle(updatedRecipe.getTitle());
                existingRecipe.setInstructions(updatedRecipe.getInstructions());
                existingRecipe.setDifficulty(updatedRecipe.getDifficulty());
                existingRecipe.setImage_url(updatedRecipe.getImage_url());
                return recipeRepository.save(existingRecipe);
            })
            .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        Optional<Recipes> recipeOptional = recipeRepository.findById(id);
        if (recipeOptional.isPresent()) {
            recipeRepository.delete(recipeOptional.get());
            return ResponseEntity.ok("Recipe deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipe not found.");
        }
    }
}
