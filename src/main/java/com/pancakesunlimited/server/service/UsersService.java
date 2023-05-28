package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Users;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository){
        this.usersRepository = usersRepository;
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users getUserById(Integer id) {
        return usersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User" + "id: " + id));
    }

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Users updateUser(Integer id, Users userDetails) {
        Users newUser = getUserById(id);
        newUser.setEmail(userDetails.getEmail());
        newUser.setUsername(userDetails.getUsername());
        newUser.setLastname(userDetails.getLastname());
        newUser.setPassword(userDetails.getPassword());
        newUser.setRoles(userDetails.getRoles());
        return usersRepository.save(newUser);
    }

    public void deleteUser(Integer id) {
        Users user = getUserById(id);
        usersRepository.delete(user);
    }
}
