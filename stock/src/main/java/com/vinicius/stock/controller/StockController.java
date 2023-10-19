package com.vinicius.stock.controller;

import com.vinicius.stock.dto.ProductResponse;
import com.vinicius.stock.service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService service;

    public StockController(StockService service) {
        this.service = service;
    }

    @GetMapping("/product/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ProductResponse> getProductById(@PathVariable UUID id) {
        return service.findAProductById(id);

    }

    @GetMapping("/product")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> ListAllProducts() {
        return service.ListAllProducts();

    }

    @GetMapping("/product/category/{category}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getProductsByCategory(@PathVariable String category){
        return service.ListProductsByCategory(category);
    }

    @GetMapping("/product/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getProductsByName(@PathVariable String name){
        return service.ListProductsByName(name);
    }
}
