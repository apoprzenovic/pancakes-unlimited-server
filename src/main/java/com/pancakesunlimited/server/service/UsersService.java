package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Users;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Service class for the {@link Users} Entity
 */
@Service
public class UsersService {

    private final UsersRepository usersRepository;

    /**
     * Constructor for the UsersService class
     *
     * @param usersRepository - the repository for the {@link Users} Entity
     */
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    /**
     * Method to get all users using {@link UsersRepository}
     *
     * @return - a list of all users
     */
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    /**
     * Method to get a user by id using {@link UsersRepository}
     *
     * @param id - the id of the user to be returned
     * @return - the user with the specified id
     */
    public Users getUserById(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" + "id: " + id));
    }

    /**
     * Method to create a user using {@link UsersRepository}
     *
     * @param user - the user to be created
     * @return - the created user
     */
    public Users createUser(Users user) {
        if (getUserByEmail(user.getEmail()) != null)
            throw new ResourceNotFoundException("There is already a user registered with the email: " + user.getEmail());
        return usersRepository.save(user);
    }

    /**
     * Method to update a user using {@link UsersRepository}
     *
     * @param id          - the id of the user to be updated
     * @param userDetails - the user details to be updated
     * @return - the updated user
     */
    public Users updateUser(Integer id, Users userDetails) {
        Users newUser = getUserById(id);
        if (getUserByEmail(userDetails.getEmail()) != null)
            throw new ResourceNotFoundException("There is already a user registered with the email: " + userDetails.getEmail());
        newUser.setEmail(userDetails.getEmail());
        newUser.setUsername(userDetails.getUsername());
        newUser.setLastname(userDetails.getLastname());
        newUser.setPassword(userDetails.getPassword());
        newUser.setRoles(userDetails.getRoles());
        return usersRepository.save(newUser);
    }

    /**
     * Method to delete a user using {@link UsersRepository}
     *
     * @param id - the id of the user to be deleted
     */
    public void deleteUser(Integer id) {
        Users user = getUserById(id);
        usersRepository.delete(user);
    }

    /**
     * Method to get a user by email using {@link UsersRepository}
     *
     * @param email - the email of the user to be returned
     * @return - the user with the specified email
     */
    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
