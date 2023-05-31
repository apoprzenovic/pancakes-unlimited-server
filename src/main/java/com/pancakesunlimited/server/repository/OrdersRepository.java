package com.pancakesunlimited.server.repository;

import com.pancakesunlimited.server.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Repository class for the {@link Orders} Entity
 */
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    /**
     * Method to get all orders by user id
     *
     * @param userId - the id of the user
     * @return - a list of all orders by the user
     */
    List<Orders> findAllByUsersId(Integer userId);
}
