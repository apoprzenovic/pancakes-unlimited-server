package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.PancakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PancakeService {

    private final PancakeRepository pancakeRepository;

    @Autowired
    public PancakeService(PancakeRepository pancakeRepository){
        this.pancakeRepository = pancakeRepository;
    }

    public List<Pancake> getAllPancakes() {
        return pancakeRepository.findAll();
    }

    public Pancake getPancakeById(Integer id) {
        return pancakeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + id));
    }

    public Pancake createPancake(Pancake pancake) {
        return pancakeRepository.save(pancake);
    }

    public Pancake updatePancake(Integer id, Pancake pancakeDetails) {
        Pancake newPancake = getPancakeById(id);
        newPancake.setName(pancakeDetails.getName());
        newPancake.setImage(pancakeDetails.getImage());
        return pancakeRepository.save(newPancake);
    }

    public void deletePancake(Integer id) {
        Pancake pancake = getPancakeById(id);
        pancakeRepository.delete(pancake);
    }
}
