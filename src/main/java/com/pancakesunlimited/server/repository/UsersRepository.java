package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Users} Entity
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
    /**
     * Method to find a user by email using {@link UsersRepository}
     * @param email - the email of the user to be returned
     * @return - the user with the specified email
     */
    Users findByEmail(String email);
}

