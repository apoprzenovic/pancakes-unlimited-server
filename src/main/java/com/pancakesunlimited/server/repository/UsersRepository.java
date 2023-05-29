package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Users} Entity
 */
public interface UsersRepository extends JpaRepository<Users, Integer> {
}

