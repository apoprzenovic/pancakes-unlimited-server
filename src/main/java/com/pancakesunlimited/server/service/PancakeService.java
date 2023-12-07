package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import com.pancakesunlimited.server.repository.PancakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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

    public ResponseEntity<List<Pancake>> getAllPancakes() {
        List<Pancake> pancakes = pancakeRepository.findAll();
        return new ResponseEntity<>(pancakes, HttpStatus.OK);
    }

    public ResponseEntity<Pancake> getPancakeById(Integer id) {
        Pancake pancake = pancakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + id));
        return new ResponseEntity<>(pancake, HttpStatus.OK);
    }

    public ResponseEntity<Pancake> createPancake(Pancake pancake) {
        fetchIngredients(pancake);
        calculatePrice(pancake);
        Pancake createdPancake = pancakeRepository.save(pancake);
        return new ResponseEntity<>(createdPancake, HttpStatus.CREATED);
    }

    public ResponseEntity<Pancake> updatePancake(Integer id, Pancake pancakeDetails) {
        Pancake currentPancake = getPancakeById(id).getBody();

        if (pancakeDetails.getName() != null && !pancakeDetails.getName().isEmpty()) {
            currentPancake.setName(pancakeDetails.getName());
        }

        if (pancakeDetails.getIngredients() != null && !pancakeDetails.getIngredients().isEmpty()) {
            fetchIngredients(currentPancake);
        }

        assert currentPancake != null;
        calculatePrice(currentPancake);

        Pancake updatedPancake = pancakeRepository.save(currentPancake);
        return new ResponseEntity<>(updatedPancake, HttpStatus.OK);
    }

    public void deletePancake(Integer id) {
        Pancake pancake = getPancakeById(id).getBody();
        pancakeRepository.delete(pancake);
        new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void recalculatePancakePrices(Ingredient updatedIngredient) {
        List<Pancake> pancakes = getAllPancakes().getBody();
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
            if (ingredient.getPrice() != null)
                totalPrice = totalPrice.add(ingredient.getPrice());
            else
                totalPrice = totalPrice.add(BigDecimal.ZERO);
        }
        pancake.setPrice(totalPrice);
    }
}