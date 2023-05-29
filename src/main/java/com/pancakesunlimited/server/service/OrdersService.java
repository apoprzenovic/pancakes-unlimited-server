package com.pancakesunlimited.server.service;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.OrdersPancake;
import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.exception.ResourceNotFoundException;
import com.pancakesunlimited.server.repository.OrdersPancakeRepository;
import com.pancakesunlimited.server.repository.OrdersRepository;
import com.pancakesunlimited.server.repository.PancakeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final PancakeRepository pancakeRepository;
    private final OrdersPancakeRepository ordersPancakeRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository, PancakeRepository pancakeRepository, OrdersPancakeRepository ordersPancakeRepository) {
        this.ordersRepository = ordersRepository;
        this.pancakeRepository = pancakeRepository;
        this.ordersPancakeRepository = ordersPancakeRepository;
    }

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public Orders getOrderById(Integer id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order" + "id: " + id));
    }

    public Orders createOrder(Orders order) {
        calculatePrice(order);
        return ordersRepository.save(order);
    }

    public Orders updateOrder(Integer id, Orders orderDetails) {
        Orders order = getOrderById(id);
        order.setLabel(orderDetails.getLabel());
        order.setDescription(orderDetails.getDescription());
        order.setOrderTime(orderDetails.getOrderTime());
        order.setOrdersPancakes(orderDetails.getOrdersPancakes());
        calculatePrice(order);
        return ordersRepository.save(order);
    }

    public void deleteOrder(Integer id) {
        Orders order = getOrderById(id);
        ordersRepository.delete(order);
    }

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

    public Orders addPancakeToOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderById(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + pancakeId));
        OrdersPancake ordersPancake = new OrdersPancake();
        ordersPancake.setOrder(order);
        ordersPancake.setPancake(pancake);
        ordersPancakeRepository.save(ordersPancake);
        calculatePrice(order);
        return order;
    }

    public Orders removePancakeFromOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderById(orderId);
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


}
