package com.pancakesunlimited.server;

import com.pancakesunlimited.server.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PancakesUnlimitedServerApplicationTests {

    @Autowired
	IngredientService ingredientService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	PancakeService pancakeService;
	@Autowired
	RolesService rolesService;
	@Autowired
	UsersService usersService;

    @Test
    void contextLoads() {
		assertNotNull(ingredientService);
        assertNotNull(ordersService);
		assertNotNull(pancakeService);
		assertNotNull(rolesService);
		assertNotNull(usersService);
    }
}
