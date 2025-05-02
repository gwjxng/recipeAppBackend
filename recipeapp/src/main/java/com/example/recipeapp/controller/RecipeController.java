package com.example.recipeapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipeapp.model.Recipes;
import com.example.recipeapp.repository.RecipeRepository;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping
    public List<Recipes> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Recipes getRecipeById(@PathVariable Long id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Recipes saveRecipe(@RequestBody Recipes recipe) {
        return recipeRepository.save(recipe);
    }
}
