package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.PancakeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link PancakeIngredient} Entity
 */
public interface PancakeIngredientRepository extends JpaRepository<PancakeIngredient, Integer> {
}
