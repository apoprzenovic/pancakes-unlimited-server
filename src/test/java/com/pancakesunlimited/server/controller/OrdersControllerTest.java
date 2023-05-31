package com.pancakesunlimited.server.controller;

import com.pancakesunlimited.server.entity.Orders;
import com.pancakesunlimited.server.service.OrdersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * @author Arnes Poprzenovic
 * Class to test the {@link OrdersController} class
 */
class OrdersControllerTest {

    @Mock
    OrdersService ordersService;

    @InjectMocks
    OrdersController ordersController;

    /**
     * Method to set up the mockito annotations
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method to test the {@link OrdersController(Integer)} method
     */
    @Test
    void getOrder() {
        Orders expectedOrder = new Orders();
        expectedOrder.setId(1);
        when(ordersService.getOrderById(1,3)).thenReturn(expectedOrder);

        Orders actualOrder = ordersController.getOrder(1,3);

        assertEquals(expectedOrder, actualOrder);
    }
}
