package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final PancakeService pancakeService;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, PancakeService pancakeService) {
        this.ingredientRepository = ingredientRepository;
        this.pancakeService = pancakeService;
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Ingredient> getMostOrderedIngredientLastMonth() {
        List<Integer> results = ingredientRepository.getMostOrderedIngredientLastMonth();
        if (results != null && !results.isEmpty()) {
            return ResponseEntity.ok(ingredientRepository.findOneById(results.get(0)));
        } else {
            throw new ResourceNotFoundException("Stored procedure MostOrderedIngredientLastMonth returned no results");
        }
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Ingredient> getMostOrderedHealthyIngredientLastMonth() {
        List<Integer> results = ingredientRepository.getMostOrderedHealthyIngredientLastMonth();
        if (results != null && !results.isEmpty()) {
            return ResponseEntity.ok(ingredientRepository.findOneById(results.get(0)));
        } else {
            throw new ResourceNotFoundException("Stored procedure MostOrderedHealthyIngredientLastMonth returned no results");
        }
    }

    public ResponseEntity<Ingredient> createIngredient(Ingredient ingredient) {
        Ingredient createdIngredient = ingredientRepository.save(ingredient);
        return new ResponseEntity<>(createdIngredient, HttpStatus.CREATED);
    }

    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    public ResponseEntity<Ingredient> getIngredientById(Integer id) {
        Ingredient ingredient = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + id));
        return new ResponseEntity<>(ingredient, HttpStatus.OK);
    }

    public ResponseEntity<Ingredient> updateIngredient(Integer id, Ingredient ingredientDetails) {
        Ingredient currentIngredient = getIngredientById(id).getBody();

        if (ingredientDetails.getName() != null && !ingredientDetails.getName().isEmpty()) {
            currentIngredient.setName(ingredientDetails.getName());
        }

        if (ingredientDetails.getCategory() != null && !ingredientDetails.getCategory().isEmpty()) {
            currentIngredient.setCategory(ingredientDetails.getCategory());
        }

        if (ingredientDetails.getHealthy() != null) {
            currentIngredient.setHealthy(ingredientDetails.getHealthy());
        }

        if (ingredientDetails.getPrice() != null) {
            currentIngredient.setPrice(ingredientDetails.getPrice());
        }

        if (ingredientDetails.getImage() != null && !ingredientDetails.getImage().isEmpty()) {
            currentIngredient.setImage(ingredientDetails.getImage());
        }

        Ingredient updatedIngredient = ingredientRepository.save(currentIngredient);

        pancakeService.recalculatePancakePrices(updatedIngredient);
        return new ResponseEntity<>(updatedIngredient, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteIngredient(Integer id) {
        Ingredient ingredient = getIngredientById(id).getBody();
        ingredientRepository.delete(ingredient);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}