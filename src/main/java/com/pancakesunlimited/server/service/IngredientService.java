package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Integer getMostOrderedIngredientLastMonth() {
        return ingredientRepository.getMostOrderedIngredientLastMonth();
    }

    public Integer getMostOrderedHealthyIngredientLastMonth() {
        return ingredientRepository.getMostOrderedHealthyIngredientLastMonth();
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + id));
    }

    public Ingredient updateIngredient(Integer id, Ingredient ingredientDetails) {
        Ingredient ingredient = getIngredientById(id);
        ingredient.setName(ingredientDetails.getName());
        ingredient.setType(ingredientDetails.getType());
        ingredient.setHealthy(ingredientDetails.isHealthy());
        ingredient.setPrice(ingredientDetails.getPrice());
        ingredient.setImage(ingredientDetails.getImage());
        Ingredient updatedIngredient = ingredientRepository.save(ingredient);

        pancakeService.recalculatePancakePrices(updatedIngredient);
        return updatedIngredient;
    }

    public void deleteIngredient(Integer id) {
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

}
