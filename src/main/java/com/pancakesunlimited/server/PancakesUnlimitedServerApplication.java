package com.pancakesunlimited.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Arnes Poprzenovic
 * Main class for the Spring Boot Application for the Pancakes Unlimited Server
 */

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.pancakesunlimited.server")
@EntityScan("com.pancakesunlimited.server")
public class PancakesUnlimitedServerApplication {

    /**
     * Main method for the Spring Boot Application
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(PancakesUnlimitedServerApplication.class, args);
    }

}
