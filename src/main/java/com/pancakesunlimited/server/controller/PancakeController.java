package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.service.PancakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Controller class for the pancakes table
 */
@RestController
@RequestMapping("/api/pu/pancakes")
public class PancakeController {

    private final PancakeService pancakeService;

    /**
     * Constructor for the PancakeController class to autowire the {@link PancakeService} class
     *
     * @param pancakeService - the service for the {@link Pancake} Entity
     */
    @Autowired
    public PancakeController(PancakeService pancakeService) {
        this.pancakeService = pancakeService;
    }

    /**
     * Method to get all pancakes using {@link PancakeService}
     *
     * @return - a list of all pancakes
     */
    @GetMapping
    public List<Pancake> getAllPancakes() {
        return pancakeService.getAllPancakes();
    }

    /**
     * Method to get a pancake by id using {@link PancakeService}
     *
     * @param id - the id of the pancake to be returned
     * @return - the pancake with the specified id
     */
    @GetMapping("/{id}")
    public Pancake getPancake(@PathVariable Integer id) {
        return pancakeService.getPancakeById(id);
    }

    /**
     * Method to create a pancake using {@link PancakeService}
     *
     * @param pancake - the pancake to be created
     * @return - the created pancake
     */
    @PostMapping
    public Pancake createPancake(@RequestBody Pancake pancake) {
        return pancakeService.createPancake(pancake);
    }

    /**
     * Method to update a pancake using {@link PancakeService}
     *
     * @param id             - the id of the pancake to be updated
     * @param pancakeDetails - the pancake to be updated
     * @return - the updated pancake
     */
    @PutMapping("/{id}")
    public Pancake updatePancake(@PathVariable Integer id, @RequestBody Pancake pancakeDetails) {
        return pancakeService.updatePancake(id, pancakeDetails);
    }

    /**
     * Method to delete a pancake using {@link PancakeService}
     *
     * @param id - the id of the pancake to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deletePancake(@PathVariable Integer id) {
        pancakeService.deletePancake(id);
    }
}
