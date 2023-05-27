package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * This class is used to handle requests for the Ingredient entity.
 * It uses the IngredientRepository to perform CRUD operations.
 */
@RestController
@RequestMapping("api.pu/ingredients")
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    /**
     * This constructor is used to inject the IngredientRepository dependency.
     * @param ingredientRepository the IngredientRepository to be injected
     */
    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * This method is used to get all ingredients.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients
     * @return a list of all ingredients
     */
    @GetMapping
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * This method is used to get an ingredient by id.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     * @param id the id of the ingredient to be retrieved
     * @return the ingredient with the given id
     */
    @GetMapping("/{id}")
    public Ingredient getIngredient(@PathVariable int id) {
        return ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
    }

    /**
     * This method is used to create an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients
     * @param ingredient the ingredient to be created
     * @return the created ingredient
     */
    @PostMapping
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * This method is used to update an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     * @param id the id of the ingredient to be updated
     * @param ingredientDetails the new ingredient details
     * @return the updated ingredient
     */
    @PutMapping("/{id}")
    public Ingredient updateIngredient(@PathVariable int id, @RequestBody Ingredient ingredientDetails) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        ingredient.setName(ingredientDetails.getName());
        ingredient.setType(ingredientDetails.getType());
        ingredient.setPrice(ingredientDetails.getPrice());
        ingredient.setCategory(ingredientDetails.getCategory());
        ingredient.setHealthy(ingredientDetails.isHealthy());
        ingredient.setImage(ingredientDetails.getImage());
        return ingredientRepository.save(ingredient);
    }

    /**
     * This method is used to delete an ingredient.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/{id}
     * @param id the id of the ingredient to be deleted
     * @return a response entity, which can be used to check if the deletion was successful
     * and the response entity could be: 200 OK, 404 Not Found, 500 Internal Server Error
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable int id) {
        Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ingredient not found"));
        ingredientRepository.delete(ingredient);
        return ResponseEntity.ok().build();
    }

    /**
     * This method is used to get the most ordered ingredient last month.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/mostOrderedLastMonth
     * @return the id of the most ordered ingredient last month
     */
    @GetMapping("/mostOrderedLastMonth")
    public Integer getMostOrderedIngredientLastMonth() {
        return ingredientRepository.getMostOrderedIngredientLastMonth();
    }

    /**
     * This method is used to get the most ordered healthy ingredient last month.
     * We can access this method by going to http://localhost:8080/api.pu/ingredients/mostOrderedHealthyLastMonth
     * @return the id of the most ordered healthy ingredient last month
     */
    @GetMapping("/mostOrderedHealthyLastMonth")
    public Integer getMostOrderedHealthyIngredientLastMonth() {
        return ingredientRepository.getMostOrderedHealthyIngredientLastMonth();
    }
}

