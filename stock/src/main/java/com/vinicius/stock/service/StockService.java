package com.vinicius.stock.service;

import com.vinicius.stock.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class StockService {
    private final WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public StockService(WebClient.Builder builder) {
        webClient = builder.baseUrl("http://localhost:8080").build();

    }

    public Mono<ProductResponse> findAProductById(UUID id) {
        logger.info("Acessando produtos cadastrados no estoque por id");
        return webClient
                .get()
                .uri("/product/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    public Flux<ProductResponse> ListAllProducts() {
        logger.info("Listando produtos cadastrados no estoque");
        return webClient
                .get()
                .uri("/product")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }
}