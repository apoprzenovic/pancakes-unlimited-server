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
        return ordersRepository.save(order);
    }

    public Orders updateOrder(Integer id, Orders orderDetails) {
        Orders newOrder = getOrderById(id);
        newOrder.setUsers(orderDetails.getUsers());
        newOrder.setLabel(orderDetails.getLabel());
        newOrder.setDescription(orderDetails.getDescription());
        newOrder.setOrderTime(orderDetails.getOrderTime());
        newOrder.setPrice(orderDetails.getPrice());
        return ordersRepository.save(newOrder);
    }

    public void deleteOrder(Integer id) {
        Orders order = getOrderById(id);
        ordersRepository.delete(order);
    }

    public Orders addPancakeToOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderById(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + pancakeId));
        OrdersPancake ordersPancake = new OrdersPancake();
        ordersPancake.setOrder(order);
        ordersPancake.setPancake(pancake);
        ordersPancakeRepository.save(ordersPancake);
        return order;
    }

    public Orders removePancakeFromOrder(Integer orderId, Integer pancakeId) {
        Orders order = getOrderById(orderId);
        Pancake pancake = pancakeRepository.findById(pancakeId)
                .orElseThrow(() -> new ResourceNotFoundException("Pancake" + "id: " + pancakeId));
        OrdersPancake ordersPancake = ordersPancakeRepository.findByOrderAndPancake(order, pancake)
                .orElseThrow(() -> new ResourceNotFoundException("OrdersPancake not found"));
        ordersPancakeRepository.delete(ordersPancake);
        return order;
    }

}
