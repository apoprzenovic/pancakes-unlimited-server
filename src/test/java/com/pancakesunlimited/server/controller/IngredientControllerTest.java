package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Arnes Poprzenovic
 * Class to test the {@link IngredientController} class
 */
class IngredientControllerTest {

    @Mock
    IngredientService ingredientService;

    @InjectMocks
    IngredientController ingredientController;

    /**
     * Method to set up the mockito annotations
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method to test the {@link IngredientController#getIngredient(Integer)} method
     */
    @Test
    void getAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1, ingredient2);

        when(ingredientService.getAllIngredients()).thenReturn(expectedIngredients);

        List<Ingredient> actualIngredients = ingredientController.getAllIngredients();

        assertEquals(expectedIngredients, actualIngredients);
    }
}
