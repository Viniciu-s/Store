package com.vinicius.stock.controller;

import com.vinicius.stock.dto.BrandResponse;
import com.vinicius.stock.dto.CategoryResponse;
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

    @GetMapping("/product/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getProductsByName(@PathVariable String name){
        return service.ListProductsByName(name);
    }

    @GetMapping("/product/color/{color}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getProductsByColor(@PathVariable String color){
        return service.ListProductsByColor(color);
    }

    @GetMapping("/product/size/{size}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductResponse> getProductsBySize(@PathVariable String size){
        return service.ListProductsBySize(size);
    }

    @GetMapping("/brand/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BrandResponse> getBrandById(@PathVariable UUID id) {
        return service.findBrandById(id);
    }

    @GetMapping("/brand")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BrandResponse> ListAllBrands() {
        return service.ListAllBrands();
    }

    @GetMapping("/brand/name/{brandName}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BrandResponse> getBrandsByName(@PathVariable String brandName) {
        return service.ListBrandsByName(brandName);
    }

    @GetMapping("/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<CategoryResponse> getCategoryById(@PathVariable UUID id) {
        return service.findCategoryById(id);
    }

    @GetMapping("/category")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CategoryResponse> ListAllCategories() {
        return service.ListAllCategories();
    }

    @GetMapping("/category/name/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CategoryResponse> getCategoryByName(@PathVariable String categoryName) {
        return service.ListCategoryByName(categoryName);
    }
}
