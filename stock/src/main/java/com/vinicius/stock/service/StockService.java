package com.vinicius.stock.service;

import com.vinicius.stock.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
public class StockService {
    private final WebClient webClient;

    private static final Logger logger = LoggerFactory.getLogger(StockService.class);

    public StockService(WebClient.Builder builder) {
        webClient = builder
                .baseUrl("http://localhost:8080")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes()))
                .build();

    }

    @Cacheable("findAProductById")
    public Mono<ProductResponse> findAProductById(UUID id) {
        logger.info("Acessando produtos cadastrados no estoque por id");
        return webClient
                .get()
                .uri("/product/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ProductResponse.class);
    }

    @Cacheable("ListAllProducts")
    public Flux<ProductResponse> ListAllProducts() {
        logger.info("Listando produtos cadastrados no estoque");
        return webClient
                .get()
                .uri("/product")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    @Cacheable("ListProductsByCategory")
    public Flux<ProductResponse> ListProductsByCategory(String category){
        logger.info("listando produtos por categoria");
        return webClient
                .get()
                .uri("/product/category/" + category)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    @Cacheable("ListProductsByName")
    public Flux<ProductResponse> ListProductsByName(String name){
        logger.info("listando produtos por nome");
        return webClient
                .get()
                .uri("/product/name/" + name)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    @Cacheable("ListProductsByColor")
    public Flux<ProductResponse> ListProductsByColor(String color) {
        logger.info("listando produtos por cor");
        return webClient
                .get()
                .uri("/product/color/" + color)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    @Cacheable("ListProductsBySize")
    public Flux<ProductResponse> ListProductsBySize(String size) {
        logger.info("listando produtos por tamanho");
        return webClient
                .get()
                .uri("/product/size/" + size)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }
}
