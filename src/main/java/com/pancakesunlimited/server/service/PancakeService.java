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

/**
 * @author Arnes Poprzenovic
 * Service class for the {@link Pancake} Entity
 */
@Service
public class PancakeService {

    private final PancakeRepository pancakeRepository;
    private final IngredientRepository ingredientRepository;

    /**
     * Constructor for the PancakeService class
     *
     * @param pancakeRepository    - the repository for the {@link Pancake} Entity
     * @param ingredientRepository - the repository for the {@link Ingredient} Entity
     */
    @Autowired
    public PancakeService(PancakeRepository pancakeRepository, IngredientRepository ingredientRepository) {
        this.pancakeRepository = pancakeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Method to get all pancakes using {@link PancakeRepository}
     *
     * @return - a list of all pancakes
     */
    public List<Pancake> getAllPancakes() {
        return pancakeRepository.findAll();
    }

    /**
     * Method to get a pancake by id using {@link PancakeRepository}
     *
     * @param id - the id of the pancake to be returned
     * @return - the pancake with the specified id
     */
    public Pancake getPancakeById(Integer id) {
        return pancakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + id));
    }

    /**
     * Method to create a pancake using {@link PancakeRepository}
     *
     * @param pancake - the pancake to be created
     * @return - the created pancake
     */
    public Pancake createPancake(Pancake pancake) {
        fetchIngredients(pancake);
        calculatePrice(pancake);
        return pancakeRepository.save(pancake);
    }

    /**
     * Method to update a pancake using {@link PancakeRepository}
     *
     * @param id             - the id of the pancake to be updated
     * @param pancakeDetails - the pancake details to be updated
     * @return - the updated pancake
     */
    public Pancake updatePancake(Integer id, Pancake pancakeDetails) {
        Pancake newPancake = getPancakeById(id);
        newPancake.setName(pancakeDetails.getName());
        newPancake.setImage(pancakeDetails.getImage());
        fetchIngredients(newPancake);
        calculatePrice(newPancake);
        return pancakeRepository.save(newPancake);
    }

    /**
     * Method to calculate the price of a pancake
     *
     * @param id - the id of the pancake to have its price calculated
     */
    public void deletePancake(Integer id) {
        Pancake pancake = getPancakeById(id);
        pancakeRepository.delete(pancake);
    }

    /**
     * Method to recalculate the prices of all pancakes that contain a specific ingredient
     *
     * @param updatedIngredient - the ingredient that has been updated
     */
    public void recalculatePancakePrices(Ingredient updatedIngredient) {
        List<Pancake> pancakes = getAllPancakes();
        for (Pancake pancake : pancakes) {
            if (pancake.getIngredients().contains(updatedIngredient)) {
                calculatePrice(pancake);
                pancakeRepository.save(pancake);
            }
        }
    }

    /**
     * Method to fetch the ingredients of a pancake
     *
     * @param pancake - the pancake to have its ingredients fetched
     */
    public void fetchIngredients(Pancake pancake) {
        List<Ingredient> ingredients = pancake.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            Ingredient ingredient = ingredients.get(i);
            Ingredient fetchedIngredient = ingredientRepository.findById(ingredient.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + ingredient.getId()));
            ingredients.set(i, fetchedIngredient);
        }
    }

    /**
     * Method to calculate the price of a pancake
     *
     * @param pancake - the pancake to have its price calculated
     */
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
