package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to handle requests for the Ingredient entity.
 * It uses the IngredientRepository to perform CRUD operations.
 */
@RestController
@RequestMapping("/api/pu/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    /**
     * This constructor is used to inject the IngredientRepository dependency.
     * @param ingredientService the IngredientRepository to be injected
     */
    @Autowired
    public IngredientController(IngredientService ingredientService) {
         this.ingredientService = ingredientService;
    }

    /**
     * This method is used to get all ingredients.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients
     *
     * @return a list of all ingredients
     */
    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }

    /**
     * This method is used to get an ingredient by id.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     *
     * @param id the id of the ingredient to be retrieved
     * @return the ingredient with the given id
     */
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable Integer id) {
        return ingredientService.getIngredientById(id);
    }

    /**
     * This method is used to create an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients
     *
     * @param ingredient the ingredient to be created
     * @return the created ingredient
     */
    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.createIngredient(ingredient);
    }

    /**
     * This method is used to update an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     *
     * @param ingredientId      the id of the ingredient to be updated
     * @param ingredientDetails the new ingredient details
     * @return the updated ingredient
     */
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable(value = "id") Integer ingredientId,
                                       @RequestBody Ingredient ingredientDetails) {
        return ingredientService.updateIngredient(ingredientId, ingredientDetails);
    }

    /**
     * This method is used to delete an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     *
     * @param ingredientId the id of the ingredient to be deleted
     * @return a response entity, which can be used to check if the deletion was successful
     * and the response entity could be: 200 OK, 404 Not Found, 500 Internal Server Error
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteIngredient(@PathVariable(value = "id") Integer ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
    }

    /**
     * This method is used to get the most ordered ingredient last month.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/mostOrderedLastMonth
     *
     * @return the id of the most ordered ingredient last month
     */
    @GetMapping("/mostOrderedLastMonth")
    public Integer getMostOrderedIngredientLastMonth() {
        return ingredientService.getMostOrderedIngredientLastMonth();
    }

    /**
     * This method is used to get the most ordered healthy ingredient last month.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/mostOrderedHealthyLastMonth
     *
     * @return the id of the most ordered healthy ingredient last month
     */
    @GetMapping("/mostOrderedHealthyLastMonth")
    public Integer getMostOrderedHealthyIngredientLastMonth() {
        return ingredientService.getMostOrderedHealthyIngredientLastMonth();
    }
}

