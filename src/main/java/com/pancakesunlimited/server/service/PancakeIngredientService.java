package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.PancakeIngredient;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.PancakeIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PancakeIngredientService {

    private final PancakeIngredientRepository pancakeIngredientRepository;

    @Autowired
    public PancakeIngredientService(PancakeIngredientRepository pancakeIngredientRepository){
        this.pancakeIngredientRepository = pancakeIngredientRepository;
    }

    public List<PancakeIngredient> getAllPancakeIngredients() {
        return pancakeIngredientRepository.findAll();
    }

    public PancakeIngredient getPancakeIngredientById(Integer id) {
        return pancakeIngredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PancakeIngredient" + "id: " + id));
    }

    public PancakeIngredient createPancakeIngredient(PancakeIngredient pancakeIngredient) {
        return pancakeIngredientRepository.save(pancakeIngredient);
    }

    public PancakeIngredient updatePancakeIngredient(Integer id, PancakeIngredient pancakeIngredientDetails) {
        PancakeIngredient newPancakeIngredient = getPancakeIngredientById(id);
        newPancakeIngredient.setPancake(pancakeIngredientDetails.getPancake());
        newPancakeIngredient.setIngredient(pancakeIngredientDetails.getIngredient());
        return pancakeIngredientRepository.save(newPancakeIngredient);
    }

    public void deletePancakeIngredient(Integer id) {
        PancakeIngredient pancakeIngredient = getPancakeIngredientById(id);
        pancakeIngredientRepository.delete(pancakeIngredient);
    }
}
