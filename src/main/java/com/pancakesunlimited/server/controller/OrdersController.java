package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Orders> getAllOrders() {
        return ordersService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Integer id) {
        return ordersService.getOrderById(id);
    }

    @PostMapping
    public Orders createOrder(@RequestBody Orders order) {
        return ordersService.createOrder(order);
    }

    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        return ordersService.updateOrder(id, orderDetails);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
    }

    @PostMapping("/{orderId}/addPancake/{pancakeId}")
    public Orders addPancakeToOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.addPancakeToOrder(orderId, pancakeId);
    }

    @DeleteMapping("/{orderId}/removePancake/{pancakeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Orders removePancakeFromOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.removePancakeFromOrder(orderId, pancakeId);
    }
}
