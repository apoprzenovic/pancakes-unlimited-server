package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.OrdersPancake;
import com.pancakesunlimited.server.entity.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrdersPancakeRepository extends JpaRepository<OrdersPancake, Integer> {
    Optional<OrdersPancake> findByOrderAndPancake(Orders order, Pancake pancake);
}
