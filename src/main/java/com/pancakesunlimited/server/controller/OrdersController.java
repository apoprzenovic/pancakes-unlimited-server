package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.entity.Pancake;
import com.pancakesunlimited.server.service.OrdersService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Arnes Poprzenovic
 * Controller class for the orders table
 */
@RestController
@RequestMapping("/api/pu/orders")
public class OrdersController {

    private final OrdersService ordersService;

    /**
     * Constructor for the OrdersController class to autowire the {@link OrdersService} class
     *
     * @param ordersService - the service for the {@link Orders} Entity
     */
    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    /**
     * Method to get all orders using {@link OrdersService}
     *
     * @return - a list of all orders
     */
    @GetMapping
    public List<Orders> getAllOrders(@RequestParam Integer userId) {
        return ordersService.getAllOrders(userId);
    }

    /**
     * Method to get an order by id using {@link OrdersService}
     *
     * @param id - the id of the order to be returned
     * @return - the order with the specified id
     */
    @GetMapping("/{id}")
    public Orders getOrder(@PathVariable Integer id, @RequestParam Integer userId) {
        return ordersService.getOrderById(id, userId);
    }

    /**
     * Method to create an order using {@link OrdersService}
     *
     * @param order - the order to be created
     * @return - the created order
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Orders createOrder(@RequestBody Orders order) {
        return ordersService.createOrder(order);
    }


    /**
     * Method to update an order using {@link OrdersService}
     *
     * @param id           - the id of the order to be updated
     * @param orderDetails - the order with the updated details
     * @return - the updated order
     */
    @PutMapping("/{id}")
    public Orders updateOrder(@PathVariable Integer id, @RequestBody Orders orderDetails) {
        return ordersService.updateOrder(id, orderDetails);
    }

    /**
     * Method to delete an order using {@link OrdersService}
     *
     * @param id - the id of the order to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
    }

    /**
     * Method to add a pancake to an order using {@link OrdersService}
     *
     * @param orderId   - the id of the order to add the pancake to
     * @param pancakeId - the id of the pancake to be added
     * @return - the updated order
     */
    @PostMapping("/{orderId}/addPancake/{pancakeId}")
    public Orders addPancakeToOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.addPancakeToOrder(orderId, pancakeId);
    }

    /**
     * Method to remove a pancake from an order using {@link OrdersService}
     *
     * @param orderId   - the id of the order to remove the pancake from
     * @param pancakeId - the id of the pancake to be removed
     * @return - the updated order
     */
    @DeleteMapping("/{orderId}/removePancake/{pancakeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Orders removePancakeFromOrder(@PathVariable Integer orderId, @PathVariable Integer pancakeId) {
        return ordersService.removePancakeFromOrder(orderId, pancakeId);
    }

    /**
     * Method to get all orders for a specific user using {@link OrdersService}
     *
     * @param userId - the id of the user whose orders should be returned
     * @return - a list of all orders for the specified user
     */
    @GetMapping("/user/{userId}")
    public List<Orders> getUserOrders(@PathVariable Integer userId) {
        return ordersService.getUserOrders(userId);
    }

    /**
     * Method to get all pancakes for a specific order using {@link OrdersService}
     *
     * @param orderId - the id of the order whose pancakes should be returned
     * @return - a list of all pancakes for the specified order
     */
    @GetMapping("/{orderId}/pancakes")
    public List<Pancake> getPancakesForOrder(@PathVariable Integer orderId) {
        return ordersService.getPancakesForOrder(orderId);
    }

}
