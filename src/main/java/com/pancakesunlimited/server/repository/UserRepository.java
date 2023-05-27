package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

