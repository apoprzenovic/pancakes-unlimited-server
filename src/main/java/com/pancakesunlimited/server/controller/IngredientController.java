package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pu/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        return ingredientService.getIngredientById(id);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable(value = "id") Integer ingredientId,
                                                       @RequestBody Ingredient ingredientDetails) {
        return ingredientService.updateIngredient(ingredientId, ingredientDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable(value = "id") Integer ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/mostOrderedLastMonth")
    public ResponseEntity<Ingredient> getMostOrderedIngredientLastMonth() {
        return ingredientService.getMostOrderedIngredientLastMonth();
    }

    @GetMapping("/mostOrderedHealthyLastMonth")
    public ResponseEntity<Ingredient> getMostOrderedHealthyIngredientLastMonth() {
        return ingredientService.getMostOrderedHealthyIngredientLastMonth();
    }
}