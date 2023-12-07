package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Users;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.UsersRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = usersRepository.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    public ResponseEntity<Users> getUserById(Integer id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" + "id: " + id));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<Users> createUser(Users user) {
        if (getUserByEmail(user.getEmail()) != null)
            throw new ResourceNotFoundException("There is already a user registered with the email: " + user.getEmail());
        Users createdUser = usersRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    public ResponseEntity<Users> updateUser(Integer id, Users userDetails) {
        Users currentUser = getUserById(id).getBody();

        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
            if (getUserByEmail(userDetails.getEmail()) != null)
                throw new ResourceNotFoundException("There is already a user registered with the email: " + userDetails.getEmail());
            currentUser.setEmail(userDetails.getEmail());
        }

        if (userDetails.getFirstname() != null && !userDetails.getFirstname().isEmpty()) {
            currentUser.setFirstname(userDetails.getFirstname());
        }

        if (userDetails.getLastname() != null && !userDetails.getLastname().isEmpty()) {
            currentUser.setLastname(userDetails.getLastname());
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            currentUser.setPassword(userDetails.getPassword());
        }

        if (userDetails.getRoles() != null) {
            currentUser.setRoles(userDetails.getRoles());
        }

        Users updatedUser = usersRepository.save(currentUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(Integer id) {
        Users user = getUserById(id).getBody();
        usersRepository.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public Users getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public ResponseEntity<Users> attemptLogin(String email, String password) {
        Users user = usersRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("Passwords do not match");
        }
    }
}