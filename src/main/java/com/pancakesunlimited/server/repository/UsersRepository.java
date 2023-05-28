package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}

