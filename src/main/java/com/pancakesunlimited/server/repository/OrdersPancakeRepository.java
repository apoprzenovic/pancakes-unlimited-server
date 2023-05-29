package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.OrdersPancake;
import com.pancakesunlimited.server.entity.Pancake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link OrdersPancake} Entity
 */
public interface OrdersPancakeRepository extends JpaRepository<OrdersPancake, Integer> {
    List<OrdersPancake> findByOrderAndPancake(Orders order, Pancake pancake);
}
