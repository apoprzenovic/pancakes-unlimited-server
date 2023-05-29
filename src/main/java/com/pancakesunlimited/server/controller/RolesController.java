package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Roles;
import com.pancakesunlimited.server.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Controller class for the roles table
 */
@RestController
@RequestMapping("/api/pu/roles")
public class RolesController {

    private final RolesService rolesService;

    /**
     * Constructor for the RolesController class to autowire the {@link RolesService} class
     *
     * @param rolesService - the service for the {@link Roles} Entity
     */
    @Autowired
    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * Method to get all roles using {@link RolesService}
     *
     * @return - a list of all roles
     */
    @GetMapping
    public List<Roles> getAllRoles() {
        return rolesService.getAllRoles();
    }

    /**
     * Method to get a role by id using {@link RolesService}
     *
     * @param id - the id of the role to be returned
     * @return - the role with the specified id
     */
    @GetMapping("/{id}")
    public Roles getRole(@PathVariable Integer id) {
        return rolesService.getRoleById(id);
    }

    /**
     * Method to create a role using {@link RolesService}
     *
     * @param role - the role to be created
     * @return - the created role
     */
    @PostMapping
    public Roles createRole(@RequestBody Roles role) {
        return rolesService.createRole(role);
    }

    /**
     * Method to update a role using {@link RolesService}
     *
     * @param id          - the id of the role to be updated
     * @param roleDetails - the role to be updated
     * @return - the updated role
     */
    @PutMapping("/{id}")
    public Roles updateRole(@PathVariable Integer id, @RequestBody Roles roleDetails) {
        return rolesService.updateRole(id, roleDetails);
    }

    /**
     * Method to delete a role using {@link RolesService}
     *
     * @param id - the id of the role to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteRole(@PathVariable Integer id) {
        rolesService.deleteRole(id);
    }
}
