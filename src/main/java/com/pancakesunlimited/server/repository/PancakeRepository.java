package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PancakeRepository extends JpaRepository<Pancake, Long> {
}
