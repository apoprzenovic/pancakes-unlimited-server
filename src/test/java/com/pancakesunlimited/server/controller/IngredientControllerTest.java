package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Ingredient;
import com.pancakesunlimited.server.service.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * Method to test the {@link IngredientController#getAllIngredients()} method
     */
    @Test
    void getAllIngredients() {
        Ingredient ingredient1 = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        List<Ingredient> expectedIngredients = Arrays.asList(ingredient1, ingredient2);

        when(ingredientService.getAllIngredients()).thenReturn(new ResponseEntity<>(expectedIngredients, HttpStatus.OK));

        ResponseEntity<List<Ingredient>> actualIngredients = ingredientController.getAllIngredients();

        assertEquals(expectedIngredients, actualIngredients.getBody());
        assertEquals(HttpStatus.OK, actualIngredients.getStatusCode());
    }
}