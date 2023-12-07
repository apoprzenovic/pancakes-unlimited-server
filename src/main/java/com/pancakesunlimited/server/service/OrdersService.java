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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PancakeRepository pancakeRepository;
    private final OrdersPancakeRepository ordersPancakeRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, PancakeRepository pancakeRepository, OrdersPancakeRepository ordersPancakeRepository, UsersRepository usersRepository) {
        this.ordersRepository = ordersRepository;
        this.pancakeRepository = pancakeRepository;
        this.ordersPancakeRepository = ordersPancakeRepository;
        this.usersRepository = usersRepository;
    }

    public ResponseEntity<List<Orders>> getAllOrders(Integer userId) {
        if (checkIfUserIsStoreOwner(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have appropriate permissions");
        }
        List<Orders> orders = ordersRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    public ResponseEntity<Orders> getOrderById(Integer id, Integer userId) {
        if (checkIfUserIsStoreOwner(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User does not have appropriate permissions");
        }
        Orders order = ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + id));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    public ResponseEntity<Orders> createOrder(Orders order) {
        List<OrdersPancake> ordersPancakes = order.getOrdersPancakes();
        for (OrdersPancake ordersPancake : ordersPancakes) {
            Pancake pancake = pancakeRepository.findById(ordersPancake.getPancake().getId())
                    .orElseThrow(() -> new RuntimeException("Pancake not found"));
            ordersPancake.setPancake(pancake);
        }
        if (order.getPrice() == null)
            calculatePrice(order);
        Orders createdOrder = ordersRepository.save(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    public ResponseEntity<Orders> updateOrder(Integer id, Orders orderDetails) {
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

        Orders updatedOrder = ordersRepository.save(currentOrder);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteOrder(Integer id) {
        Orders order = getOrderByIdOnly(id);
        ordersRepository.delete(order);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private Orders getOrderByIdOnly(Integer id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + id));
    }

    private void calculatePrice(Orders order) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrdersPancake> ordersPancakes = order.getOrdersPancakes();
        for (OrdersPancake ordersPancake : ordersPancakes) {
            Pancake pancake = ordersPancake.getPancake();
            if (pancake.getPrice() != null)
                totalPrice = totalPrice.add(pancake.getPrice());
            else
                totalPrice = totalPrice.add(BigDecimal.ZERO);
        }
        order.setPrice(totalPrice);
    }

    public ResponseEntity<Orders> addPancakeToOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderByIdOnly(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + pancakeId));
        OrdersPancake ordersPancake = new OrdersPancake();
        ordersPancake.setOrder(order);
        ordersPancake.setPancake(pancake);
        ordersPancakeRepository.save(ordersPancake);
        calculatePrice(order);
        return ResponseEntity.ok(order);
    }

    public ResponseEntity<Orders> removePancakeFromOrder(Integer orderId, Integer pancakeId) {
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

        return ResponseEntity.ok(order);
    }

    public ResponseEntity<List<Orders>> getUserOrders(Integer userId) {
        return ResponseEntity.ok(ordersRepository.findAllByUsersId(userId));
    }

    public ResponseEntity<List<Pancake>> getPancakesForOrder(Integer orderId) {
        Orders currentOrder = ordersRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + orderId));
        return ResponseEntity.ok(currentOrder.getOrdersPancakes().stream().map(OrdersPancake::getPancake).collect(Collectors.toList()));
    }

    public ResponseEntity<Integer> getAmountOfOrders(Integer id) {
        return ResponseEntity.ok(ordersRepository.findAllByUsersId(id).size());
    }

    public boolean checkIfUserIsStoreOwner(Integer userId) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User" + "id: " + userId));
        return user.getRoles().getId() != UserRole.STORE_OWNER.getId();
    }
}