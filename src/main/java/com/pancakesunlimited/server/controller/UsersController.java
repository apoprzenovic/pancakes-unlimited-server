package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Users;
import com.pancakesunlimited.server.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Controller class for the users table
 */
@RestController
@RequestMapping("/api/pu/users")
public class UsersController {

    private final UsersService usersService;

    /**
     * Constructor for the UsersController class to autowire the {@link UsersService} class
     *
     * @param usersService - the service for the {@link Users} Entity
     */
    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    /**
     * Method to get all users using {@link UsersService}
     *
     * @return - a list of all users
     */
    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    /**
     * Method to get a user by id using {@link UsersService}
     *
     * @param id - the id of the user to be returned
     * @return - the user with the specified id
     */
    @GetMapping("/{id}")
    public Users getUser(@PathVariable Integer id) {
        return usersService.getUserById(id);
    }

    /**
     * Method to create a user using {@link UsersService}
     *
     * @param user - the user to be created
     * @return - the created user
     */
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.createUser(user);
    }

    /**
     * Method to update a user using {@link UsersService}
     *
     * @param id          - the id of the user to be updated
     * @param userDetails - the user details to be updated
     * @return - the updated user
     */
    @PutMapping("/{id}")
    public Users updateUser(@PathVariable Integer id, @RequestBody Users userDetails) {
        return usersService.updateUser(id, userDetails);
    }

    /**
     * Method to delete a user using {@link UsersService}
     *
     * @param id - the id of the user to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Integer id) {
        usersService.deleteUser(id);
    }
}
