package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Pancake} Entity
 */
public interface PancakeRepository extends JpaRepository<Pancake, Integer> {
}
