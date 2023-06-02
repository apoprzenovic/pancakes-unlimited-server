package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.OrdersPancake;
import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.entity.Users;
import com.pancakesunlimited.server.enums.UserRole;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.OrdersPancakeRepository;
import com.pancakesunlimited.server.repository.OrdersRepository;
import com.pancakesunlimited.server.repository.PancakeRepository;
import com.pancakesunlimited.server.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Arnes Poprzenovic
 * Service class for the {@link Orders} Entity
 */
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PancakeRepository pancakeRepository;
    private final OrdersPancakeRepository ordersPancakeRepository;
    private final UsersRepository usersRepository;

    /**
     * Constructor for the OrdersService class
     *
     * @param ordersRepository        - the repository for the {@link Orders} Entity
     * @param pancakeRepository       - the repository for the {@link Pancake} Entity
     * @param ordersPancakeRepository - the repository for the {@link OrdersPancake} Entity
     */
    @Autowired
    public OrdersService(OrdersRepository ordersRepository, PancakeRepository pancakeRepository, OrdersPancakeRepository ordersPancakeRepository, UsersRepository usersRepository) {
        this.ordersRepository = ordersRepository;
        this.pancakeRepository = pancakeRepository;
        this.ordersPancakeRepository = ordersPancakeRepository;
        this.usersRepository = usersRepository;
    }

    /**
     * Method to get all orders using {@link OrdersRepository}
     *
     * @return - a list of all orders
     */
    public List<Orders> getAllOrders(Integer userId) {
        if (!checkIfUserIsStoreOwner(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have appropriate permissions");
        }
        return ordersRepository.findAll();
    }

    /**
     * Method to get an order by id using {@link OrdersRepository}
     *
     * @param id - the id of the order to be returned
     * @return - the order with the specified id
     */
    public Orders getOrderById(Integer id, Integer userId) {
        if (!checkIfUserIsStoreOwner(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have appropriate permissions");
        }
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + id));
    }

    /**
     * Method to create an order using {@link OrdersRepository}
     *
     * @param order - the order to be created
     * @return - the created order
     */
    public Orders createOrder(Orders order) {
        List<OrdersPancake> ordersPancakes = order.getOrdersPancakes();
        for (OrdersPancake ordersPancake : ordersPancakes) {
            Pancake pancake = pancakeRepository.findById(ordersPancake.getPancake().getId())
                    .orElseThrow(() -> new RuntimeException("Pancake not found"));
            ordersPancake.setPancake(pancake);
        }
        if(order.getPrice() == null)
            calculatePrice(order);
        return ordersRepository.save(order);
    }

    /**
     * Method to update an order using {@link OrdersRepository}
     *
     * @param id           - the id of the order to be updated
     * @param orderDetails - the order details to be updated
     * @return - the updated order
     */
    public Orders updateOrder(Integer id, Orders orderDetails) {
        Orders currentOrder = getOrderByIdOnly(id);

        if (orderDetails.getDescription() != null) {
            currentOrder.setDescription(orderDetails.getDescription());
        }

        if (orderDetails.getOrderTime() != null) {
            currentOrder.setOrderTime(orderDetails.getOrderTime());
        }

        if (orderDetails.getOrdersPancakes() != null && !orderDetails.getOrdersPancakes().isEmpty()) {
            currentOrder.setOrdersPancakes(orderDetails.getOrdersPancakes());
        }

        calculatePrice(currentOrder);

        return ordersRepository.save(currentOrder);
    }

    /**
     * Method to delete an order using {@link OrdersRepository}
     *
     * @param id - the id of the order to be deleted
     */
    public void deleteOrder(Integer id) {
        Orders order = getOrderByIdOnly(id);
        ordersRepository.delete(order);
    }

    /**
     * Method get Order by id only using {@link OrdersRepository}
     *
     * @param id - the id of the order to be returned
     * @return - the order with the specified id
     */
    private Orders getOrderByIdOnly(Integer id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + id));
    }

    /**
     * Method to calculate the price of an order
     *
     * @param order - the order to calculate the price for
     */
    private void calculatePrice(Orders order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrdersPancake> ordersPancakes = order.getOrdersPancakes();
        for (OrdersPancake ordersPancake : ordersPancakes) {
            Pancake pancake = ordersPancake.getPancake();
            if (pancake.getPrice() != null) {
                totalPrice = totalPrice.add(pancake.getPrice());
            } else {
                System.out.println("Warning: Pancake with ID " + pancake.getId() + " has a null price.");
            }
        }
        order.setPrice(totalPrice);
    }

    /**
     * Method to add a pancake to an order
     *
     * @param orderId   - the id of the order to add the pancake to
     * @param pancakeId - the id of the pancake to be added
     * @return - the order with the added pancake
     */
    public Orders addPancakeToOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderByIdOnly(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + pancakeId));
        OrdersPancake ordersPancake = new OrdersPancake();
        ordersPancake.setOrder(order);
        ordersPancake.setPancake(pancake);
        ordersPancakeRepository.save(ordersPancake);
        calculatePrice(order);
        return order;
    }

    /**
     * Method to remove a pancake from an order
     *
     * @param orderId   - the id of the order to remove the pancake from
     * @param pancakeId - the id of the pancake to be removed
     * @return - the order with the removed pancake
     */
    public Orders removePancakeFromOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderByIdOnly(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + " id: " + pancakeId));
        List<OrdersPancake> ordersPancakes = ordersPancakeRepository.findByOrderAndPancake(order, pancake);
        if (ordersPancakes.isEmpty()) {
            throw new ResourceNotFoundException("OrdersPancake not found");
        }

        OrdersPancake ordersPancake = ordersPancakes.get(0);
        ordersPancakeRepository.delete(ordersPancake);

        calculatePrice(order);
        ordersRepository.save(order);

        return order;
    }


    /**
     * Method to get all orders for a user
     *
     * @param userId - the id of the user
     * @return - a list of all orders for the user
     */
    public List<Orders> getUserOrders(Integer userId) {
        return ordersRepository.findAllByUsersId(userId);
    }

    /**
     * Method to check if a user is a store owner using {@link UsersRepository}
     *
     * @param userId - the id of the user to be checked
     * @return - true if the user is a store owner, false otherwise
     */
    public boolean checkIfUserIsStoreOwner(Integer userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + "id: " + userId));
        return user.getRoles().getId() == UserRole.STORE_OWNER.getId();
    }

    /**
     * Method to get all pancakes for an order
     *
     * @param orderId - the id of the order
     * @return - a list of all pancakes for the order
     */
    public List<Pancake> getPancakesForOrder(Integer orderId) {
        Orders currentOrder = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + orderId));
        return currentOrder.getOrdersPancakes().stream().map(OrdersPancake::getPancake).collect(Collectors.toList());
    }

    /**
     * Method to get the amount of orders for a user
     * @param id - the id of the user
     * @return - the amount of orders for the user
     */
    public Integer getAmountOfOrders(Integer id) {
        return ordersRepository.findAllByUsersId(id).size();
    }
}
