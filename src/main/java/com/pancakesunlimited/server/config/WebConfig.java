package com.pancakesunlimited.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Arnes Poprzenovic
 * Configuration class for the web
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Method to configure the CORS policy
     * @param registry - the registry to be configured
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
    }
}

