package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
