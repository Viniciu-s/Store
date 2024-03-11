package com.vinicius.stock.service;

import com.vinicius.stock.dto.BrandResponse;
import com.vinicius.stock.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Flux<ProductResponse> ListProductsByCategory(String category){
        logger.info("listando produtos por categoria");
        return webClient
                .get()
                .uri("/product/category/" + category)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    public Flux<ProductResponse> ListProductsByName(String name){
        logger.info("listando produtos por nome");
        return webClient
                .get()
                .uri("/product/name/" + name)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    public Flux<ProductResponse> ListProductsByColor(String color) {
        logger.info("listando produtos por cor");
        return webClient
                .get()
                .uri("/product/color/" + color)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    public Flux<ProductResponse> ListProductsBySize(String size) {
        logger.info("listando produtos por tamanho");
        return webClient
                .get()
                .uri("/product/size/" + size)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(ProductResponse.class);
    }

    public Mono<BrandResponse> findBrandById(UUID id) {
        logger.info("Acessando marcas cadastradas no estoque por id");
        return webClient
                .get()
                .uri("/brand/" + id)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BrandResponse.class);
    }

    public Flux<BrandResponse> ListAllBrands() {
        logger.info("Listando marcas cadastradas no estoque");
        return webClient
                .get()
                .uri("/brand")
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BrandResponse.class);
    }

    public Flux<BrandResponse> ListBrandsByName(String brandName){
        logger.info("listando marcas por nome");
        return webClient
                .get()
                .uri("/brand/name/" + brandName)
                .accept(APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(BrandResponse.class);}
}
