package com.vinicius.gateway.routes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Route {

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("product", r -> r
                        .path("/product/**")
                        .filters(f -> f.circuitBreaker(c -> c.setName("product").setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8082/"))
                .build();
    }
}