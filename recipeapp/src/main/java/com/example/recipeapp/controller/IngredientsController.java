package com.example.recipeapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
            String ingredientName = (String) ingredientData.get("ingredient_name");  
            Long recipeId = ((Number) ingredientData.get("recipe_id")).longValue();

            Recipes recipe = recipesRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            Ingredients ingredient = new Ingredients();
            ingredient.setIngredient_name(ingredientName);  
            ingredient.setRecipe(recipe);  

            savedIngredients.add(ingredientsRepository.save(ingredient));
        }

        return savedIngredients;
    }

    @PutMapping("/{ingredientId}")
    public Ingredients updateIngredient(@PathVariable Long ingredientId, @RequestBody Ingredients updatedIngredient) {
        return ingredientsRepository.findById(ingredientId)
            .map(existingIngredient -> {
                existingIngredient.setIngredient_name(updatedIngredient.getIngredient_name());

                Long recipeId = updatedIngredient.getRecipe().getId();
                Recipes recipe = recipesRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

                existingIngredient.setRecipe(recipe);

                return ingredientsRepository.save(existingIngredient);
            })
            .orElseThrow(() -> new RuntimeException("Ingredient not found"));
    }

    @DeleteMapping("/{ingredientId}")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        ingredientsRepository.deleteById(ingredientId);
    }

}

