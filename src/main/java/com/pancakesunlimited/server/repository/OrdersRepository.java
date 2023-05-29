package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Orders} Entity
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
