package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Controller class for the ingredients table
 */
@RestController
@RequestMapping("/api/pu/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * Constructor for the IngredientController class to autowire the {@link IngredientService} class
     *
     * @param ingredientService - the service for the {@link Ingredient} Entity
     */
    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Method to get all ingredients using {@link IngredientService}
     *
     * @return - a list of all ingredients
     */
    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /**
     * Method to get an ingredient by id using {@link IngredientService}
     *
     * @param id - the id of the ingredient to be returned
     * @return - the ingredient with the specified id
     */
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Integer id) {
        return ingredientService.getIngredientById(id);
    }

    /**
     * Method to create an ingredient using {@link IngredientService}
     *
     * @param ingredient - the ingredient to be created
     * @return - the created ingredient
     */
    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    /**
     * Method to update an ingredient using {@link IngredientService}
     *
     * @param ingredientId      - the id of the ingredient to be updated
     * @param ingredientDetails - the ingredient with the updated details
     * @return - the updated ingredient
     */
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable(value = "id") Integer ingredientId,
                                       @RequestBody Ingredient ingredientDetails) {
        return ingredientService.updateIngredient(ingredientId, ingredientDetails);
    }

    /**
     * Method to delete an ingredient using {@link IngredientService}
     *
     * @param ingredientId - the id of the ingredient to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteIngredient(@PathVariable(value = "id") Integer ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

    /**
     * This method is used to get the most ordered ingredient last month using {@link IngredientService}'s custom method.
     *
     * @return the id of the most ordered ingredient last month
     */
    @GetMapping("/mostOrderedLastMonth")
    public Ingredient getMostOrderedIngredientLastMonth() {
        return ingredientService.getMostOrderedIngredientLastMonth();
    }

    /**
     * This method is used to get the most ordered healthy ingredient last month using {@link IngredientService}'s custom method.
     *
     * @return the id of the most ordered healthy ingredient last month
     */
    @GetMapping("/mostOrderedHealthyLastMonth")
    public Ingredient getMostOrderedHealthyIngredientLastMonth() {
        return ingredientService.getMostOrderedHealthyIngredientLastMonth();
    }
}

