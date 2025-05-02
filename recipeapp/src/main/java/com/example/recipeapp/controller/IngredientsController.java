package com.example.recipeapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipeapp.model.Ingredients;
import com.example.recipeapp.model.Recipes;
import com.example.recipeapp.repository.IngredientsRepository;
import com.example.recipeapp.repository.RecipeRepository;

@RestController
@RequestMapping("/ingredients")
public class IngredientsController {

    @Autowired
    private IngredientsRepository ingredientsRepository;

    @Autowired
    private RecipeRepository recipesRepository;

    @GetMapping("/{recipeId}")
    public List<Ingredients> getIngredientsByRecipeId(@PathVariable Long recipeId) {
        return ingredientsRepository.findByRecipeId(recipeId);
    }
    
    @PostMapping
    public List<Ingredients> saveIngredients(@RequestBody List<Map<String, Object>> ingredientsList) {
        List<Ingredients> savedIngredients = new ArrayList<>();

        for (Map<String, Object> ingredientData : ingredientsList) {
            // Extract ingredient_name and recipe_id from the data
            String ingredientName = (String) ingredientData.get("ingredient_name");  // Expecting 'ingredient_name'
            Long recipeId = ((Number) ingredientData.get("recipe_id")).longValue();

            // Find the recipe using recipe_id
            Recipes recipe = recipesRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            // Create a new Ingredients object and set the recipe
            Ingredients ingredient = new Ingredients();
            ingredient.setIngredient_name(ingredientName);  // Set the 'ingredient_name'
            ingredient.setRecipe(recipe);  // Set the associated recipe

            // Save and add to list
            savedIngredients.add(ingredientsRepository.save(ingredient));
        }

        return savedIngredients;
    }
}

