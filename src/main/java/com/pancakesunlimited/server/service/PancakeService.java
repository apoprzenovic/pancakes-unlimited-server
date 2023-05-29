package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import com.pancakesunlimited.server.repository.PancakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pancakesunlimited.server.entity.Ingredient;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PancakeService {

    private final PancakeRepository pancakeRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public PancakeService(PancakeRepository pancakeRepository, IngredientRepository ingredientRepository) {
        this.pancakeRepository = pancakeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Pancake> getAllPancakes() {
        return pancakeRepository.findAll();
    }

    public Pancake getPancakeById(Integer id) {
        return pancakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + id));
    }

    public Pancake createPancake(Pancake pancake) {
        fetchIngredients(pancake);
        calculatePrice(pancake);
        return pancakeRepository.save(pancake);
    }

    public Pancake updatePancake(Integer id, Pancake pancakeDetails) {
        Pancake newPancake = getPancakeById(id);
        newPancake.setName(pancakeDetails.getName());
        newPancake.setImage(pancakeDetails.getImage());
        fetchIngredients(newPancake);
        calculatePrice(newPancake);
        return pancakeRepository.save(newPancake);
    }

    public void deletePancake(Integer id) {
        Pancake pancake = getPancakeById(id);
        pancakeRepository.delete(pancake);
    }

    public void recalculatePancakePrices(Ingredient updatedIngredient) {
        List<Pancake> pancakes = getAllPancakes();
        for (Pancake pancake : pancakes) {
            if (pancake.getIngredients().contains(updatedIngredient)) {
                calculatePrice(pancake);
                pancakeRepository.save(pancake);
            }
        }
    }

    public void fetchIngredients(Pancake pancake) {
        List<Ingredient> ingredients = pancake.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            Ingredient fetchedIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + ingredient.getId()));
            ingredients.set(i, fetchedIngredient);
        }
    }

    public void calculatePrice(Pancake pancake) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<Ingredient> ingredients = pancake.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getPrice() != null) {
                totalPrice = totalPrice.add(ingredient.getPrice());
            } else {
                System.out.println("Warning: Ingredient with ID " + ingredient.getId() + " has a null price.");
            }
        }
        pancake.setPrice(totalPrice);
    }

}
