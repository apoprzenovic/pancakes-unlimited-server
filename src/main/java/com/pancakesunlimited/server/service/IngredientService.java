package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Service class for the {@link Ingredient} Entity
 */
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final PancakeService pancakeService;

    /**
     * Constructor for the IngredientService class
     *
     * @param ingredientRepository - the repository for the {@link Ingredient} Entity
     * @param pancakeService       - the service for the {@link PancakeService} Entity
     */
    @Autowired
    public IngredientService(IngredientRepository ingredientRepository, PancakeService pancakeService) {
        this.ingredientRepository = ingredientRepository;
        this.pancakeService = pancakeService;
    }

    /**
     * Method to get the most ordered ingredient last month using {@link IngredientRepository}
     *
     * @return - the id of the most ordered ingredient last month
     */
    @Transactional(readOnly = true)
    public Ingredient getMostOrderedIngredientLastMonth() {
        List<Integer> results = ingredientRepository.getMostOrderedIngredientLastMonth();
        if (results != null && !results.isEmpty()) {
            return ingredientRepository.findOneById(results.get(0));
        } else {
            throw new ResourceNotFoundException("Stored procedure MostOrderedIngredientLastMonth returned no results");
        }
    }

    /**
     * Method to get the most ordered healthy ingredient last month using {@link IngredientRepository}
     *
     * @return - the id of the most ordered healthy ingredient last month
     */
    @Transactional(readOnly = true)
    public Ingredient getMostOrderedHealthyIngredientLastMonth() {
        List<Integer> results = ingredientRepository.getMostOrderedHealthyIngredientLastMonth();
        if (results != null && !results.isEmpty()) {
            return ingredientRepository.findOneById(results.get(0));
        } else {
            throw new ResourceNotFoundException("Stored procedure MostOrderedHealthyIngredientLastMonth returned no results");
        }
    }

    /**
     * Method to get the most ordered unhealthy ingredient last month using {@link IngredientRepository}
     *
     * @param ingredient - the ingredient to be created
     * @return - the created ingredient
     */
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    /**
     * Method to get all ingredients using {@link IngredientRepository}
     *
     * @return - a list of all ingredients
     */
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    /**
     * Method to get an ingredient by id using {@link IngredientRepository}
     *
     * @param id - the id of the ingredient to be returned
     * @return - the ingredient with the specified id
     */
    public Ingredient getIngredientById(Integer id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient" + "id: " + id));
    }

    /**
     * Method to update an ingredient using {@link IngredientRepository}
     *
     * @param id                - the id of the ingredient to be updated
     * @param ingredientDetails - the ingredient details to be updated
     * @return - the updated ingredient
     */
    public Ingredient updateIngredient(Integer id, Ingredient ingredientDetails) {
        Ingredient currentIngredient = getIngredientById(id);

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
        return updatedIngredient;
    }


    /**
     * Method to delete an ingredient using {@link IngredientRepository}
     *
     * @param id - the id of the ingredient to be deleted
     */
    public void deleteIngredient(Integer id) {
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

}
