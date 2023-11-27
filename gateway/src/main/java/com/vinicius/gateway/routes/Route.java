package com.vinicius.gateway.routes;

import com.vinicius.gateway.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Route {

    private final AuthenticationFilter filter;

    public Route(AuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user", r -> r
                        .path("/user/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("user")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(filter))
                        .uri("http://localhost:8085/"))
                .route("product", r -> r
                        .path("/product/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("product")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(filter))
                        .uri("http://localhost:8082/"))
                .route("notification", r -> r
                        .path("/notification/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("notification")
                                        .setFallbackUri("forward:/fallback")))
                        .uri("http://localhost:8083/"))
                .route("stock", r -> r
                        .path("/stock/**")
                        .filters(f -> f
                                .circuitBreaker(c -> c
                                        .setName("stock")
                                        .setFallbackUri("forward:/fallback"))
                                .filter(filter))
                        .uri("http://localhost:8084/"))
                .build();
    }
}