package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.PancakeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PancakeIngredientRepository extends JpaRepository<PancakeIngredient, Long> {
}
