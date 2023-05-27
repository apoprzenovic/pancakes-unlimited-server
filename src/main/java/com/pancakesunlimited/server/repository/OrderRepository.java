package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
