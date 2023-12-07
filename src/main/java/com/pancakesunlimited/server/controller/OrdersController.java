package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pu/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @GetMapping
    public ResponseEntity<List<Orders>> getAllOrders(@RequestParam Integer userId) {
        return ordersService.getAllOrders(userId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrder(@PathVariable Integer id, @RequestParam Integer userId) {
        return ordersService.getOrderById(id, userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Orders> createOrder(@RequestBody Orders order) {
        return ordersService.createOrder(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        return ordersService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{orderId}/addPancake/{pancakeId}")
    public ResponseEntity<Orders> addPancakeToOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.addPancakeToOrder(orderId, pancakeId);
    }

    @DeleteMapping("/{orderId}/removePancake/{pancakeId}")
    public ResponseEntity<Orders> removePancakeFromOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.removePancakeFromOrder(orderId, pancakeId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Orders>> getUserOrders(@PathVariable Integer userId) {
        return ordersService.getUserOrders(userId);
    }

    @GetMapping("/{orderId}/pancakes")
    public ResponseEntity<List<Pancake>> getPancakesForOrder(@PathVariable Integer orderId) {
        return ordersService.getPancakesForOrder(orderId);
    }

    @GetMapping("user/{id}/amountOfOrders")
    public ResponseEntity<Integer> getAmountOfOrders(@PathVariable Integer id) {
        return ordersService.getAmountOfOrders(id);
    }
}