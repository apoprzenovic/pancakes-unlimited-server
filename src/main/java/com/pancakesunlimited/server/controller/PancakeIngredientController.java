package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.PancakeIngredient;
import com.pancakesunlimited.server.service.PancakeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pu/pancakeIngredients")
public class PancakeIngredientController {

    private final PancakeIngredientService pancakeIngredientService;

    @Autowired
    public PancakeIngredientController(PancakeIngredientService pancakeIngredientService){
        this.pancakeIngredientService = pancakeIngredientService;
    }

    @GetMapping
    public List<PancakeIngredient> getAllPancakeIngredients() {
        return pancakeIngredientService.getAllPancakeIngredients();
    }

    @GetMapping("/{id}")
    public PancakeIngredient getPancakeIngredient(@PathVariable Integer id) {
        return pancakeIngredientService.getPancakeIngredientById(id);
    }

    @PostMapping
    public PancakeIngredient createPancakeIngredient(@RequestBody PancakeIngredient pancakeIngredient) {
        return pancakeIngredientService.createPancakeIngredient(pancakeIngredient);
    }

    @PutMapping("/{id}")
    public PancakeIngredient updatePancakeIngredient(@PathVariable Integer id, @RequestBody PancakeIngredient pancakeIngredientDetails) {
        return pancakeIngredientService.updatePancakeIngredient(id, pancakeIngredientDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePancakeIngredient(@PathVariable Integer id) {
        pancakeIngredientService.deletePancakeIngredient(id);
    }
}
